package cn.witsky.smb.core.domain;

import lombok.Data;

/**
 * @author HuangYX
 * @date 2018/5/9 11:22
 */
@Data
public class LogData {
    /**
     * 协议名称：“HTTP”、“ICP”
     */
    private String protocol;

    /**
     * IF,逻辑接口名称：
     * “telcomapi”：通信能力API接口
     * “serviceapi”:业务API接口
     * “orchestrate”:业务编排接口
     * “cdr”:详单拉取接口
     * “voiceservice”:语音服务接口
     * “recordservice”:录音服务接口
     */
    private String IF;

    /**
     * 消息类型：
     * “request”：请求消息
     * “response”：响应消息
     * “both”:请求和响应消息
     */
    private String phase;


    /**
     * 消息概要（可空项如果接口中有出现就必填）
     * "digest":{
     * "in_caller_dn":"",//主叫侧呼入（可空）
     * "in_called_dn":"",//主叫侧被叫号码（可空）
     * "out_caller_dn":"",//被叫侧呼出（可空）
     * "out_called_dn":"",//被叫侧被叫号码（可空）
     * "callid":"",//（可空）
     * "subid":""//绑定ID（可空）
     * }
     */
    private LogDataDigest digest;

    //    private Map<String, Object> body;
    private LogDataBody body = new LogDataBody();

}
