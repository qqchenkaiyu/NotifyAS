package cn.witsky.smb.core.domain;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/5/9 11:12
 */
@Data
public class Log {
    protected String timestamp;
    protected String module;
    protected String instance;
    protected String ip;
    protected String type;
    protected String phase;
    protected String direction;
    protected String from;
    protected String to;
    protected String toAddress;
    protected String globalId;
    //    protected Map<String, Object> extData;
    protected LogData data = new LogData();
}
