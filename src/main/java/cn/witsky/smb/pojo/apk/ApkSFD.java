package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 业务功能描述符
 *
 * @author HuangYX
 * @date 2018/6/6 18:58
 */
@Data
public class ApkSFD {
    /**
     * 条件
     * 呼叫功能描述符
     */
    private ApkCallFD callfd;
    /**
     * 条件
     * 短信功能描述符
     */
    private ApkSmsFD smsfd;
    /**
     * 条件
     * 绑定有效的默认时间
     */
    private String expiration;
    /**
     * 条件
     * 扩展私有功能描述符
     */
    private ApkExtPFD extPrivatefd;
}
