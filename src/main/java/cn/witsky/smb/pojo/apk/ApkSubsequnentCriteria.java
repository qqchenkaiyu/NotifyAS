package cn.witsky.smb.pojo.apk;

import java.util.List;
import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 18:42
 */
@Data
public class ApkSubsequnentCriteria {
    /**
     * 优先级别 数字越大 优先级越高
     */
    private String priority;
    /**
     * 业务类型(ayb,nas)
     */
    private String serviceKey;
    /**
     * 触发检测URL
     */
    private ApkNonLocalUrl triggerDetectionURL;
    /**
     * 触发条件项
     */
    private List<ApkTriggerDetection> triggerDetection;
}
