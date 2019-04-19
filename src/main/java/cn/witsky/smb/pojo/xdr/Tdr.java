package cn.witsky.smb.pojo.xdr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author HuangYX
 * @date 2018/8/22 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Tdr {
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
     * Octet String (decimal)	String		1		11：小号短信业务
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
     * 短信接收来显号码
     * Octet String （decimal）	String		1		入局短信时的主叫号码
     */
    private String deliveryDisplaydn;
    /**
     * 短信接收终端号码
     * Octet String （decimal）	String		1		入局短信时的被叫号码
     */
    private String deliveryTermindn;
    /**
     * 短信发送显示号码
     * Octet String (decimal)	String		1		出局短信时的主叫号码
     */
    private String submitDisplaydn;
    /**
     * 短信发送终端号码
     * Octet String (decimal)	String		1		出局短信时的被叫号码
     */
    private String submitTermindn;
    /**
     * 短信内容提取扩展号码
     * Octet String (decimal)	String		1		入局短信时的内容中携带的扩展号码
     */
    private String deliveryExtsubdn;
    /**
     * 服务类型
     * Octet String (decimal)	String		1		比如：“AXB”，“AXN”，“AXYB”“ALIC”
     */
    private String servicetype;
    /**
     * 短信方式
     * Octet String (decimal)	String		1
     * "1：点对点方式短信
     * 2：在线发送短信
     * 3：在线接收短信
     * 4：点对点+在线接收短信
     * 5：点对点方式短信状态报告
     * 6：在线接收短信状态报告
     * 9：接收拦截"
     */
    private String mode;

    /**
     * 短信类型
     * Octet String (decimal)	String		1		"0：未知 1：A主叫;2：A被叫"
     */
    private String type;
    /**
     * 短信接续
     * Octet String (decimal)	String		1
     * "x：接续类取值
     * 0：正常接续;
     * 1：用户白名单;
     * 1x：拦截类：
     * 10：系统黑名单;
     * 11：用户黑名单"
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
     * Octet String （decimal）	String		1		绑定的B号码或other号码
     */
    private String telB;
    /**
     * 收到时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String deliverytime;
    /**
     * 发送时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String submittime;
    /**
     * 发送结果
     * Octet String (decimal)	number		1
     * "0：成功
     * 非0：不成功"
     */
    private String submitresult;

    /**
     * 短信回执
     * Octet String (decimal)	number		1		"空：不需要短信回执; 0-255：需要短信回执，短信回执的reference id
     */
    private String smreport;

    /**
     * 短信接收结果
     * Octet String (decimal)	number		1		"0：接收成功
     * 非0：不成功"
     */
    private String recipientresult;

    /**
     * 短信接收时间
     * Octet String (decimal)	String		1		yyyy-MM-dd HH:mm:ss
     */
    private String recipientts;
    /**
     * 短信长度
     * Octet String (decimal)	String		1		短信长度
     */
    private String msglength;
    /**
     * 短信条数
     * Octet String (decimal)	String		1		短信条数
     */
    private String msgcount;

    /** 短信中心号 */
    private String smcid;


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
    private String svclevel;

}
