package cn.witsky.smb.StateMachine;


import cn.witsky.smb.Constant.AsCons;
import cn.witsky.smb.Constant.Cons;
import cn.witsky.smb.Constant.Field;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.pojo.CallData;
import cn.witsky.smb.pojo.ContextData;
import cn.witsky.smb.pojo.apk.ApkConfig;
import cn.witsky.smb.pojo.communication.SlfReleaseNotification;
import cn.witsky.smb.pojo.communication.SlfSessionTest;
import cn.witsky.smb.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.annotation.AsyncExecute;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

@StateMachineParameters(stateType = State.class, eventType = Event.class, contextType = Object.class)
public class StateMachineForCollectAndConnect extends AbstractUntypedStateMachine {
    private final static Logger logger = LoggerFactory.getLogger(StateMachineForCollectAndConnect.class);
    public ContextData contextData;
    private    ApplicationContext applicationContext;
private  Config config;
    private ApkConfig apkConfig;
    private StateMachineStore stateMachineStore;

    public StateMachineForCollectAndConnect(ApplicationContext applicationContext, ContextData contextData,
                                            StateMachineStore stateMachineStore, ApkConfig apkConfig) {
        this.contextData = contextData;
        this.applicationContext = applicationContext;
        this.stateMachineStore = stateMachineStore;
        this.config = stateMachineStore.config;
        this.apkConfig = apkConfig;
    }

    public ContextData getContextData() {
        return contextData;
    }

    /**
     * 初始状态准备连接
     * @param from
     * @param to
     * @param event
     * @param context
     */

    @AsyncExecute
    protected void prepareToConnect(State from,State to,Event event,Object context) {
        //从contextData里取得slf业务接续接口需要的字段
        //记录此单电话呼出时间
        JSONObject Data = PrepareDataUtil.getBodyForSlfConnect(contextData,config,apkConfig);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        Map<String, Object> resp = httpUtil.postData(contextData.getSlfRootUrl() +config.getAstoslfMappingUrl() + Cons.RequestMethod.CALLOUTREQUEST, Data, contextData);
                        if(resp!=null&& Objects.equals(resp.get("code"),0)){
                    //this.fire(Event.connectSuccess);
                    fire(Event.connectSuccess);
                    logger.debug(contextData.getLocalSessionId()+"  connectSLF成功");
                    contextData.setCallID((String) resp.get("callId"));
                    contextData.setCalltime(LogTimeUtil.getTime());
                     contextData.setCallouttime(System.currentTimeMillis());

                     //替换sessionid
                            String sessionId = contextData.getSessionId();
                            String replace = sessionId.replace(config.getTemp_pid(), (String) resp.get("c"));
                            contextData.setSessionId(replace);
                        }else{
                    fire(Event.intoFail);
                    logger.debug(contextData.getLocalSessionId()+"  connectSLF失败 ，销毁状态机"+ resp);
                }

    }
    private Object getBean(Class clazz) {
        return applicationContext.getBean(clazz);
    }
    @AsyncExecute
    private void testSession(State from,State to,Event event,Object context) {
        logger.debug("sm:timeToTestSession,from={},to={}", from, to);
        JSONObject Data = PrepareDataUtil.getBodyForSlfTest(contextData, config, apkConfig);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        String postDataForString = httpUtil.postDataForString(contextData.getSlfRootUrl() +config.getAstoslfMappingUrl()+Cons.RequestMethod.SESSIONTEST, Data, contextData);
        SlfSessionTest result = JSONObject.parseObject(postDataForString, SlfSessionTest.class);
        //   .po(contextData, contextData.getSlfRootUrl(), sst);
        if (result != null
                && result.getCode() != null
                && result.getCode() == Int.ZERO
                && Objects.equals(result.getCallId(), contextData.getCallID())
                && Objects.equals(result.getSessionId(), contextData.getSessionId())) {
            logger.debug(contextData.getLocalSessionId()+ "testSessionOk");
            // 重置失败次数
            contextData.resetItProgressiveFailures();
        } else {
            // 递增失败次数
            int failures = contextData.increaseITProgressiveFailures();
            logger.info("id[{}].testSession.failed,failures= {}", contextData.getLocalSessionId(), failures);
            // 达到某个阈值时触发检测会话失败事件
            if (failures >= config.getBearableContinuousFailsForItTest()) {
                logger.error("testSessionFail_exceed_times,localSessionId={},successiveFailsForTestSession={}",
                        contextData.getLocalSessionId(), failures);
                this.fireImmediate(Event.testSessionFail, AsUtil.getStateMachineContextDataCause("IT test failed"));
            }
        }
    }
    @AsyncExecute
    private void receiveAlerting(State from,State to,Event event,Object context) {
        contextData.setAlertTime(System.currentTimeMillis());
        logger.debug(contextData.getLocalSessionId()+" alertTime"+ LocalDateTime.now().toString());

    }
    @AsyncExecute
    private void receiveRinging(State from,State to,Event event,Object context) {
        logger.debug(contextData.getLocalSessionId()+" RingingTime"+ LocalDateTime.now().toString());
        contextData.setRingtime(LogTimeUtil.getTime());
        contextData.setRingingTime(System.currentTimeMillis());
    }
    //定时触发pa
    @AsyncExecute
    private void receiveConnnecting(State from,State to,Event event,Object context) {
        logger.debug(contextData.getLocalSessionId()+" ConnectingTime"+ LocalDateTime.now().toString());
        contextData.setStarttime(System.currentTimeMillis());
        contextData.setTalked(true);
        contextData.setAnswertime(LogTimeUtil.getTime());
    }


    //用于重播次数大于1时的流程
    @AsyncExecute
    private void playOnly(State from,State to,Event event,Object context) {
        logger.debug("准备放音");
        //向slf发ivr  语音内容为“是否重听”
        CallData callData = contextData.getCallData();
        JSONObject Data = PrepareDataUtil.getBodyForSlfPA(contextData,config,apkConfig);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        Map<String, Object> resp = httpUtil.postData(contextData.getSlfRootUrl() +config.getAstoslfMappingUrl()+ Cons.RequestMethod.SERVICEPLAYANDCOLLECT, Data,contextData);
        if(resp!=null&& Objects.equals(resp.get("message"),"success")){
            //放音成功不是放音结束
            logger.debug(contextData.getLocalSessionId()+ "放音成功");
}

    }
    @AsyncExecute
    private void playEnd(State from,State to,Event event,Object context) {
        //现在判断是否下IVR  根据callData判断
        logger.debug(contextData.getLocalSessionId()+"  receiveSRRTime"+ LocalDateTime.now().toString());
        CallData callData = contextData.getCallData();
        if(isNeedAutoReplay(callData)){
            Integer repeat = Integer.valueOf(callData.getRepeattimes());
            //判断剩余重播
            if(contextData.getPlayTimes()==null)contextData.setPlayTimes(0);
            if(contextData.increasePlayTime()< repeat){
               logger.debug(contextData.getLocalSessionId()+"  准备重复放音，剩余次数"+(repeat-contextData.getPlayTimes()));
                fire(Event.PA);
                return;
            }else{
                fire(Event.ReleaseWithNoplay);
            }
        }
        if(isNeedIVR(callData)){
            contextData.setIVR(true);
            fire(Event.askForReplayKey);
            return;
        }
        fire(Event.ReleaseWithNoplay);
    }
    @AsyncExecute
    private void askForReplayKey(State from,State to,Event event,Object context) {
        //向slf发ivr  语音内容为“是否重听”
        CallData callData = contextData.getCallData();
        JSONObject Data = PrepareDataUtil.getBodyForSlfIVR(contextData,config,apkConfig);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        Map<String, Object> resp = httpUtil.postData(contextData.getSlfRootUrl() +config.getAstoslfMappingUrl()+ Cons.RequestMethod.SERVICEPLAYANDCOLLECT, Data,contextData);
        if(resp!=null&& Objects.equals(resp.get("message"),"success"))logger.debug("播放重听提示成功" +contextData.getLocalSessionId());
    }

    private boolean isNeedAutoReplay(CallData callData) {
        return  callData.getRepeattimes()!=null;
    }
    private boolean isNeedIVR(CallData callData) {
        return  callData.getRepeattimes()==null&&callData.getRepeatcondition()!=null;
    }


    @AsyncExecute
    private void timeoutForRing(State from,State to, Event event,Object context) {
        endCall(false);

    }



    @AsyncExecute
    private void timeoutWhenRinging(State from,State to, Event event,Object context) {
        contextData.setTimeOutWhenRing(true);
        endCall(false);

    }
    @AsyncExecute
    private void ReleasedWhenRinging(State from,State to, Event event,Object context) {
        logger.debug(" ReleasedWhenRinging "+ LogTimeUtil.getTime());
        SlfReleaseNotification srnf= (SlfReleaseNotification) context;
        contextData.setDir(srnf.getReleaseDirect());
        if(srnf.getCause()!=null)
            contextData.setCause(srnf.getCause().getReason());
        releaseSelf();
    }
    @AsyncExecute
    private void ReleasedWhenTalking(State from,State to, Event event,Object context) {
        logger.debug(" ReleasedWhenTalking "+ LogTimeUtil.getTime());
        SlfReleaseNotification srnf= (SlfReleaseNotification) context;
        contextData.setDir(srnf.getReleaseDirect());
        if(srnf.getCause()!=null)
            contextData.setCause(srnf.getCause().getReason());
        releaseSelf();

    }


//主动释放
    @AsyncExecute
    private void ReleaseWithNoplay(State from,State to, Event event,Object context) {
        endCall(false);
    }
    //主动释放
    private void endCall(boolean replay) {
        /**
         * 经咨询薛哥：
         * AS下发disconnect的时候，plan填0，reason填31。
         */
        contextData.setCause(AsCons.TAG_DISCONNECT_CAUSE_REASON);
        contextData.setDir(Cons.RELEASE_DIR_CALLER);
        releaseSelf();
        //发送断连请求

        JSONObject Data = PrepareDataUtil.getBodyForSlfDisConnect(contextData,config,apkConfig,replay);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        Map<String, Object> resp = httpUtil.postData(contextData.getSlfRootUrl() +config.getAstoslfMappingUrl()+Cons.RequestMethod.SERVICERELEASE, Data,contextData);
        if(resp!=null&& Objects.equals(resp.get("message"),"success")){
            if(replay){
                logger.debug("endCallWithPlay成功" +contextData.getLocalSessionId());
            }else{
                logger.debug("endCallWithNoPlay成功" +contextData.getLocalSessionId());
            }
        }
    }
    @AsyncExecute
    private void  ReleaseWithReplay(State from,State to, Event event,Object context) {
        endCall(true);
    }

    @AsyncExecute
    private void intoFail(State from, State to,Event event,Object context) {
        releaseSelf();
    }
    @AsyncExecute
    private void receiveRelease(State from, State to,Event event,Object context) {
        logger.debug(" receiveRelease "+ LogTimeUtil.getTime());
        SlfReleaseNotification srnf= (SlfReleaseNotification) context;
        if(srnf!=null) {
            contextData.setDir(srnf.getReleaseDirect());
            if (srnf.getCause() != null)
                contextData.setCause(srnf.getCause().getReason());
        }
        releaseSelf();

    }
    @AsyncExecute
    private void testSessionFail(State from, State to,Event event,Object context) {
        logger.debug(" testSessionFail "+ LogTimeUtil.getTime());

        releaseSelf();

    }
    @AsyncExecute
    private void timeoutForAlert(State from, State to,Event event,Object context) {
        logger.debug(" timeoutForAlert "+ LogTimeUtil.getTime());
        endCall(false);
    }

    private void releaseSelf() {
        if(contextData.isTalked()) {
            contextData.setEndtime(System.currentTimeMillis() / 1000);
            contextData.setDurationTime((contextData.getEndtime()-contextData.getStarttime())/1000);
        }
        contextData.setReleasetime(LogTimeUtil.getTime());
        AddToReport();
        stateMachineStore.getXdrService().outputCDR(PrepareDataUtil.getCDRACaller(contextData, config));
        // 停止状态机
        this.terminate();
        // 删除映射
        this.UnRigister(contextData.getLocalSessionId());
    }
    private void UnRigister(String localSessionId) {
        //在这里记录结束时间是否合适？
        stateMachineStore.remove(localSessionId);
        logger.debug("从系统移除状态机 " + localSessionId);
        //从两个阻塞队列清除
        HashMap<String, ArrayBlockingQueue<String>> callBlockingMap = stateMachineStore.getCallBlockingMap();
        ArrayBlockingQueue<String> callBlockingQueue = stateMachineStore.getCallBlockingQueue();
        callBlockingQueue.remove(localSessionId);
        HashMap<String, ArrayList<String>> callSubMap = stateMachineStore.getCallSubMap();

        ArrayList<String> subidlist = callSubMap.get(contextData.getSubID());
        ArrayBlockingQueue<String> queue_task = callBlockingMap.get(contextData.getCallData().getTaskid());
        queue_task.remove(localSessionId);

            if (queue_task.size() == 0) {
                callBlockingMap.remove(contextData.getCallData().getTaskid());
            }
        int size;
        //这里是否必须加锁
        synchronized (callSubMap) {
        subidlist.remove(localSessionId);
            size = subidlist.size();
        }
            if (size == 0) {
                //说明刚刚是这次绑定的最后一次通话
                callSubMap.remove(contextData.getSubID());
                logger.debug(contextData.getSubID() + "这是最后一次通话  推送报告");
                SendReport();
            }

    }


    private void SendReport() {
        if(contextData.getCallData().getNotifyurl()==null)return;
        CallData callData = contextData.getCallData();
        HashMap<String, JSONArray> noticeresults = stateMachineStore.getNoticeresults();
        JSONArray jsonArray = noticeresults.get(contextData.getSubID());
        if(jsonArray==null) return;
        JSONObject Data = PrepareDataUtil.getBodyForReport(contextData,jsonArray);
        HttpUtil httpUtil = (HttpUtil) getBean(HttpUtil.class);
        String resp = httpUtil.postDataForString(config.getNotifyUrl(), Data, contextData);
        if(resp!=null&& resp.contains("success")){
            logger.debug("发送报告成功 "+contextData.getSubID());
        }else{
            logger.debug("发送报告失败，subid id是 "+contextData.getSubID()+"  resp="+resp);

        }
    }
    private void AddToReport() {
        //这里还有值为空的情况
        if(contextData.getCallData().getNotifyurl()==null)return;
        String status = ResultUtil.getStatus(contextData.isTalked(), contextData.getDir(),
                contextData.getCause(), contextData.getRingtime() != null, contextData.isTimeOutWhenRing());
        contextData.setStatus(status);
        CallData callData = contextData.getCallData();
        HashMap<String, JSONArray> noticeresults = stateMachineStore.getNoticeresults();
        JSONArray jsonArray = noticeresults.get(contextData.getSubID());
        if(jsonArray==null) {
            jsonArray=new JSONArray();
            noticeresults.put(contextData.getSubID(),jsonArray);
        }
        JSONObject noticeresult = new JSONObject();
        noticeresult.put(Field.DN,contextData.getCalled());
        noticeresult.put(Field.STATUS,contextData.getStatus());
        noticeresult.put(Field.CALLTIME,contextData.getCalltime());
        noticeresult.put(Field.RINGTIME,contextData.getRingtime());
        noticeresult.put(Field.ANSWERTIME,contextData.getAnswertime());
        noticeresult.put(Field.RELEASETIME,contextData.getReleasetime());
        noticeresult.put(Field.PLAYTIMES,contextData.getPlayTimes());
        jsonArray.add(noticeresult);
    }
}
