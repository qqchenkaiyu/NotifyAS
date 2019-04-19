package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsToSlfServiceSmsSubmit {

    /**
     * 短信显示号码
     */
    private String callingDn;

    /**
     * 短信被叫号码
     */
    private String calledDn;

    /**
     * 编码
     */
    private int messageCoding;

    /**
     * 短信内容，字节数组以字符串表示，如0xA370表示为"A370"
     */
    private String messageContent;

    /**
     * 发送方式，可选
     */
    private AsToSlfSmsSubmitWay smsSubmitWay;

}
