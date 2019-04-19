package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 系统呼叫管控规则
 *
 * @author HuangYX
 * @date 2018/6/6 18:33
 */
@Data
public class ApkCriteria {
    /**
     * 1：管控
     * 0：不管控
     */
    private String control;
    /**
     * control为“管控”时有效
     * 0：非本地规则
     * 1：本地规则
     */
    private String local;

    /**
     *非本地时URL
     */
    private ApkNonLocalUrl url;

}
