package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:10
 */
@Data
public class ApkSmsFD {
    /**
     * 对A而言的呼入
     */
    private ApkSmsOrigOrTerm orig;
    /**
     * 对非A而言的呼入
     */
    private ApkSmsOrigOrTerm term;
}
