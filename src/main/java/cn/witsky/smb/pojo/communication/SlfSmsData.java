package cn.witsky.smb.pojo.communication;

import lombok.Data;

/**
 * Created by Administrator on 2017/7/21.
 */
@Data
public class SlfSmsData {

    /**
     * 发送者号码
     */
    private String callingDn;

    /**
     * 接收者号码
     */
    private String calledDn;

    /**
     * 编码
     */
    private Integer messageCoding;

    /**
     * 短信内容，字节数组以字符串表示，如0xA370表示为"A370"
     */
    private String messageContent;

    /**
     * 短消息字节数
     */
    private Integer messageByteLength;

    /**
     * 短信条数
     */
    private Integer messageCount;

}
