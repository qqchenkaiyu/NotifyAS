package cn.witsky.smb.pojo.xdr;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/11/5 15:30
 */
@Data
public class QueryDR {
    /**
     * 记录时间戳  Octet String （decimal）	String		1		YYYYMMDDhhmmssNNN
     */
    private String time;
    /**
     * 实例标识  Octet String (decimal)	String		1		自身标识
     */
    private String elementId;
    /**
     * 服务平台标识  Octet String (decimal)	String		1		"服务平台的标识    没有，则为空"
     */
    private String platformId;
    /**
     * 话单标识  Octet String （decimal）	String		1		话单的ID
     */
    private String xdrId;
    /**
     * 话单类型  Octet String (decimal)	String		1		15：主动查询业务
     */
    private String type;
    /**
     * 服务appkey  Octet String （decimal）	String		1		X号码分配的appkey
     */
    private String appkey;
    /**
     * 绑定ID  Octet String (decimal)	String				无绑定关系为空
     */
    private String subId;
    /**
     * 通话标识  Octet String (decimal)	String		1		同接口定义
     */
    private String callid;
    /**
     * 查询计数  Octet String (decimal)	number		1		"显示当前计数的序号，从1开始计数。    代理模块如果不确定序号，统一填0"
     */
    private int querySeq;
    /**
     * 请求时间  Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String requestTime;
    /**
     * 响应时间  Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String responseTime;
    /**
     * 响应代码  Octet String (decimal)	String		1		结果code
     */
    private String code;
    /**
     * 代码原因  Octet String (decimal)	String		1		结果描述
     */
    private String description;
    /**
     * 业务来源  Octet String (decimal)	String		1		"0：呼叫            1：短信"
     */
    private String serviceSource;
    /**
     * 来显号码  Octet String 	String		1		入局呼叫时的主叫号码
     */
    private String callInDisplayDn;
    /**
     * 接入号码  Octet String 	String		1		入局呼叫时的被叫号码
     */
    private String callInTerminDn;
    /**
     * 呼入原始被叫号码  Octet String 	String		1		入局呼叫时的原始号码
     */
    private String callInRedirectDn;
    /**
     * 指示动作  Octet String (decimal)	String		1		"0：CONTINUE  1：REJECT 2：IVR"
     */
    private String action;
    /**
     * 服务类型  Octet String (decimal)	String		1		"“AXB”，“AXN”，“AXYB”，“AXG”， “ALIC”：阿里中心化；“SOP”：业务开放 “SPX”：业务代理，如链家"
     */
    private String serviceType;
    /**
     * 去呼显号码  Octet String 	String		1		出局呼叫时的主叫号码
     */
    private String callOutDisplayDn;
    /**
     * 去呼目的号码  Octet String 	String		1		出局呼叫时的被叫号码
     */
    private String callOutTerminDn;
    /**
     * 去呼携带呼转号码  Octet String 	String		1		出局呼叫时的原始号码
     */
    private String calloutRedirectDn;
    /**
     * 录音标志  Octet String (decimal)	String		1		"0：不需要 1：摘机录音"
     */
    private int needRecord;
    /**
     * 短信通道  Octet String (decimal)	String		1		"0：正常点对点 1：短信托收 2：丢失"
     */
    private String smChannel;
    /**
     * 主叫侧放音编码  Octet String 	String		1		多个提示音通过“|”分割
     */
    private String callerPlayCode;
    /**
     * 被叫侧放音编码  Octet String 	String		1		多个提示音通过“|”分割
     */
    private String calledPlayCode;
    /**
     * 短信中心地址  Octet String 	String		1		短信中心地址
     */
    private String smcId;


    public void setTime(String time) {
        this.time = time;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setXdrId(String xdrId) {
        this.xdrId = xdrId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public void setQuerySeq(int querySeq) {
        this.querySeq = querySeq;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServiceSource(String serviceSource) {
        this.serviceSource = serviceSource;
    }

    public void setCallInDisplayDn(String callInDisplayDn) {
        this.callInDisplayDn = callInDisplayDn;
    }

    public void setCallInTerminDn(String callInTerminDn) {
        this.callInTerminDn = callInTerminDn;
    }

    public void setCallInRedirectDn(String callInRedirectDn) {
        this.callInRedirectDn = callInRedirectDn;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCallOutDisplayDn(String callOutDisplayDn) {
        this.callOutDisplayDn = callOutDisplayDn;
    }

    public void setCallOutTerminDn(String callOutTerminDn) {
        this.callOutTerminDn = callOutTerminDn;
    }

    public void setCalloutRedirectDn(String calloutRedirectDn) {
        this.calloutRedirectDn = calloutRedirectDn;
    }

    public void setNeedRecord(int needRecord) {
        this.needRecord = needRecord;
    }

    public void setSmChannel(String smChannel) {
        this.smChannel = smChannel;
    }

    public void setCallerPlayCode(String callerPlayCode) {
        this.callerPlayCode = callerPlayCode;
    }

    public void setCalledPlayCode(String calledPlayCode) {
        this.calledPlayCode = calledPlayCode;
    }

    public void setSmcId(String smcId) {
        this.smcId = smcId;
    }
}
