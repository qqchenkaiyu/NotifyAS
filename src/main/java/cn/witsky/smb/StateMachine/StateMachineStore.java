package cn.witsky.smb.StateMachine;

import cn.witsky.smb.config.Config;
import cn.witsky.smb.service.XdrService;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhanghaifeng
 * @time 2017年08月25日 上午10:42
 */
@Data
@Component
public class StateMachineStore {

    private final static Logger logger = LoggerFactory.getLogger(StateMachineStore.class);

    /**
     * 呼叫状态机map(数量控制)
     */
    private Map<String, StateMachineData> map;
        @Autowired
    Config config;
    @Autowired
    private XdrService xdrService;
    /**
     * 短信状态机map(无数量控制)
     */
    private Map<String, StateMachineData> smsStateMachineMap;
    private  HashMap<String,ArrayList<String>> callSubMap;
    private  HashMap<String,ArrayBlockingQueue<String>> callBlockingMap;
    private  ArrayBlockingQueue<String> callBlockingQueue;

//通话记录集合  以taskID为键
    private  HashMap<String,JSONArray> noticeresults;
//状态机信息集合  根据taskid分类  key taskid  value  statemachineData集合
    private  HashMap<String,JSONArray> executeResult;
    /**
     * 状态机数量记录(历史总数)
     */
    private AtomicLong total;
    private AtomicLong smsTotal;

    public StateMachineStore(Config config) {
        // 初始化
    this.config=config;
        map = Maps.newConcurrentMap();
        smsStateMachineMap = Maps.newConcurrentMap();
        total = new AtomicLong(0);
        smsTotal = new AtomicLong(0);
        callBlockingQueue=new ArrayBlockingQueue(config.getConcurrency_sys());
        callBlockingMap=new HashMap<>();
        noticeresults=new HashMap<>();
        executeResult=new HashMap<>();
        callSubMap=new HashMap<>();
        // 启动定时线程,清理超过最大存活时间的状态机
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder().setNameFormat("sm-store-scheduled-%d").build());
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long now = DateTimeUtils.currentTimeMillis();
                for (Map.Entry<String, StateMachineData> entry : map.entrySet()) {
                    // 停止超过最大存活时间的状态机 1h
                    if (now - entry.getValue().getCreateMillis() > config.getStatemachineMaxTtl()) {
                        StateMachineData data = map.get(entry.getKey());
                        if (data != null) {
                            UntypedStateMachine stateMachine = data.getStateMachine();
                            Object currentState = stateMachine.getCurrentState();
                            if(stateMachine.isError() || !Objects.equals(currentState, State.connected)){
                                logger.info("StateMachine={} is going to terminate,cause of ={}", entry.getKey(), "time_out");
                                stateMachine.fire(Event.receiveRelease);
                            }else {
                              // 当前是connected状态
                              stateMachine.fire(Event.timeoutToMaxDuring);
                            }
                        }
                    }
                }
            }
        }, 1, 1, TimeUnit.MINUTES);

        // 删除短信映射
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long now = DateTimeUtils.currentTimeMillis();
                for (Map.Entry<String, StateMachineData> entry : smsStateMachineMap.entrySet()) {
                    // 停止超过最大存活时间的状态机
                    if (now - entry.getValue().getCreateMillis() > config.getStatemachineMaxTtl()) {
                        StateMachineData data = smsStateMachineMap.remove(entry.getKey());
                        if (data != null && data.getStateMachine()!=null) {
                            data.getStateMachine().terminate();
                        }
                    }
                }
            }
        }, 1, 1, TimeUnit.MINUTES);

    }

    private boolean isStateMachineTimeOut(StateMachineData data, long now,long stateMachineMaxTtl){
        // 超过绑定信息中的最大通话时间 或者 超过系统设置的状态机最大存活时间
        return (data.getCallDuration() > 0 && now - data.getPickUpMillis() > data.getCallDuration())
                || now - data.getCreateMillis() > stateMachineMaxTtl;
    }

    public StateMachineData getCallStateMachineData(String id){
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return map.get(id);
    }

    /**
     * 放入呼叫状态机
     *
     * @param id
     * @param stateMachine
     */
    public void put(String id,
                    UntypedStateMachine stateMachine) {

        if (id == null || stateMachine == null) {
            throw new IllegalArgumentException("id or stateMachine cannot be null");
        }
        map.put(id, new StateMachineData(stateMachine));
        total.incrementAndGet();
    }

    /**
     * 放入短信状态机
     *
     * @param id
     * @param stateMachine
     */
    public void putSmsStateMachine(String id,
                                   UntypedStateMachine stateMachine) {

        if (id == null || stateMachine == null) {
            throw new IllegalArgumentException("id or stateMachine cannot be null");
        }
        smsStateMachineMap.put(id, new StateMachineData(stateMachine));
        smsTotal.incrementAndGet();
    }

    /**
     * 呼叫状态机数量
     *
     * @return
     */
    public int size() {
        return map.size();
    }

    /**
     * 状态机历史总数
     *
     * @return
     */
    public long total() {
        return total.get();
    }

    /**
     * 通过id移除呼叫状态机
     *
     * @param id
     * @return
     */
    public UntypedStateMachine remove(String id) {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        StateMachineData data = map.remove(id);
        return data != null ? data.getStateMachine() : null;
    }

    /**
     * 通过id移除短信状态机
     *
     * @param id
     * @return
     */
    public UntypedStateMachine removeSmsStateMachine(String id) {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        StateMachineData data = smsStateMachineMap.remove(id);
        return data != null ? data.getStateMachine() : null;
    }

    /**
     * 通过id获取通话状态机
     *
     * @param id
     * @return
     */
    public UntypedStateMachine get(String id) {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        StateMachineData data = map.get(id);
        return data != null ? data.getStateMachine() : null;
    }

    /**
     * 通过id获取短信状态机
     *
     * @param id
     * @return
     */
    public UntypedStateMachine getSmsStateMachine(String id) {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        StateMachineData data = smsStateMachineMap.get(id);
        return data != null ? data.getStateMachine() : null;
    }

    /**
     * 通过id获取呼叫状态机状态
     *
     * @param id
     * @return
     */
    public Map<String, Object> getStatus(String id) {

        StateMachineData data = map.get(id);
        if (data != null) {
            return new ImmutableMap.Builder<String, Object>().put("status", data.getStateMachine().getCurrentState().toString()).put("createMillis", data
                    .getCreateMillis()).build();
        }
        return Maps.newHashMap();
    }

    /**
     * 通过id获取短信状态机状态
     *
     * @param id
     * @return
     */
    public Map<String, Object> getSmsStateMachineStatus(String id) {

        StateMachineData data = smsStateMachineMap.get(id);
        if (data != null) {
            return new ImmutableMap.Builder<String, Object>().put("status", data.getStateMachine().getCurrentState().toString()).put("createMillis", data
                    .getCreateMillis()).build();
        }
        return Maps.newHashMap();
    }

    public List<String> getList() {
        List<String> list = Lists.newLinkedList();
        for (StateMachineData stateMachineData : map.values()) {
            logger.info("x={}", stateMachineData.getStateMachine().getDescription());
            stateMachineData.getStateMachine().getIdentifier();
            list.add(stateMachineData.getCreateMillis() + "," + stateMachineData.getStateMachine().getCurrentState());
        }
        return list;
    }

    @Scheduled(fixedDelay = 30*1000)
    public void scheduledToLogSMStoreHealth(){
        logger.info("callCurrentCount={}, callHistoryTotalCount={}, smsCurrentCount={}, smsHistoryTotalCount={}",
                map.size(), total.get(), smsStateMachineMap.size(), smsTotal.get());
    }


    public HashMap<String,ArrayBlockingQueue<String>> getCallBlockingMap() {
        return callBlockingMap;
    }

    public ArrayBlockingQueue<String> getCallBlockingQueue() {
        return callBlockingQueue;
    }
}
