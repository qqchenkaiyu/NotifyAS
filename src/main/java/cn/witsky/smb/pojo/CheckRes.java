package cn.witsky.smb.pojo;

import lombok.*;

/**
 * @author HuangYX
 * @date 2018/6/11 19:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRes {
    /**
     * 是否达到检查目的，根据实际情形，自行定义
     * tp1/tp2 是否发送查询请求
     * tp3 是否通过检查
     */
    private boolean queryToUrlOrCheckOk;

    /**
     * 0:check ok
     * 1:query sent
     * 2:check fail
     */
    private int code;

    /**
     * 描述语句
     */
    private String msg;

    public CheckRes(boolean queryToUrlOrCheckOk, String msg){
        this.queryToUrlOrCheckOk = queryToUrlOrCheckOk;
        this.msg = msg;
    }

}
