package cn.witsky.smb.pojo.apk;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/6/6 17:24
 */
@Data
public class ApkServiceAgentCriteria {
    /**
     * 必选
     * 呼叫代理查询URL
     */
    private Object callAgentQueryURL;
    /**
     * 必选
     * 短信代理查询URL
     */
    private Object smAgentQueryURL;
}
