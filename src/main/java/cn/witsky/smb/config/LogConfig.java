package cn.witsky.smb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by HuangYX on 2018/4/4 16:34.
 *
 * @author HuangYX
 */
@Data
@Component
@ConfigurationProperties(prefix = "log")
public class LogConfig {
    private String journalKafkaTopic;
    private String bootstrapServers;
//    private int kafkaWriteThreadCount;
    private String moduleInstanceId;
    private String moduleName;


    private String logHh;
    private String logIi;
    private boolean logSwitch;
    private String logPath;
    private String logFilesize;
    private String logMaxlines;
    private String logMaxage;

}
