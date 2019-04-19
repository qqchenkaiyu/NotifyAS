package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 18:57
 */
@Data
public class ApkRSF {
    /**
     * 必选
     * 录音服务路径
     */
    private String rspath;

    /**
     * 自添加，未出现在文档中，如未来有则修改
     */
    private String urlPath;

    /**
     * 目标文件的装载名称格式，当loadDest为0时该字段必选。文件名称可由以下字段组成：
     * ${接入方标识: 0}_${来话号码: 1}_${去话号码: 2}_${来话被叫(X): 3}_${时间戳: 4}_${呼叫标识: 5}_${板卡信息: 6}。
     */
    private String loadFmt;
}
