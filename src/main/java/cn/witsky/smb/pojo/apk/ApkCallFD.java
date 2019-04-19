package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 19:03
 */
@Data
public class ApkCallFD {
    /**
     * 对A而言的呼入
     */
    private ApkCallOrigOrTerm orig;
    /**
     * 对非A而言的呼入
     */
    private ApkCallOrigOrTerm term;
}
