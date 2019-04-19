package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:31
 */
@Data
public class ApkCallPushCriteria {
    /**
     * 可选
     * 呼叫开始的推送
     */
    private ApkPushCriteria callStartPush;
    /**
     * 可选
     * 呼叫振铃的推送
     */
    private ApkPushCriteria callRingingPush;
    /**
     * 可选
     * 呼叫摘机的推送
     */
    private ApkPushCriteria callConnectingPush;
    /**
     * 可选
     * 呼叫进展（除振铃、摘机外）的推送
     */
    private ApkPushCriteria callProgressPush;
    /**
     * 可选
     * 呼叫按键的推送
     */
    private ApkPushCriteria callDTMFPush;
    /**
     * 必选
     * 呼叫结束的推送
     */
    private ApkPushCriteria callFinishPush;
}
