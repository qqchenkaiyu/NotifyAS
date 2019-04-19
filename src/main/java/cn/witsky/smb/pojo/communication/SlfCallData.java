package cn.witsky.smb.pojo.communication;

import lombok.*;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlfCallData {
    /**
     * 数据id
     */
    private String callId;

    /**
     * 地址信息，用于组成会话标识，参见会话标识说明
     * {"c":"116#20#20#192.168.110.104"}
     */
    private String sessionIdAddressInfo;

    /**
     * 主叫号码
     */
    private String callerNumber;

    /**
     * 被叫号码
     */
    private String calledNumber;

    /**
     * 改发号码信息
     */
    private SlfRedirNumberInfo redirNumberInfo;

    /**
     * 区号
     */
    private String locationNumber;

    /**
     * 开始时间
     */
    private Long callTime;

    /**
     * 呼叫协议；BICC: 0; SIP: 1
     */
    private Integer prtlType;
    /**
     * 呼叫类型；0: 未知; 1: 主叫; 2; 被叫
     */
    private Integer callType;

}
