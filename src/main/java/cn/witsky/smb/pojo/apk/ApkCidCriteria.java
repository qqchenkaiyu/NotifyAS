package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 呼叫来显规则
 * @author HuangYX
 * @date 2018/6/6 17:25
 */
@Data
public class ApkCidCriteria {
    /**
     * 0：不检查
     * 1：只能为核定号码
     * 2：核定号码+当前呼入来显
     */
    private String displaylevel;
    /**
     * 0：默认为当前X号码并继续
     * 1：业务拒绝
     */
    private String defaulthandle;

}
