package cn.witsky.smb.pojo.xdr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author HuangYX
 * @date 2018/9/3 9:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Cdr {

    /**
     * 记录时间戳
     * Octet String （decimal）	String		1		YYYYMMDDhhmmssNNN
     */
    private String time;
    /**
     * 实例标识
     * Octet String (decimal)	String		1		自身标识
     */
    private String elementid;
    /**
     * 服务平台标识		Octet String (decimal)	String		1		"服务平台的标识
     * 比如：呼叫CDR的ALI_CCF_A"
     */
    private String platformid;
    /**
     * 话单标识
     * Octet String （decimal）	String		1		话单的ID
     */
    private String xdrid;
    /**
     * 话单类型
     * Octet String (decimal)	String		1		1：小号呼叫业务
     */
    private String xdrType;
    /**
     * 服务appkey
     * Octet String （decimal）	String		1		X号码分配的appkey
     */
    private String appkey;

    /**
     * 绑定ID
     * Octet String (decimal)	String		1		无绑定关系为空
     */
    private String subid;
    /**
     * 通话标识
     * Octet String (decimal)	String		1		同接口定义
     */
    private String callid;
    /**
     * 呼入来显号码
     * Octet String （decimal）	String		1		入局呼叫时的主叫号码
     */
    private String callinDisplaydn;
    /**
     * 呼入被叫号码
     * Octet String （decimal）	String		1		入局呼叫时的被叫号码
     */
    private String callinTermindn;
    /**
     * 呼出显示号码
     * Octet String (decimal)	String		1		出局呼叫时的主叫号码
     */
    private String calloutDisplaydn;
    /**
     * 呼出对端号码
     * Octet String (decimal)	String		1		出局呼叫时的被叫号码
     */
    private String calloutTermindn;
    /**
     * 呼入原始被叫号码
     * Octet String (decimal)	String		1		入局呼叫时的原始号码
     */
    private String callinRedirectdn;
    /**
     * 按键号码
     * Octet String (decimal)	String		1		入局时，交互按键的号码，多次按键通过“|”分割
     */
    private String callinDtmf;
    /**
     * 服务类型
     * Octet String (decimal)	String		1		比如：“AXB”，“AXN”，“AXYB”“ALIC”
     */
    private String servicetype;
    /**
     * 呼叫方式
     * Octet String (decimal)	String		1		"0：大网直呼;1：在线呼出;2：DTMF呼出;3：呼叫转接;11：双呼"
     */
    private String mode;
    /**
     * 呼叫类型
     * Octet String (decimal)	String		1		"0：未知;1：A主叫;2：A被叫"
     */
    private String type;
    /**
     * 呼叫接续
     * Octet String (decimal)	String		1		"x：接续类取值;0：正常接续;1：用户白名单;1x：拦截类：10：系统黑名单;11：用户黑名单"
     */
    private String proceeding;
    /**
     * 虚拟X号码
     * Octet String (decimal)	String		1		中间虚拟号码
     */
    private String telX;
    /**
     * 虚拟Y号码
     * Octet String (decimal)	String		1		中间虚拟号码
     */
    private String telY;
    /**
     * 绑定A号码
     * Octet String (decimal)	String		1		绑定的A号码
     */
    private String telA;
    /**
     * 绑定B号码
     * Octet String （decimal）	String		1		绑定的B号码或other号码（包括分机号）
     */
    private String telB;
    /**
     * 呼入开始时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String callintime;
    /**
     * 呼出开始时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String callouttime;
    /**
     * 呼叫确认时间
     * Octet String （decimal）	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String alertingtime;
    /**
     * 振铃开始时间
     * Octet String （decimal）	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String ringingtime;
    /**
     * 通话开始时间
     * Octet String （decimal）	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String starttime;
    /**
     * 通话结束时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String releasetime;
    /**
     * 通话时长
     * Octet String (decimal)	number		1		有通话时的有效通话时长
     */
    private String duringtime;
    /**
     * 释放原因
     * Octet String (decimal)	number		1
     */
    private String releaseCause;
    /**
     * 释放方向
     * Octet String (decimal)	number		1
     */
    private String releaseDir;
    /**
     * 被叫有条件呼转号码
     * Octet String (decimal)	String		1
     */
    private String conditionRedirectDN;
    /**
     * 被叫有条件呼转原因
     * Octet String (decimal)	number		1
     */
    private String conditionRedirectCause;
    /**
     * 触发二次呼转标识
     * Octet String (decimal)	number		1		0-不触发，1-触发
     */
    private String secondredir;
    /**
     * 录音模式
     * Octet String (decimal)	String		1		"0：不录音;1：接通录音;2：振铃录音"
     */
    private String recordmode;
    /**
     * 录音URL
     * Octet String (decimal)	String		1		"不录音，此值为空；录音，此值为录音的URL"
     */
    private String recordurl;

    /**
     * 订单请求ID
     * Octet String (decimal)	String		1		绑定订单中的RequestId
     */
    private String bandrequestid;
    /**
     * 订单请求标记
     * Octet String (decimal)	String		1		绑定订单中的Remark
     */
    private String bandremark;
    /**
     * 组ID
     * Octet String (decimal)	String		1		组号，AXG模式使用
     */
    private String groupid;

    /**
     * 关联区号		Octet String (decimal)	String		1		关联的区号，没有填0
     */
    private String areacode;
    /**
     * 业务等级		Octet String (decimal)	String		1		"当前的呼叫等级，默认为0
     * 阿里中心化时，响应接口中携带"
     */
    private String svclevel = "0";

}
