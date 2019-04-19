package cn.witsky.smb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author HuangYX
 * @date 2018/7/4 20:16
 */
@Data
@ConfigurationProperties("witsky.twiceturncall")
@RefreshScope
@Component
public class TwiceTurnCall {
    private boolean switchOn;
    private int maxTimeCostFromConnectToProgress;
    private String q850CauseReason;
}
