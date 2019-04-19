package cn.witsky.smb.controller;

import cn.witsky.smb.annotation.TimeCost;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.exception.NoticeException;
import cn.witsky.smb.exception.QueryException;
import cn.witsky.smb.pojo.CallData;
import cn.witsky.smb.pojo.ContextData;
import cn.witsky.smb.pojo.QueryBody;
import cn.witsky.smb.pojo.QueryData;
import cn.witsky.smb.pojo.communication.SlfRes;
import cn.witsky.smb.pojo.communication.SlfServiceExecuteReport;
import cn.witsky.smb.pojo.communication.SlfServiceNotification;
import cn.witsky.smb.pojo.communication.SlfSessionTest;
import cn.witsky.smb.service.CallService;
import cn.witsky.smb.util.PrepareDataUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Administrator
 */
@RestController()
@RequestMapping("${witsky.astoslf-mapping-url}")
public class SlfController {

  private static final Logger logger = LoggerFactory.getLogger(SlfController.class);
  private final CallService callService;

  private final Config config;


  @Autowired
  public SlfController(CallService callService,
					    Config config) {
    this.callService = callService;
//        this.logOutPut = logOutPut;
   // this.cache = cache;
    this.config = config;
  }

//语音外呼接口
  @TimeCost
  @PostMapping("/voiceNotice")
  public JSONObject voiceNotice(@RequestBody @Valid CallData callData, BindingResult bindingResult) throws Exception {
    //参数检查
    if (bindingResult.hasErrors()) {
      throw  new NoticeException(bindingResult.getFieldError().getDefaultMessage());
    }
      ContextData data = new ContextData(config.getSlfurl(),  System.currentTimeMillis(),callData);
   // callService.serviceCallRequest(data)
    return callService.serviceCallRequest(data);
  }
//进展通知
  @RequestMapping("/serviceNotification")
  public Object serviceNotification(@RequestBody   @Valid SlfServiceNotification ssn, BindingResult bindingResult) throws Exception {
    //进行参数检查
    if (bindingResult.hasErrors()) {
      throw  new NoticeException(bindingResult.getFieldError().getDefaultMessage());
    }
    logger.info("收到serviceNotification   ssn="+JSONObject.toJSONString(ssn));
    SlfRes slfRes=null;
    int type = ssn.getType();
    if (type == Int.ZERO) {
      //进展
      slfRes = callService.serviceProgressNotification(ssn.getProgressNotification());
    } else if (type == Int.ONE) {
      //释放
      slfRes = callService.serviceReleaseNotification(ssn.getReleaseNotification());
    }
    return slfRes;
  }

//  @RequestInLog
  @RequestMapping("/sessionTest")
  public Object sessionTest( @RequestBody @Valid SlfSessionTest sst, BindingResult bindingResult) throws NoticeException {
    //进行参数检查
    if (bindingResult.hasErrors()) {
      throw  new NoticeException(bindingResult.getFieldError().getDefaultMessage());
    }

    return callService.sessionTest(sst);
  }

//  @RequestInLog
  @RequestMapping("/serviceExecuteReport")
  public Object serviceExecuteReport( @RequestBody @Valid SlfServiceExecuteReport report, BindingResult bindingResult) throws NoticeException {
    //进行参数检查
    if (bindingResult.hasErrors()) {
      throw  new NoticeException(bindingResult.getFieldError().getDefaultMessage());
    }
    logger.info("SlfServiceExecuteReport   report="+JSONObject.toJSONString(report));

    return callService.serviceExecuteReport(report);
  }

  /**
   * 处理语音和短信呼入流程
   * @param
   * @return
   */
  @RequestMapping("/control")
  public Object query_session_control(@Valid  QueryData queryData,BindingResult bindingResult )throws Exception {
//参数检查
    if (bindingResult.hasErrors()) {
      throw  new QueryException(bindingResult.getFieldError().getDefaultMessage());
    }
    QueryBody queryBody = JSONObject.parseObject(queryData.getRequest_body(), QueryBody.class);
    queryData.setQuery_Body(queryBody);
    //根据语音和短信分开处理
      if(Objects.equals(queryBody.getRecordType(),"CALL")){
        String anncode = callService.HandelCallIn(queryData);
        return PrepareDataUtil.getQuerySultForCallReject(anncode,"notifyAS not support CALL IN");
      }else if(Objects.equals(queryBody.getRecordType(),"SMS")){
        callService.HandelSMSIn(queryData);
        return PrepareDataUtil.getQuerySultForSMSReject("notifyAS not support SMS IN");
      }else{
       return PrepareDataUtil.getQuerySultForERR("unsupport recordtype");
      }
  }
}