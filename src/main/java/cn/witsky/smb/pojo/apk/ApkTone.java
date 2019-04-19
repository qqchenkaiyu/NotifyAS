package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 可扩展，初始的默认提示音
 *
 * @author HuangYX
 * @date 2018/6/6 18:57
 */
@Data
public class ApkTone {
    /**
     * 可选
     * 无效提示音
     */
    private String illegal;
    /**
     * 可选
     * 振铃期间提示音
     */
    private String ringback;
    /**
     * 可选
     * 收号提示音
     */
    private String collectdgts;
    /**
     * 可选
     * 结束提示音
     */
    private String finish;
    /**
     * 可选
     * 受限提示音
     */
    private String restrict;
    /**
     * 可选
     * 无权提示音
     */
    private String noright;
}
