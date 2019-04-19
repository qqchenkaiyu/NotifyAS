package cn.witsky.smb.pojo.communication;

import lombok.*;

/**
 * @author jwpeng
 * @time 2018/2/1 11:31
 */
@Data
@NoArgsConstructor
@ToString
public class OnlineSmsData {
    String requestId;

    String telB;

    String smscontent;

    String statusreport;
}
