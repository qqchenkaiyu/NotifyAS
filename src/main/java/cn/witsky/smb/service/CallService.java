package cn.witsky.smb.service;

import cn.witsky.smb.Constant.AsCons;
import cn.witsky.smb.Constant.Cons;
import cn.witsky.smb.Constant.NotificationType;
import cn.witsky.smb.StateMachine.Event;
import cn.witsky.smb.StateMachine.StateMachineForCollectAndConnect;
import cn.witsky.smb.annotation.TimeCost;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.core.utils.JavaUtils;
import cn.witsky.smb.pojo.CallData;
import cn.witsky.smb.pojo.ContextData;
import cn.witsky.smb.pojo.QueryData;
import cn.witsky.smb.pojo.SecretNOInfo;
import cn.witsky.smb.pojo.apk.ApkConfig;
import cn.witsky.smb.pojo.apk.ApkServiceFunction;
import cn.witsky.smb.pojo.apk.ApkVSF;
import cn.witsky.smb.pojo.communication.*;
import cn.witsky.smb.pojo.xdr.Cdr;
import cn.witsky.smb.pojo.xdr.Tdr;
import cn.witsky.smb.util.AsUtil;
import cn.witsky.smb.util.PrepareDataUtil;
import cn.witsky.smb.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 * @date 2017/7/18
 */
@Service
public class CallService {

    private static Logger logger = LoggerFactory.getLogger(CallService.class);
    private final StateMachineService stateMachineService;
    private final CacheService cacheService;
    private final RedisService redisService;
    private final String asRootUrl;
    private StringRedisTemplate stringRedisTemplate;
    private  Config config;
    private  XdrService xdrService;
    @Autowired
    public CallService(Environment environment,
                       StateMachineService stateMachineService, RedisService redisService, StringRedisTemplate stringRedisTemplate,
                       Config config,CacheService cacheService,XdrService xdrService) {
        this.stateMachineService = stateMachineService;
        this.cacheService = cacheService;
        this.asRootUrl = Joiner.on("").join("http://", environment.getProperty(CoreCons.SERVER_ADDRESS), ":",
                environment.getProperty(CoreCons.SERVER_PORT));
        this.redisService = redisService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.config = config;
        this.xdrService = xdrService;

    }


    @TimeCost
    public JSONObject serviceCallRequest(ContextData contextData) throws Exception {
        CallData callData = contextData.getCallData();
        contextData.setAsRootUrl(asRootUrl);
        //获取appkey信息 放入状态机
        //真实文件名
        contextData.setCallintime(System.currentTimeMillis());
        SecretNOInfo secretNOInfo = cacheService.getSecretNOInfo(callData.getDisplaynumber());
        if(secretNOInfo!=null)
            contextData.setAreaCode(secretNOInfo.getAreaCode());
        ApkConfig apkConfig = cacheService.getApkConfig(callData.getAppkey());
        ArrayList<String> calledpartylist = callData.getCalledpartylist();
        //需要把APKkey信息传入用来生成语音完整路径

        stateMachineService.createStateMachineList(contextData,calledpartylist,apkConfig);
        contextData.setSubID(JavaUtils.getRandomString(Int.THIRTY_TWO));
        JSONObject notiResult = ResultUtil.getNotiResult(contextData.getSubID());
        return notiResult;
    }
    public SlfRes serviceReleaseNotification(SlfReleaseNotification srnf) {
        String localSessionId = AsUtil.parseLocalSessionId(srnf.getSessionId());
        if (localSessionId == null) {
            logger.error("sessionId format error = {}", srnf);
            return AsUtil.getInvalidRequestParamsRes("sessionId format error");
        }
        Map<Object, Object> map = Maps.newHashMap();
        map.put("data", srnf);
        if (!stateMachineService.exist(localSessionId)) {
            logger.error("StateMachine is null,data = {}", srnf);
            return AsUtil.getExceptionHandleRes("StateMachine is null");
        }
        StateMachineForCollectAndConnect stateMachine = (StateMachineForCollectAndConnect) stateMachineService.get(localSessionId);
        if (!stateMachineService.fireEventToStateMachine(localSessionId, Event.receiveRelease, srnf)) {
            logger.error("StateMachine is null, data ={}", srnf);
            return AsUtil.getExceptionHandleRes("StateMachine is null");
        }
        return AsUtil.getOKRes();

    }
//根据release原因设置状态
    //
    //忙 拒接 无应答  关机 空号 停机



    public SlfRes serviceProgressNotification(SlfProgressNotification spnf) {
        String localSessionId = AsUtil.parseLocalSessionId(spnf.getSessionId());
        if (localSessionId == null) {
            logger.error("sessionId format error = {}", spnf);
            return AsUtil.getInvalidRequestParamsRes("sessionId format error");
        }
//      if(spnf==null){
//          return AsUtil.getInvalidRequestParamsRes("get Dgts  error");
//      }
        if (!stateMachineService.exist(localSessionId)) {
            logger.error("StateMachine is null,data = {}", spnf);
            return AsUtil.getExceptionHandleRes("StateMachine is null");
        }
        //摘机
        if (Objects.equals(spnf.getEventType(), NotificationType.ANM)) {
            stateMachineService.fireEventToStateMachine(localSessionId,Event.receiveConnnecting,spnf);

        } else if (Objects.equals(spnf.getEventType(), NotificationType.ACM)) {
            stateMachineService.fireEventToStateMachine(localSessionId,Event.receiveAlerting,spnf);
        } else if (Objects.equals(spnf.getEventType(), NotificationType.RING)) {
            stateMachineService.fireEventToStateMachine(localSessionId,Event.receiveRinging,spnf);
        } else {
            logger.info("unsupported_progress_type, data = {}", spnf.getEventType());

            return AsUtil.getExceptionHandleRes("unsupported_progress_type, data = "+ spnf.getEventType());

        }
        return AsUtil.getOKRes();
    }

    public SlfSessionTest sessionTest( SlfSessionTest sst) {
        String localSessionId = AsUtil.parseLocalSessionId(sst.getSessionId());
        if (localSessionId == null) {
            logger.error("formatSlfSessionTest.sessionId format error = {}", sst);
            sst.setCode(AsCons.SLFRES_CODE_FAIL);
            return sst;
        }
        if (stateMachineService.exist(localSessionId)) {
            sst.setCode(Int.ZERO);
        } else {
            sst.setCode(Int.ONE);
        }
        return sst;
    }


    public SlfRes serviceExecuteReport(SlfServiceExecuteReport report) {

        String localSessionId = AsUtil.parseLocalSessionId(report.getSessionId());
        if (localSessionId == null) {
            logger.error("sessionId format error = {}", report);
            return AsUtil.getInvalidRequestParamsRes("sessionId format error");
        }
        Map<Object, Object> map = Maps.newHashMap();
        map.put(Cons.DATA, report);
        if(!stateMachineService.exist(localSessionId)){
            return AsUtil.getExceptionHandleRes("StateMachine is null");
        }
        StateMachineForCollectAndConnect stateMachine = (StateMachineForCollectAndConnect) stateMachineService.get(localSessionId);
        ContextData contextData = stateMachine.getContextData();
        if(report.getDgts()==null){
            contextData.setDtmf(report.getDgts());
            stateMachineService.fireEventToStateMachine(localSessionId,Event.playEnd,map);
        }else if( Objects.equals(report.getDgts(),contextData.getCallData().getRepeatcondition())){
            //重放
            stateMachineService.fireEventToStateMachine(localSessionId, Event.ReleaseWithReplay, map);
        }else{
            // 输错了 直接结束
            stateMachineService.fireEventToStateMachine(localSessionId, Event.ReleaseWithNoplay, map);
        }
        return AsUtil.getOKRes();
    }


    public void HandelSMSIn(QueryData queryData) {
        SecretNOInfo secretNOInfo = cacheService.getSecretNOInfo(queryData.getQuery_Body().getSecretNo());
        Tdr  tdraCalled = PrepareDataUtil.getTDRACalled(queryData, config, secretNOInfo);
        xdrService.outputTDR(tdraCalled);
    }
    public  String HandelCallIn(QueryData queryData) throws Exception{
        SecretNOInfo secretNOInfo = cacheService.getSecretNOInfo(queryData.getQuery_Body().getSecretNo());
        Cdr cdraCalled;
        if(secretNOInfo==null){
            cdraCalled = PrepareDataUtil.getCDRACalled(queryData, config,null);
        }else {
            cdraCalled = PrepareDataUtil.getCDRACalled(queryData, config, secretNOInfo.getAreaCode());
        }
        xdrService.outputCDR(cdraCalled);

        ApkConfig apkConfig = cacheService.getApkConfig(queryData.getVendor_access_key());
        ApkServiceFunction serviceFunction = apkConfig.getServiceFunction();
        if(serviceFunction==null||serviceFunction.getVsf()==null||serviceFunction.getDfattone()==null||serviceFunction.getDfattone().getNoright()==null){
            logger.debug("query default tone error because Default tone is null or vsf is null ,appkey is"+queryData.getVendor_access_key());
            return  null;
        }
        ApkVSF vsf = serviceFunction.getVsf();
        String noright = serviceFunction.getDfattone().getNoright();
        //必须获得语音编码
        return  ResultUtil.getAnnFileName(vsf,noright);
    }
}
