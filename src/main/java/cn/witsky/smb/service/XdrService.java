package cn.witsky.smb.service;


import cn.witsky.smb.Constant.RecordMode;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.constant.Str;
import cn.witsky.smb.core.utils.JavaUtils;
import cn.witsky.smb.pojo.xdr.Cdr;
import cn.witsky.smb.pojo.xdr.QueryDR;
import cn.witsky.smb.pojo.xdr.Tdr;
import com.google.common.collect.Maps;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author HuangYX
 * @date 2018/9/26 9:17
 */
@Service
@RefreshScope
public class XdrService {

  private static final Logger logger = LoggerFactory.getLogger(XdrService.class);
  private static final String version = "云通信小号系统 详单字段V1.1_1108A.xls";
  private static final DateTimeFormatter DATE_FORMATTER_YYYYMMDDHHMMSSSSS = DateTimeFormat.forPattern(
      "yyyyMMddHHmmssSSS");
  private static final String DOUHAO = ",";

  private static final String JOURNAL_NAME_CDR = "CDR";
  private static final String JOURNAL_ID_CDR = "CDR";

  private static final String JOURNAL_NAME_TDR = "TDR";
  private static final String JOURNAL_ID_TDR = "TDR";

  private static final String JOURNAL_NAME_QDR = "QueryDR";
  private static final String JOURNAL_ID_QDR = "QDR";

  public static final String SERVICE_SOURCE_CALL = "0";
  public static final String SERVICE_SOURCE_SMS = "1";

  private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormat.forPattern(
      "yyyy-MM-dd HH:mm:ss.SSS");

  private final XdrJournalWriter xdrJournalWriter;
  private final Config config;

  @Autowired
  public XdrService(XdrJournalWriter xdrJournalWriter, Config config) {
    this.xdrJournalWriter = xdrJournalWriter;
    this.config = config;
    HashMap<String, String> types = Maps.newHashMap();
    types.put(JOURNAL_NAME_CDR, JOURNAL_ID_CDR);
    types.put(JOURNAL_NAME_TDR, JOURNAL_ID_TDR);
    types.put(JOURNAL_NAME_QDR, JOURNAL_ID_QDR);
    this.xdrJournalWriter.addTypes(types);
  }

  public void outputCDR(Cdr cdr) {
    if (cdr.getAreacode() == null) {
      cdr.setAreacode(Str.ZERO);
    }
    if (cdr.getSvclevel() == null) {
      cdr.setSvclevel(Str.ZERO);
    }

    if (cdr.getRecordmode() == null) {
      cdr.setRecordmode(RecordMode.NO_REC);
    }

    logger.info("输出 Cdr = {} ", cdr);
    xdrJournalWriter.write(JOURNAL_NAME_CDR, createCdrString(cdr));
  //  logger.info(createCdrString(cdr));
  }

  /**
   * 根据版本输出
   *
   * @param tdr tdr文件
   */
  public void outputTDR(Tdr tdr) {
    if (tdr.getAreacode() == null) {
      tdr.setAreacode(Str.ZERO);
    }
    if (tdr.getSvclevel() == null) {
      tdr.setSvclevel(Str.ZERO);
    }
    logger.info("输出 tdr = {}", tdr);
    xdrJournalWriter.write(JOURNAL_NAME_TDR, createTdrString(tdr));
  }


  public void outputQueryDr(QueryDR q) {
    logger.info("输出 qdr = {}", q);
    xdrJournalWriter.write(JOURNAL_NAME_QDR, createQdrString(q));
  }


  private String createCdrString(Cdr cdr) {
    return JavaUtils.concatenateStrings(new String[]{
        //记录时间戳	time
        Optional.ofNullable(cdr.getTime()).orElse(DATE_FORMATTER_YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis())),
        //实例标识	elementid
        replaceCommaWithVerticalLine(cdr.getElementid()),
        //服务平台标识	platformid	Octet String (decimal)	String		1		"服务平台的标识 比如：短信TDR的ALI_CCF_A"
        replaceCommaWithVerticalLine(cdr.getPlatformid()),
        //话单标识	xdrid
        replaceCommaWithVerticalLine(cdr.getXdrid()),
        //话单类型	type
        replaceCommaWithVerticalLine(cdr.getXdrType()),
        //服务appkey	appkey
        replaceCommaWithVerticalLine(cdr.getAppkey()),
        //绑定ID	subid
        replaceCommaWithVerticalLine(cdr.getSubid()),
        //通话标识	callid
        replaceCommaWithVerticalLine(cdr.getCallid()),
        //呼入来显号码	callinDisplaydn
        replaceCommaWithVerticalLine(cdr.getCallinDisplaydn()),
        //呼入被叫号码	callinTermindn
        replaceCommaWithVerticalLine(cdr.getCallinTermindn()),
        //呼出显示号码	calloutDisplaydn
        replaceCommaWithVerticalLine(cdr.getCalloutDisplaydn()),
        //呼出对端号码	calloutTermindn
        replaceCommaWithVerticalLine(cdr.getCalloutTermindn()),
        //呼入原始被叫号码	callinRedirectdn
        replaceCommaWithVerticalLine(cdr.getCallinRedirectdn()),
        //按键号码	callin_dtmf
        replaceCommaWithVerticalLine(cdr.getCallinDtmf()),
        //服务类型	servicetype
        replaceCommaWithVerticalLine(cdr.getServicetype()),
        //呼叫方式	mode
        replaceCommaWithVerticalLine(cdr.getMode()),
        //呼叫类型	type
        replaceCommaWithVerticalLine(cdr.getType()),
        //呼叫接续	proceeding
        replaceCommaWithVerticalLine(cdr.getProceeding()),
        //虚拟X号码	callinSecretNO
        replaceCommaWithVerticalLine(cdr.getTelX()),
        //虚拟Y号码	telY
        replaceCommaWithVerticalLine(cdr.getTelY()),
        //绑定A号码	axybNa
        replaceCommaWithVerticalLine(cdr.getTelA()),
        //绑定B号码	telB
        replaceCommaWithVerticalLine(cdr.getTelB()),
        //呼入开始时间	callintime
        replaceCommaWithVerticalLine(cdr.getCallintime()),
        //呼出开始时间	callouttime
        replaceCommaWithVerticalLine(cdr.getCallouttime()),
        //呼叫确认时间	alertingtime
        replaceCommaWithVerticalLine(cdr.getAlertingtime()),
        //振铃开始时间	ringingtime
        replaceCommaWithVerticalLine(cdr.getRingingtime()),
        //通话开始时间	starttime
        replaceCommaWithVerticalLine(cdr.getStarttime()),
        //通话结束时间	releasetime
        replaceCommaWithVerticalLine(cdr.getReleasetime()),
        //通话时长	duringtime
        replaceCommaWithVerticalLine(cdr.getDuringtime()),
        //释放原因	release_cause
        replaceCommaWithVerticalLine(cdr.getReleaseCause()),
        //释放方向	release_dir
        replaceCommaWithVerticalLine(cdr.getReleaseDir()),
        //被叫有条件呼转号码	conditionRedirectDN
        replaceCommaWithVerticalLine(cdr.getConditionRedirectDN()),
        //被叫有条件呼转原因	conditionRedirectCause
        replaceCommaWithVerticalLine(cdr.getConditionRedirectCause()),
        //触发二次呼转标识	secondredir
        replaceCommaWithVerticalLine(cdr.getSecondredir()),
        //录音模式	recordmode
        replaceCommaWithVerticalLine(cdr.getRecordmode()),
        //录音URL	recordurl
        replaceCommaWithVerticalLine(cdr.getRecordurl()),
        //订单请求ID	bandrequestid
        replaceCommaWithVerticalLine(cdr.getBandrequestid()),
        //订单请求标记	bandremark
        replaceCommaWithVerticalLine(cdr.getBandremark()),
        //组ID	groupid
        replaceCommaWithVerticalLine(cdr.getGroupid()),
        //关联区号	areacode	Octet String (decimal)	String		1		关联的区号，没有填0
        replaceCommaWithVerticalLine(cdr.getAreacode()),
        //业务等级	svclevel	Octet String (decimal)	String		1		"当前的呼叫等级，默认为0.阿里中心化时，响应接口中携带"
        replaceCommaWithVerticalLine(cdr.getSvclevel())}, DOUHAO, true);
  }

  private String createTdrString(Tdr tdr) {

    return JavaUtils.concatenateStrings(new String[]{
        //记录时间戳	time	Octet String （decimal）	String		1		YYYYMMDDhhmmssNNN
        Optional.ofNullable(tdr.getTime()).orElse(DATE_FORMATTER_YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis())),
        //实例标识	elementid	Octet String (decimal)	String		1		自身标识
        replaceCommaWithVerticalLine(tdr.getElementid()),
        //服务平台标识	platformid	Octet String (decimal)	String		1		"服务平台的标识 比如：短信TDR的ALI_CCF_A"
        replaceCommaWithVerticalLine(tdr.getPlatformid()),
        //话单标识	xdrid	Octet String （decimal）	String		1		话单的ID
        replaceCommaWithVerticalLine(tdr.getXdrid()),
        //话单类型	type	Octet String (decimal)	String		1		11：小号短信业务
        replaceCommaWithVerticalLine(tdr.getXdrType()),
        //服务appkey	appkey	Octet String （decimal）	String		1		X号码分配的appkey
        replaceCommaWithVerticalLine(tdr.getAppkey()),
        //绑定ID	subid	Octet String (decimal)	String		1		无绑定关系为空
        replaceCommaWithVerticalLine(tdr.getSubid()),
        //通话标识	callid	Octet String (decimal)	String		1		同接口定义
        replaceCommaWithVerticalLine(tdr.getCallid()),
        //短信接收来显号码	deliveryDisplaydn	Octet String （decimal）	String		1		入局短信时的主叫号码
        replaceCommaWithVerticalLine(tdr.getDeliveryDisplaydn()),
        //短信接收终端号码	deliveryTermindn	Octet String （decimal）	String		1		入局短信时的被叫号码
        replaceCommaWithVerticalLine(tdr.getDeliveryTermindn()),
        //短信发送显示号码	submitDisplaydn	Octet String (decimal)	String		1		出局短信时的主叫号码
        replaceCommaWithVerticalLine(tdr.getSubmitDisplaydn()),
        //短信发送终端号码	submitTermindn	Octet String (decimal)	String		1		出局短信时的被叫号码
        replaceCommaWithVerticalLine(tdr.getSubmitTermindn()),
        //短信内容提取扩展号码	deliveryExtsubdn	Octet String (decimal)	String		1		入局短信时的内容中携带的扩展号码
        replaceCommaWithVerticalLine(tdr.getDeliveryExtsubdn()),
        //服务类型	servicetype	Octet String (decimal)	String		1		比如：“AXB”，“AXN”，“AXYB”“ALIC”
        replaceCommaWithVerticalLine(tdr.getServicetype()),
        //短信方式	mode	Octet String (decimal)	String		1
        //"1：点对点方式短信
        //2：在线发送短信
        //3：在线接收短信
        //4：点对点+在线接收短信
        //5：点对点方式短信状态报告
        //6：在线接收短信状态报告
        //9：接收拦截"
        replaceCommaWithVerticalLine(tdr.getMode()),
        //短信类型	type	Octet String (decimal)	String		1
        //"0：未知
        //1：A主叫
        //2：A被叫"
        replaceCommaWithVerticalLine(tdr.getType()),
        //短信接续	proceeding	Octet String (decimal)	String		1
        //"x：接续类取值
        //0：正常接续
        //1：用户白名单
        //1x：拦截类：
        //10：系统黑名单
        //11：用户黑名单"
        replaceCommaWithVerticalLine(tdr.getProceeding()),
        //虚拟X号码	callinSecretNO	Octet String (decimal)	String		1		中间虚拟号码
        replaceCommaWithVerticalLine(tdr.getTelX()),
        //虚拟Y号码	telY	Octet String (decimal)	String		1		中间虚拟号码
        replaceCommaWithVerticalLine(tdr.getTelY()),
        //绑定A号码	axybNa	Octet String (decimal)	String		1		绑定的A号码
        replaceCommaWithVerticalLine(tdr.getTelA()),
        //绑定B号码	telB	Octet String （decimal）	String		1		绑定的B号码或other号码
        replaceCommaWithVerticalLine(tdr.getTelB()),
        //收到时间	deliverytime	Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
        replaceCommaWithVerticalLine(tdr.getDeliverytime()),
        //发送时间	submittime	Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
        replaceCommaWithVerticalLine(tdr.getSubmittime()),
        //发送结果	submitresult	Octet String (decimal)	number		1		"0：成功
        //            非0：不成功"
        replaceCommaWithVerticalLine(tdr.getSubmitresult()),
        //短信回执	smreport	Octet String (decimal)	number		1		"空：不需要短信回执
        //0-255：需要短信回执，短信回执的reference id"
        replaceCommaWithVerticalLine(tdr.getSmreport()),
        //短信接收结果	recipientresult	Octet String (decimal)	number		1		"0：接收成功
        //非0：不成功"
        replaceCommaWithVerticalLine(tdr.getRecipientresult()),
        //短信接收时间	recipientts	Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
        replaceCommaWithVerticalLine(tdr.getRecipientts()),
        //短信长度	msglength	Octet String (decimal)	String		1		短信长度
        replaceCommaWithVerticalLine(tdr.getMsglength()),
        //短信条数	msgcount	Octet String (decimal)	String		1		短信条数
        replaceCommaWithVerticalLine(tdr.getMsgcount()),
        //短信中心地址	smcid	Octet String 	String		1		短信中心地址
        replaceCommaWithVerticalLine(tdr.getSmcid()),
        //订单请求ID	bandrequestid	Octet String (decimal)	String		1		绑定订单中的RequestId
        replaceCommaWithVerticalLine(tdr.getBandrequestid()),
        //订单请求标记	bandremark	Octet String (decimal)	String		1		绑定订单中的Remark
        replaceCommaWithVerticalLine(tdr.getBandremark()),
        //组ID	groupid	Octet String (decimal)	String		1		组号，AXG模式使用
        replaceCommaWithVerticalLine(tdr.getGroupid()),
        //关联区号	areacode	Octet String (decimal)	String		1		关联的区号，没有填0
        replaceCommaWithVerticalLine(tdr.getAreacode()),
        //业务等级	svclevel	Octet String (decimal)	String		1		"当前的呼叫等级，默认为0.阿里中心化时，响应接口中携带"
        replaceCommaWithVerticalLine(tdr.getSvclevel())

    }, DOUHAO, true);
  }

  private String createQdrString(QueryDR qdr) {

    return JavaUtils.concatenateStrings(new String[]{
        //记录时间戳	time	Octet String （decimal）	String		1		YYYYMMDDhhmmssNNN
        Optional.ofNullable(qdr.getTime()).orElse(DATE_FORMATTER_YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis())),
        //实例标识	elementid	Octet String (decimal)	String		1		自身标识
        replaceCommaWithVerticalLine(qdr.getElementId()),
        //服务平台标识	platformid	Octet String (decimal)	String		1		"服务平台的标识
        //没有，则为空"
        replaceCommaWithVerticalLine(qdr.getPlatformId()),
        //话单标识	xdrid	Octet String （decimal）	String		1		话单的ID
        replaceCommaWithVerticalLine(qdr.getXdrId()),
        //话单类型	type	Octet String (decimal)	String		1		15：主动查询业务
        replaceCommaWithVerticalLine(qdr.getType()),
        //服务appkey	appkey	Octet String （decimal）	String		1		X号码分配的appkey
        replaceCommaWithVerticalLine(qdr.getAppkey()),
        //查询必选项
        //绑定ID	subid	Octet String (decimal)	String				无绑定关系为空
        replaceCommaWithVerticalLine(qdr.getSubId()),
        //通话标识	callid	Octet String (decimal)	String		1		同接口定义
        replaceCommaWithVerticalLine(qdr.getCallid()),
        //查询计数	queryseq	Octet String (decimal)	number		1		"显示当前计数的序号，从1开始计数。
        //代理模块如果不确定序号，统一填0"
        replaceCommaWithVerticalLine(String.valueOf(qdr.getQuerySeq())),
        //请求时间	requesttime	Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
        replaceCommaWithVerticalLine(qdr.getRequestTime()),
        //响应时间	responsetime	Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
        replaceCommaWithVerticalLine(qdr.getResponseTime()),
        //响应代码	code	Octet String (decimal)	String		1		结果code
        replaceCommaWithVerticalLine(qdr.getCode()),
        //代码原因	description	Octet String (decimal)	String		1		结果描述
        replaceCommaWithVerticalLine(qdr.getDescription()),
        //业务来源	servicesource	Octet String (decimal)	String		1		"0：呼叫
        //1：短信"
        replaceCommaWithVerticalLine(qdr.getServiceSource()),
        //来显号码	callin_displaydn	Octet String 	String		1		入局呼叫时的主叫号码
        replaceCommaWithVerticalLine(qdr.getCallInDisplayDn()),
        //接入号码	callin_termindn	Octet String 	String		1		入局呼叫时的被叫号码
        replaceCommaWithVerticalLine(qdr.getCallInTerminDn()),
        //呼入原始被叫号码	callin_redirectdn	Octet String 	String		1		入局呼叫时的原始号码
        replaceCommaWithVerticalLine(qdr.getCallInRedirectDn()),
        //指示动作	action	Octet String (decimal)	String		1		"0：CONTINUE
        //1：REJECT
        //2：IVR"
        replaceCommaWithVerticalLine(qdr.getAction()),
        //服务类型	servicetype	Octet String (decimal)	String		1		"“AXB”，“AXN”，“AXYB”，“AXG”，
        //“ALIC”：阿里中心化；
        //“SOP”：业务开放
        //“SPX”：业务代理，如链家"
        replaceCommaWithVerticalLine(qdr.getServiceType()),
        //去呼显号码	callout_displaydn	Octet String 	String		1		出局呼叫时的主叫号码
        replaceCommaWithVerticalLine(qdr.getCallOutDisplayDn()),
        //去呼目的号码	callout_termindn	Octet String 	String		1		出局呼叫时的被叫号码
        replaceCommaWithVerticalLine(qdr.getCallOutTerminDn()),
        //去呼携带呼转号码	callout_redirectdn	Octet String 	String		1		出局呼叫时的原始号码
        replaceCommaWithVerticalLine(qdr.getCalloutRedirectDn()),
        //录音标志	need_record	Octet String (decimal)	String		1		"0：不需要
        //1：摘机录音"
        replaceCommaWithVerticalLine(String.valueOf(qdr.getNeedRecord())),
        //短信通道	sm_channel	Octet String (decimal)	String		1		"0：正常点对点
        //1：短信托收
        //2：丢失"
        replaceCommaWithVerticalLine(qdr.getSmChannel()),
        //主叫侧放音编码	caller_playcode	Octet String 	String		1		多个提示音通过“|”分割
        replaceCommaWithVerticalLine(qdr.getCallerPlayCode()),
        //被叫侧放音编码	called_playcode	Octet String 	String		1		多个提示音通过“|”分割
        replaceCommaWithVerticalLine(qdr.getCalledPlayCode()),
        //短信中心地址	smcid	Octet String 	String		1		短信中心地址
        replaceCommaWithVerticalLine(qdr.getSmcId())

    }, ",", true);
  }



  public static String getVersion() {
    return version;
  }

  private static final String COMMA = ",";
  private static final String VERTICAL_LINE = "|";

  private String replaceCommaWithVerticalLine(String source) {
    if (StringUtils.isEmpty(source) || !source.contains(COMMA)) {
      return source;
    }
    return source.replaceAll(COMMA, VERTICAL_LINE);
  }
}
