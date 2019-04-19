package cn.witsky.smb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author HuangYX
 * @date 2018/7/4 19:40
 */
@Data
@ConfigurationProperties("witsky.phone.prefixer")
@RefreshScope
@Component
public class PhonePrefixer {
    /**
     * 联通号段
     */
    private String unicom;
    /**
     * 电信号段
     */
    private String telecom;
    /**
     * 移动号段
     */
    private String mobile;


    /** 指定的号段 */
    private String specified;
}
