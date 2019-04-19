package cn.witsky.smb.service;

import cn.witsky.smb.StateMachine.Event;
import cn.witsky.smb.StateMachine.State;
import cn.witsky.smb.StateMachine.StateMachineForCollectAndConnect;
import cn.witsky.smb.StateMachine.StateMachineStore;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.utils.JavaUtils;
import cn.witsky.smb.pojo.ContextData;
import cn.witsky.smb.pojo.apk.ApkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Modified by Hyx for Ali PaaS+ Project.
 *
 * @author zhanghaifeng
 * @author HuangYX
 * @time 2017年09月28日 下午2:38
 * @time 2018年05月29日 下午8:59
 */
@Service

public class StateMachineService implements ApplicationContextAware {
  @Autowired
  private  UntypedStateMachineBuilder stateMachineBuilderForCollectAndConnect;
  @Autowired
  private StateMachineStore stateMachineStore;
@Autowired
  private  Config config;

  private final static Logger logger = LoggerFactory.getLogger(StateMachineService.class);
  private ApplicationContext applicationContext;
  @Autowired
  private Environment environment;


  public  void createStateMachine(String id, ContextData ContextData, ApkConfig apkConfig) {

    try {
      // 创建状态机
      StateMachineForCollectAndConnect stateMachine;
      stateMachine = (StateMachineForCollectAndConnect) stateMachineBuilderForCollectAndConnect.newStateMachine(State.idle,
              applicationContext,ContextData, stateMachineStore,apkConfig);

      // 并保存会话标识与状态机的映射关系

      //这里就会阻塞
      HashMap<String, ArrayBlockingQueue<String>> callBlockingMap = stateMachineStore.getCallBlockingMap();
      HashMap<String, ArrayList<String>> callSubMap = stateMachineStore.getCallSubMap();

      ArrayBlockingQueue<String> queue = callBlockingMap.get(ContextData.getCallData().getTaskid());
      ArrayList<String> subidlist = callSubMap.get(ContextData.getSubID());
      if(queue==null){
        queue=new ArrayBlockingQueue<>(config.getConcurrency_req());
        callBlockingMap.put(ContextData.getCallData().getTaskid(),queue);
      }
      if(subidlist==null){
        subidlist=new ArrayList(ContextData.getCallData().getCalledpartylist().size());
        callSubMap.put(ContextData.getSubID(),subidlist);
      }
      queue.put(id);
      subidlist.add(id);
      stateMachineStore.getCallBlockingQueue().put(id);
      stateMachineStore.put(id, stateMachine);
      logger.debug("加入了状态机"+id);
      stateMachine.start();
    } catch (Exception e) {
      logger.error("StateMachineService.createStateMachine().EXCEPTION,cause of={}", e.getMessage(), e);
      logger.info("ContextData={}", ContextData);
      logger.info("stateMachineStore={}", stateMachineStore);
    }
  }

  public boolean fireEventToStateMachine(String id, Event event, Object data) {
    // 根据本地sessionId定位会话
    UntypedStateMachine stateMachine = stateMachineStore.get(id);
    if (stateMachine != null) {
      stateMachine.fire(event, data);
      return true;
    }
    return false;
  }



  public boolean exist(String id) {
    return stateMachineStore.get(id) != null;
  }
  public UntypedStateMachine get(String id) {
    return stateMachineStore.get(id) ;
  }
  public StateMachineStore getStateMachineStore() {
    return stateMachineStore ;
  }
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Async("statemachineList")
  public void createStateMachineList(ContextData contextData, ArrayList<String> calledpartylist, ApkConfig apkConfig) {
  contextData.setCaller(contextData.getCallData().getDisplaynumber());
  for (int i = 0; i < calledpartylist.size(); i++) {
     contextData.setLocalSessionId(JavaUtils.getUUID());
    contextData.GenerateSessionId(config.getTemp_pid(),environment);
    contextData.setCalled(calledpartylist.get(i));
    createStateMachine(contextData.getLocalSessionId(), contextData.clone(),apkConfig);
  }
  }
}
