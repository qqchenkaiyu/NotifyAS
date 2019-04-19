package cn.witsky.smb.config;

import cn.witsky.smb.core.constant.Int;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.squirrelframework.foundation.component.SquirrelSingletonProvider;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author HuangYX
 * @date 2018/4/17 14:12
 */
@Configuration
@EnableAsync
@ConditionalOnBean(Config.class)
public class ExecutorConfig {
  @Autowired
  public Config config;
  /**
   * Set the ThreadPoolExecutor'submit core pool size.
   */
  @Value("${witsky.async-thread-pool-core-size:100}")
  private int corePoolSize;
  /**
   * Set the ThreadPoolExecutor'submit maximum pool size.
   */
  @Value("${witsky.async-thread-pool-max-size:100}")
  private int maxPoolSize;
  /**
   * Set the capacity for the ThreadPoolExecutor'submit BlockingQueue.
   */
  @Value("${witsky.async-thread-pool-queue-capacity:1000}")
  private int queueCapacity;

  @Bean("statemachineList")
  public ThreadPoolTaskExecutor taskCreator() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix("createList-pool-");
    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
    //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
//    ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();
//    threadPoolExecutor.prestartAllCoreThreads();
    return executor;
  }
  //状态机线程池设置
  @PostConstruct
  void setScheduledExecutorServiceForStateMachine() {
    SquirrelSingletonProvider.getInstance().register(ScheduledExecutorService.class, new ScheduledThreadPoolExecutor(config.getStatemachineThreadcount(), new ThreadFactoryBuilder().setNameFormat("fsm-scheduled-%d").build()));

    //主要配置这个
   // ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
   // Executors.
    ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("fsm-async-%d").build());
    SquirrelSingletonProvider.getInstance().register(ExecutorService.class, executorService);
  }
  @Value("${witsky.scheduled-thread-pool-core-size:2}")
  private int scheduledSize;

  @Bean("scheduledPoolExecutor")
  public ScheduledThreadPoolExecutor taskScheduler() {
    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
    ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(scheduledSize,
        new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").build(),
        handler
    );
    return e;
  }

  @Value("${witsky.push-scheduled-thread-pool-core-size:20}")
  private int pushScheduledSize;


  @Bean(name = "pushThreadPoolExecutor")
  public ScheduledThreadPoolExecutor scheduledThreadPoolExecutorForPush() {
    ScheduledThreadPoolExecutor e = new ScheduledThreadPoolExecutor(
        pushScheduledSize,
        new BasicThreadFactory.Builder().namingPattern("push-scheduled-pool-%d").build(),
        new ThreadPoolExecutor.AbortPolicy()
    );
    e.setKeepAliveTime(Int.ONE, TimeUnit.MINUTES);

    return e;
  }


  @Value("${witsky.astoslf-thread-pool-max-size:40}")
  private int asToSlfMaxPoolSize;
  @Value("${witsky.astoslf-thread-pool-queue-capacity:1000}")
  private int asToSlfQueueCapacity;
  @Bean(name = "internalHttp")
  public ThreadPoolTaskExecutor createThreadPoolExecutorForInternalHttp() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(asToSlfMaxPoolSize);
    executor.setQueueCapacity(asToSlfQueueCapacity);
    executor.setThreadNamePrefix("asToSlf-pool-");

    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    executor.initialize();
    return executor;
  }

}
