package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:32
 */
@Data
public class ApkSmPushCriteria {
    /**
     * 可选
     * 通过短信接收的内容推送
     */
    private ApkPushCriteria smDeliveryContentPush;
    /**
     * 可选
     * 通过短信接收的事件推送
     */
    private ApkPushCriteria smDeliveryEventPush;
    /**
     * 可选
     * 短信接收的状态报告推送
     */
    private ApkPushCriteria smSubmitReportPush;
}
