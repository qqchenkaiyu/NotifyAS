package cn.witsky.smb.core.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/4/27 16:31
 */
@Data
public class LogDataDigest {
    /**
     * 主叫侧呼入（可空）
     */
    @JSONField(name = "in_caller_dn")
    private String inCallerDn;
    /**
     * 主叫侧被叫号码（可空）
     * inCalledDn
     */
    @JSONField(name = "in_called_dn")
    private String inCalledDn;
    /**
     * 被叫侧呼出（可空）
     * outCallerDn
     */
    @JSONField(name = "out_caller_dn")
    private String outCallerDn;
    /**
     * 被叫侧被叫号码（可空）
     */
    @JSONField(name = "out_called_dn")
    private String outCalledDn;

    /**
     * 绑定ID（可空）
     */
    private String subid;
    /**
     * 呼叫ID（可空）
     */
    private String callid;
}
