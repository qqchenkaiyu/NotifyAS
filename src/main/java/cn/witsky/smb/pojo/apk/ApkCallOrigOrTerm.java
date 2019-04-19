package cn.witsky.smb.pojo.apk;

import lombok.*;

/**
 * @author HuangYX
 * @date 2018/6/6 19:05
 */
@Data
public class ApkCallOrigOrTerm {
    /**
     * 必选
     * 呼叫限制项
     */
    private ApkRestrict restrict;
    /**
     * 必选	 0：开放，默认显示X ,
     * 1：强制为X ,
     * 2：强制透传;
     */
    private String cid_display;
    /**
     * 必选
     * 提示音
     */
    @Deprecated
    private ApkTone tone;
    /**
     * 必选
     * 0：不提供,
     * 1：提供，默认,
     * 2：提供，原始两路,
     * 3：提供，合并立体声
     */
    private String record;

}
