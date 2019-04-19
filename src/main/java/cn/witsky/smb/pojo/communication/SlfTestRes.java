package cn.witsky.smb.pojo.communication;

/**
 * Created by Administrator on 2017/7/18.
 */
public class SlfTestRes {

    private int code;

    private String sessionId;

    private String message;

    private String callId;

    public SlfTestRes() {
    }

    public SlfTestRes(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public SlfTestRes(int code, String message, String sessionId) {
        this.code = code;
        this.sessionId = sessionId;
        this.message = message;
    }




    public int getCode() {
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
