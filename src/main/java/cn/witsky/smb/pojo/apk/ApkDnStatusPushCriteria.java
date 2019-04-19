package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:30
 */
@Data
public class ApkDnStatusPushCriteria {
    /**
     * 必选	号码状态变更的推送
     */
    private ApkPushCriteria dnStatusPush;
}
