package cn.witsky.smb.pojo.communication;

import lombok.Data;

/**
 * Created by Administrator on 2017/7/21.
 */
@Data
public class SlfServiceReq {

    /** 类型：0-呼叫，1-短信*/
    private int type;

    /**呼叫参数，类型=呼叫时必需*/
    private SlfCallData callData;

    /**短信参数，类型=短信时必需*/
    private SlfSmsData smsData;

}
