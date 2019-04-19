package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 17:24
 */
@Data
public class ApkServicePushCriteria {
    /**
     * 号码状态推送规则，可选
     */
    private ApkDnStatusPushCriteria dnStatusPushCriteria;
    /**
     * 呼叫过程的推送规则 必选
     */
    private ApkCallPushCriteria callPushCriteria;
    /**
     * 短信推送 必选
     */
    private ApkSmPushCriteria smPushCriteria;

}
