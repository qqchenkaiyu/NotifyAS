package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接入商控制项
 *
 * @author HuangYX
 * @date 2018/6/1 16:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApkConfig {
    /**
     * 接入商标识,完整的appkey
     */
    private String wholeAppkey;

    /**
     * 用户验签秘钥
     */
    private String secretkey;
    /**
     * 初始规则表
     */
    private ApkServiceRouteTable serviceRouteTable;
    /**
     * 扩展规则表
     */
    private ApkExtCriteriaTable extCriteriaTable;
    /**
     * 业务功能表
     */
    private ApkServiceFunction serviceFunction;
    /**
     * 业务计费信息
     */
    private ApkServicePayment servicePayment;
    /**
     * 业务推送规则
     */
    private ApkServicePushCriteria servicePushCriteria;
    /**
     * 业务代理规则
     */
    private ApkServiceAgentCriteria serviceAgentCriteria;
}
