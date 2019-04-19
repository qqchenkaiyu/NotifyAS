package cn.witsky.smb.pojo.apk;

import lombok.Data;

import java.util.List;

/**
 * 业务功能表
 *
 * @author HuangYX
 * @date 2018/6/6 17:23
 */
@Data
public class ApkServiceFunction {

    /**
     * 必选
     * 业务单元列表
     */
    private List<ApkSU> sulist;
    /**
     * 必选
     * 语音服务参数
     */
    private ApkVSF vsf;
    /**
     * 可选
     * 录音服务参数
     */
    private ApkRSF rsf;
    /**
     * 必选
     * 默认提示音
     */
    private ApkTone dfattone;
}
