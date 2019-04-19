package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 对A而言的接入
 *
 * @author HuangYX
 * @date 2018/6/6 19:11
 */
@Data
public class ApkSmsOrigOrTerm {
    /**
     * 条件
     * 接入限制项
     */
    private ApkRestrict restrict;
    /**
     * 条件
     * 0：不允许在线始发
     * 1：允许在线始发
     */
    private String mo_channel;
    /**
     * 条件
     * 0：不限制，允许接口制定
     * 1：仅点对点
     * 2：仅在线推送
     * 3：点对点+在线
     * 4：拦截
     */
    private String mt_channel;

}
