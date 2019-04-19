package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 推送规则
 * @author HuangYX
 * @date 2018/6/6 19:27
 */
@Data
public class ApkPushCriteria {
    /**
     * 必选
     * 推送地址
     */
    private String pushurl;
    /**
     * 可选
     * 重试次数，默认为0
     */
    private Integer retrycount;
    /**
     * 条件
     * 重试间隔，retrycount携带出现：
     * 0：interval;1：当前次数*interval
     */
    private Integer retryinterval;
    /**
     * 条件
     * 间隔，单位秒，，retrycount携带出现：
     */
    private Integer interval;
}
