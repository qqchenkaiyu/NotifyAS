package cn.witsky.smb.core.domain;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/5/9 14:58
 */
@Data
public class LogDataBody {
    private Object requestDetails;
    private String requestTimestamp;

    private Object responseDetails;
    private String responseTimestamp;

    private long processCost;

    private Throwable exception;

    private Object processLog;

    private String cdrId;
}
