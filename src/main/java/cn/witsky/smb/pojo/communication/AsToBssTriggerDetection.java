package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 14:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsToBssTriggerDetection {

    /**
     * 导入的appkey
     */
    private String appkey;
    /**
     * 服务模式：
     *  1：呼叫
     *  0：短信
     */
    private String serviceMode;
    /**
     * 业务类型，如AX,AXB
     */
    private String serviceType;
    /**
     * 业务主键
     */
    private String serviceKey;
    /**
     * 主叫号码
     */
    private String callerPartyDN;
    /**
     * 被叫号码
     */
    private String calledPartyDN;
    /**
     * 改发号码
     */
    private String redirPartyDN;
    /**
     * 附加被叫号码
     */
    private String assistCalledPartyDn;

}
