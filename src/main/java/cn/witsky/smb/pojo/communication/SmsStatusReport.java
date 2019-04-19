package cn.witsky.smb.pojo.communication;

import lombok.*;

/**
 * @author jwpeng
 * @time 2018/3/30 12:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsStatusReport {

    //短信的发送者号码（IMSI号）
    private String senderDn;
    //短信的参考号
    private Integer reference;
    //短信的接收者号码
    private String recipientDN;
    //短信接收时间
    private String recipientTimeStamp;
    //短信接收状态
    private Integer sms_tp_status;

}
