package cn.witsky.smb.pojo.communication;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2017/8/9.
 */
@Data
@ToString
public class SlfRes {

    private Integer code;

    private String sessionId;

    private String message;

    private String subid;
    private String callId;

    public SlfRes() {
    }

    public SlfRes(int code,String message) {
        this.code = code;
        this.message = message;
    }


    public SlfRes(int code, String message, String sessionId) {
        this.code = code;
        this.sessionId = sessionId;
        this.message = message;
    }




    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

}
