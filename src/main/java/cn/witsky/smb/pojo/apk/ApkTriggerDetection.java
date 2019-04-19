package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 触发条件项
 * @author HuangYX
 * @date 2018/6/6 18:45
 */
@Data
public class ApkTriggerDetection {
    /**
     * 0：触发
     * 1：抑制触发
     */
    private String conditionType;
    /**
     * “call”，呼叫
     * “sms”，短信
     */
    private String conditionMethod;
    /**
     * 会话场景
     * “originating”:A呼入主叫
     * “terminating”:非A呼入主叫
     */
    private String sessionCase;


}
