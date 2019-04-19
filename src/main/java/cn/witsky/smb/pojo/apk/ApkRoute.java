package cn.witsky.smb.pojo.apk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyuyang@witsky.cn
 * @date 2018/11/12 11:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApkRoute {

    /**
     * 订阅地址
     */
    private String subscribeURL;
    /**
     * 通知地址
     */
    private String notifyURL;
    /**
     * 业务地址
     */
    private String serviceURL;
    /**
     * 推送地址
     */
    private String pushURL;
}
