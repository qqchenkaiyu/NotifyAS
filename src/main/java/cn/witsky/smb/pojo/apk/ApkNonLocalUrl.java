package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * 非本地时URL
 * @author HuangYX
 * @date 2018/6/6 18:44
 */
@Data
public class ApkNonLocalUrl {
    /**
     * 地址类型：
     * 0:宏定义
     * 1:URL格式
     */
    private String type;
    /**
     * type为0时：是jsonObject的json字符串
     * “subscribeURL”：订阅URL
     * “serviceURL”：业务URL
     * “notifyURL”：通知URL
     * “pushURL”：推送URL；
     * type为1时：具体的URL地址
     */
    private String address;

}
