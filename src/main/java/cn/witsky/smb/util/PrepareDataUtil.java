package cn.witsky.smb.util;

import cn.witsky.smb.Constant.Field;
import cn.witsky.smb.Constant.Mode;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.config.PhonePrefixer;
import cn.witsky.smb.core.constant.DateFormaters;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.core.constant.Str;
import cn.witsky.smb.pojo.*;
import cn.witsky.smb.pojo.apk.ApkConfig;
import cn.witsky.smb.pojo.apk.ApkVSF;
import cn.witsky.smb.pojo.xdr.Cdr;
import cn.witsky.smb.pojo.xdr.Tdr;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
public class PrepareDataUtil {

private static PhonePrefixer prefixer=new PhonePrefixer();

	public PrepareDataUtil() {
	}

	public static JSONObject getBodyForSlfConnect(ContextData contextData, Config config, ApkConfig apkConfig) {
		JSONObject Data = new JSONObject();
		CallData callData = contextData.getCallData();

		Data.put("callId", contextData.getCallID());
		Data.put("sessionId", contextData.getSessionId());
		Data.put("invokeId", 1);
		Data.put("displayDn", contextData.getCaller());
		Data.put("calledDn", contextData.getCalled());
		//来显为真实号码时，被叫为移动时，用强控制来提高呼叫成功率
		Data.put("callHandle",1);
		int[] serviceFlag = {config.getAstoslfServiceflagAcm(), config.getAstoslfServiceflagAnm(),
				config.getAstoslfServiceflagPresskey(), config.getAstoslfServiceflagMedia(),
				config.getAstoslfServiceflagCpg()};
		Data.put("serviceFlag",serviceFlag);

//		JSONObject serviceAnnCodeInfo = new JSONObject();
//		serviceAnnCodeInfo.put("calledAnnCode",getcalledAnnCode(contextData, apkConfig));
//		Data.put("serviceAnnCodeInfo", serviceAnnCodeInfo);

		Data.put("codec", config.getCodecForOptimizedFormat());
		Data.put("handleLevel", config.getHandleLevel());
		return  Data;

	}

	public static JSONObject getBodyForSlfDisConnect(ContextData contextData, Config config, ApkConfig apkConfig, boolean replay) {
		JSONObject Data = new JSONObject();
		Data.put("callId", contextData.getCallID());
		Data.put("sessionId", contextData.getSessionId());
		Data.put("invokeId", 0);//这里该写什么
		JSONObject Cause = new JSONObject();
		Cause.put("plan",0);
		Cause.put("reason",contextData.getCause());
		Data.put("Cause", Cause);
		JSONObject serviceAnnCodeInfo = new JSONObject();
		if(replay) {
			serviceAnnCodeInfo.put("calledAnnCode", getcalledAnnCode(contextData, apkConfig));
			Data.put("serviceAnnCodeInfo", serviceAnnCodeInfo);
		}
		return  Data;
	}
	public static JSONObject getBodyForSlfTest(ContextData contextData, Config config, ApkConfig apkConfig) {
		JSONObject Data = new JSONObject();
		Data.put("callId", contextData.getCallID());
		Data.put("sessionId", contextData.getSessionId());
		Data.put("invokeId", 0);//这里该写什么
		JSONObject Cause = new JSONObject();
		Cause.put("plan",0);
		Cause.put("reason",31);
		Data.put("Cause", Cause);
		JSONObject serviceAnnCodeInfo = new JSONObject();
		if(true) {
			serviceAnnCodeInfo.put("calledAnnCode", getcalledAnnCode(contextData, apkConfig));
			Data.put("serviceAnnCodeInfo", serviceAnnCodeInfo);
		}
		return  Data;
	}
	public static JSONArray getcalledAnnCode(ContextData contextData, ApkConfig apkConfig) {
		JSONArray calledAnnCode = new JSONArray();
		String raw = contextData.getCallData().getVoicecode();
		if(apkConfig==null) return  calledAnnCode;
		ApkVSF vsf = apkConfig.getServiceFunction().getVsf();
		if(raw!=null) {
			for (String fileName : raw.split(",")) {
				JSONObject fileinfo = new JSONObject();
				fileinfo.put("fileType", 1);
				fileinfo.put("ipAddressOfServer", 0);
				fileinfo.put("portOfServer", 0);
				fileinfo.put("forceLoading", 0);
			//	fileinfo.put("fileName", vsf.getVspath()+"/"+fileName+"."+vsf.getVsext());
				fileinfo.put("fileName", ResultUtil.getAnnFileName(vsf,fileName));

				calledAnnCode.add(fileinfo);
			}
		}
		return  calledAnnCode;
	}
	//去哪里找是否重听提示音
	public static JSONArray getcalledIVRAnnCode(ContextData contextData, ApkConfig apkConfig) {
		JSONArray calledAnnCode = new JSONArray();
		String raw = contextData.getCallData().getRepeattone();
		ApkVSF vsf = apkConfig.getServiceFunction().getVsf();
		if(raw!=null) {
			for (String fileName : raw.split(",")) {
				JSONObject fileinfo = new JSONObject();
				fileinfo.put("fileType", 1);
				fileinfo.put("ipAddressOfServer", 0);
				fileinfo.put("portOfServer", 0);
				fileinfo.put("forceLoading", 0);
				fileinfo.put("fileName", fileName);
				calledAnnCode.add(fileinfo);
			}
		}
		return  calledAnnCode;
	}

	public static JSONObject getBodyForSlfIVR(ContextData contextData, Config config, ApkConfig apkConfig) {
		JSONObject Data = new JSONObject();
		CallData callData = contextData.getCallData();
		Data.put("callId", contextData.getCallID());
		Data.put("sessionId", contextData.getSessionId());

		JSONObject serviceAnnCodeInfo = new JSONObject();
		serviceAnnCodeInfo.put("calledAnnCode",getcalledIVRAnnCode(contextData, apkConfig));
		Data.put("serviceAnnCodeInfo", serviceAnnCodeInfo);

		JSONObject dgtsEventInfo = new JSONObject();
		dgtsEventInfo.put("control",config.getDgtsControl());
		dgtsEventInfo.put("minCollect",config.getDgtsMinCollect());
		dgtsEventInfo.put("maxCollect",config.getDgtsMaxCollect());
		dgtsEventInfo.put("maxInteractTime",config.getDgtsMaxInteractiveTime());
		dgtsEventInfo.put("initInterDgtTime",config.getDgtsInitInteractiveTime());
		dgtsEventInfo.put("normInterDgtTime",config.getDgtsInterDigitTime());
		dgtsEventInfo.put("enterDgtMask",config.getDgtsEndMask());
		dgtsEventInfo.put("digitCollectionType",config.getDgtsCollectType());

		Data.put("dgtsEventInfo", dgtsEventInfo);
		Pattern specified_phone_pattern = Pattern.compile("^(86)?(" + prefixer.getSpecified() + ")");
		Integer codec;
		if (specified_phone_pattern.matcher(callData.getDisplaynumber()).lookingAt()){
			codec= config.getCodecForSpecifiedFormat();
		}else {
			codec= config.getCodecForCollectFormat();
		}
		Data.put("codec", codec);
		return  Data;
	}

	public static JSONObject getBodyForSlfPA(ContextData contextData, Config config, ApkConfig apkConfig) {
		JSONObject Data = new JSONObject();
		CallData callData = contextData.getCallData();
		Data.put("callId", contextData.getCallID());
		Data.put("sessionId", contextData.getSessionId());

		JSONObject serviceAnnCodeInfo = new JSONObject();
		serviceAnnCodeInfo.put("calledAnnCode",getcalledAnnCode(contextData, apkConfig));
		Data.put("serviceAnnCodeInfo", serviceAnnCodeInfo);
		Pattern specified_phone_pattern = Pattern.compile("^(86)?(" + prefixer.getSpecified() + ")");
		Integer codec;
		if (specified_phone_pattern.matcher(callData.getDisplaynumber()).lookingAt()){
			codec= config.getCodecForSpecifiedFormat();
		}else {
			codec= config.getCodecForCollectFormat();
		}
		Data.put("codec", codec);
		return  Data;
	}

	public static JSONObject getBodyForReport(ContextData contextData, JSONArray jsonArray) {
		JSONObject Data = new JSONObject();
		CallData callData = contextData.getCallData();
		Data.put(Field.APPKEY, callData.getAppkey());
		Data.put(Field.TS, callData.getTs());
		Data.put(Field.MSGDGT, callData.getMsgdgt());
		Data.put(Field.REQUESTID, callData.getRequestid());
		Data.put(Field.TASKID, callData.getTaskid());
		Data.put(Field.SUBID, contextData.getSubID());

		Data.put(Field.NOTICERESULT, jsonArray);

		return  Data;
	}

	public static JSONObject getQuerySultForSMSReject(String err_message) {
		JSONObject Data = new JSONObject();
		Data.put(Field.CODE, "0");
		Data.put(Field.MESSAGE,err_message );
		Data.put(Field.SUCCESS, true);

		JSONObject module = new JSONObject();
		module.put(Field.CONTROLOPERATE,Field.REJECT);
		Data.put(Field.MODULE, module);
		return  Data;
	}

	public static Object getQuerySultForERR(String err_message) {
		JSONObject Data = new JSONObject();
		Data.put(Field.CODE, "1");
		Data.put(Field.MESSAGE, err_message);
		Data.put(Field.SUCCESS, true);

		JSONObject module = new JSONObject();
		module.put(Field.CONTROLOPERATE,Field.REJECT);
		Data.put(Field.MODULE, module);
		return  Data;
	}

	public static Object getQuerySultForCallReject(String anncode,String err_message) {
		JSONObject Data = new JSONObject();
		Data.put(Field.CODE, "1");
		Data.put(Field.MESSAGE, err_message);
		Data.put(Field.SUCCESS, true);
		JSONObject module = new JSONObject();
		module.put(Field.CONTROLOPERATE,Field.REJECT);
		module.put(Field.CALLNOPLAYCODE,anncode);
		Data.put(Field.MODULE, module);
		return  Data;
	}

	public static Cdr getCDRACaller(ContextData contextData,Config config) {
		CallData callData = contextData.getCallData();
		Cdr cdr = new Cdr(
				//记录时间戳 		YYYYMMDDhhmmssNNN
				DateFormaters.YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis()),
				//实例标识		自身标识
				config.getModuleInstanceId(),
				//服务平台标识
				config.getPlatformID(),
				//话单标识	xdrid	话单的ID
				contextData.getLocalSessionId(),
				//话单类型	type	1：小号呼叫业务
				Mode.YYTZ,
				//服务appkey	appkey	X号码分配的appkey
				callData.getAppkey(),
				//绑定ID	subid		无绑定关系为空
				contextData.getSubID(),
				//通话标识	callid	同接口定义
				contextData.getCallID(),
				//呼入来显号码	callin_displaydn	入局呼叫时的主叫号码
				null,
				//呼入被叫号码	callin_displaydn	入局呼叫时的被叫号码
				null,
				//呼出显示号码	callout_displaydn	出局呼叫时的主叫号码
				contextData.getCaller(),
				//呼出对端号码	callin_termindn		出局呼叫时的被叫号码
				contextData.getCalled(),
				//呼入原始被叫号码	callin_redirectdn	入局呼叫时的原始号码
				null,
				//按键号码?
				contextData.getDtmf(),
				//服务类型	servicetype	比如：“AXB”，“AXN”，“AXYB”“ALIC”
				Mode.NOTICE,
				//呼叫方式	mode "0：大网直呼;1：在线呼出;2：DTMF呼出;3：呼叫转接;11：双呼"
				Mode.ZJHC,
				//呼叫类型	type	"0：未知;1：A主叫;2：A被叫"
				"1",
				//呼叫接续	proceeding	"x：接续类取值;0：正常接续;1：用户白名单;1x：拦截类：;10：系统黑名单11：用户黑名单"
				"0",
				//虚拟X号码	callinSecretNO  中间虚拟号码
				contextData.getCaller(),
				//虚拟Y号码	telY  中间虚拟号码
				null,
				//绑定A号码	axybNa  绑定的A号码
				contextData.getCalled(),
				//绑定B号码	telB  绑定的B号码或other号码（包括分机号）
				null,
				//呼入开始时间	callintime	 yyyy-MM-dd HH:mm:ss
				contextData.getCallintime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getCallintime()),
				//呼出开始时间	callouttime	 yyyy-MM-dd HH:mm:ss
				contextData.getCallouttime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getCallouttime()),
				//呼叫确认时间    alertingtime yyyy-MM-dd HH:mm:ss
				contextData.getAlertTime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getAlertTime()),
				//振铃开始时间	ringingtime  yyyy-MM-dd HH:mm:ss
				contextData.getRingingTime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getRingingTime()),
				//通话开始时间	starttime    yyyy-MM-dd HH:mm:ss
				contextData.getStarttime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getStarttime()),
				//通话结束时间	releasetime	 yyyy-MM-dd HH:mm:ss
				contextData.getEndtime()==0?null:DateFormaters.YYYY_MM_DD_HH_MM_SS.print(contextData.getEndtime()),
				//通话时长	duringtime  有通话时的有效通话时长
				String.valueOf(contextData.getDurationTime()),
				//释放原因	release_cause
				String.valueOf(contextData.getCause()),
				//释放方向	release_dir
				String.valueOf(contextData.getDir()),
				//被叫有条件呼转号码	conditionRedirectDN
				null,
				//被叫有条件呼转原因	conditionRedirectCause
				null,
				//触发二次呼转标识	secondredir
				null,
				//录音模式	recordmode	"0：不录音;1：接通录音;2：振铃录音"
				null,
				//录音URL	recordurl
				null,
				//订单请求ID	bindrequestid
				callData.getRequestid(),
				//订单请求标记	bindremark	绑定订单中的Remark
				callData.getContent(),
				//组ID	groupid	 组号，AXG模式使用
				callData.getTaskid(),
				//关联区号
				contextData.getAreaCode(),
				//业务等级
				Str.ZERO
		);
		return  cdr;
	}
	public static Cdr getCDRACalled(QueryData queryData, Config config,String Areacode) {
		//CallData callData = contextData.getCallData();
		QueryBody query_body = queryData.getQuery_Body();
		Cdr cdr = new Cdr();
		cdr.setTime(DateFormaters.YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis()))
				.setElementid(config.getModuleInstanceId())
				.setPlatformid(config.getPlatformID())
				.setXdrid(query_body.getCallId())
				.setType(Mode.XHZXH)
				.setAppkey(queryData.getVendor_access_key())
				.setCallid(query_body.getCallId())
				.setCallinDisplaydn(query_body.getCallNo())
				.setCallinTermindn(query_body.getSecretNo())
				.setServicetype(Mode.WTC)
				.setMode(Mode.DWZH)
				.setType("2")
				.setTelX(query_body.getSecretNo())
				.setTelB(query_body.getCallNo())
				.setReleaseCause("31")
				.setReleaseDir(String.valueOf(Mode.PTSF))
				.setAreacode(Areacode)
				.setSvclevel(Str.ZERO);
return cdr;
	}

	public static Tdr getTDRACalled(QueryData queryData, Config config, SecretNOInfo secretNOInfo) {
		QueryBody query_body = queryData.getQuery_Body();
		Tdr tdr = new Tdr();
		//记录时间戳	time	Octet String （decimal）	String		1		YYYYMMDDhhmmssNNN
		tdr.setTime(DateFormaters.YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis()))
				//实例标识		自身标识
				.setElementid(config.getModuleInstanceId())
				//服务平台标识
				.setPlatformid(config.getPlatformID())
				//话单标识	xdrid	话单的ID
				.setXdrid(query_body.getCallId())
				//话单类型	type
				.setXdrType(Mode.XHZXH)
				//服务appkey	appkey	X号码分配的appkey
				.setAppkey(query_body.getVendorKey())
				//绑定ID	subid		无绑定关系为空
				.setSubid(null)
				//通话标识	callid	同接口定义
				.setCallid(query_body.getCallId())
				//短信接收来显号码	delivery_displaydn	Octet String （decimal）	String		1		入局短信时的主叫号码
				.setDeliveryDisplaydn(query_body.getCallNo())
				//短信接收终端号码 delivery_termindn   入局短信时的被叫号码
				.setDeliveryTermindn(query_body.getSecretNo())
				//服务类型	servicetype	Octet String (decimal)	String		1		比如：“AXB”，“AXN”，“AXYB”“ALIC”
				.setServicetype(Mode.WTC)
				.setMode("2")
				.setType("2")
				//虚拟X号码	callinSecretNO  中间虚拟号码
				.setTelX(query_body.getSecretNo())
				//绑定A号码	axybNa  绑定的A号码
				//.setTelA(contextData.getNA())
				//绑定B号码	telB  绑定的B号码或other号码（包括分机号）
				.setTelB(query_body.getCallNo())

				//短信长度
				.setMsglength((Int.INTEGER_ZERO).toString())
				//短信条数

				.setMsgcount((Int.INTEGER_ONE).toString())
				//短信中心地址
				.setSmcid(secretNOInfo==null?null:secretNOInfo.getSmcgt())
				//订单请求ID	bandrequestid	Octet String (decimal)	String		1		绑定订单中的RequestId
				.setBandrequestid(query_body.getRequestId())
				//关联区号
				.setAreacode(secretNOInfo==null?null:secretNOInfo.getAreaCode())
				//业务等级
				.setSvclevel(Str.ZERO);
		return  tdr;
	}
}
