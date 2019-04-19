package cn.witsky.smb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by HuangYX on 2018/4/4 16:34.
 *
 * @author HuangYX
 */
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "xdr")
@RefreshScope
public class XdrConfig {
    /**
     * xdr输出是否开启
     */
    private Boolean logSwitch;
    /**
     * xdr输入地址
     */
    private String logPath;
    /**
     * xdr日志超时时间
     */
    private Integer logMaxage;
//    private String logMaxlines;
//    private String logFilesize;
//    private String logProgId;
}
