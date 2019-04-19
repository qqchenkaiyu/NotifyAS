package cn.witsky.smb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by HuangYX on 2018/4/3 9:39.
 *
 * @author HuangYX
 */
@Data
@Component
@ConfigurationProperties("witsky")
@RefreshScope
public class Config {
    private int timeoutForWaitingServiceExecuteReport;
    private int timeoutForWaitingAlert;
    private int timeoutForWaitingRing;
    private int timeoutForWaitingConnect;
    private int timeoutForWaitingReport;
    private int  replayKey;
    private int  concurrency_sys;
    private int  concurrency_req;
    private String asIdAxb;
    private String asIdAxn;
    private String asIdAxx;
    private String asIdAxyb;
    private String asIdAx2b;
    private String asType;
    private String planeType;
    private String asfType;
    private String slfurl;
    private String platformID;
    private int astoslfHttpTimeout;
    private int astoslfHttpMaxRetry;
    private int minRingDuringTime;
    private String notifyUrl;
    private String temp_pid;
    private int astoslfThreadcount;

    private int astoslfServiceflagAnm;
    private int astoslfServiceflagPresskey;
    private int astoslfServiceflagAcm;
    private int astoslfServiceflagMedia;
    private int astoslfServiceflagCpg;

    private int statemachineInitcount;
    private int statemachineSessiontestInterval;
    private int statemachineMaxTtl;
    private int statemachineThreadcount;
    private int statemachineTimeoutForWaitingRelease;
    private int statemachineTimeoutForWaitingServiceExecuteReport;
    private int bearableFailsForPlayAndCollect;


    private String astoslfRecordDir;
    private int astoslfRedirReason;

    private String astoslfMappingUrl;

    private Integer handleLevel;

    private Integer codecForOptimizedFormat;
    private Integer codecForSpecifiedFormat;
    private Integer codecForCollectFormat;

    /**-放音收号-*/
    /** # 控制指示 */
    private int dgtsControl;
    /** # 最小收集数字个数 */
    private int dgtsMinCollect;
    /** # 最大收集数字个数 */
    private int dgtsMaxCollect;
    /** # 等待收集数字完成的总时长 */
    private int dgtsMaxInteractiveTime;
    /** # 等待首位数字超时时间 */
    private int dgtsInitInteractiveTime;
    /** # 两个数字输入之间的间隔时间 */
    private int dgtsInterDigitTime;
    /** # 应答结束数字 */
    private int dgtsEndMask;
    /** # 收号方式 */
    private Integer dgtsCollectType;
    /** # 收号次数 */
    private int astoslfDgtstimes;


    /**
     * 0-working
     * 1-pending
     */
    private int workingStatus = 0;


    private int recordFileFlag;

    /**
     * 录音url的后缀
     */
    private String recordSuffix;

    private boolean twiceturncallSwitch = false;

    private String recordUrl;


    private String moduleInstanceId;
    private String moduleName;

    /**
     * 0,可以发送；
     * 1，不可以发送
     */
    private int sendStatusForKafkaErr;

    private int submitWay;

    private boolean luaLogMark;

    private int submitTimeout;


    private String vendorRouteNginxUrl;


    private int restTemplateMaxTotalConnections = 20000;
    private int restTemplateMaxConnectionsPerRoute = 800;

    private int  bearableContinuousFailsForItTest;

    private boolean onlineSmsEnable;
    private String onlineSmsSlfroots;

    private int pushScheduledThreadPoolCoreSize;


}
