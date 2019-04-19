package cn.witsky.smb.pojo.communication;

import lombok.ToString;

/**
 * Created by Administrator on 2017/7/26.
 */
@ToString
public class CodeRes {

    private Integer code;
    private String message;


    public CodeRes() {
    }

    public CodeRes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
