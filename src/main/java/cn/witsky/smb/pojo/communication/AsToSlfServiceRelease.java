package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class AsToSlfServiceRelease {

    private String callId;

    private String sessionId;

    // 释放原因
    private Cause cause;

    /** 释放提示音信息，可选 */
    private AnnCode serviceAnnCodeInfo;

    /** INVOKEID为0，则释放全部呼叫；为非0值，则释放对应编号的被叫流。 */
    private Integer invokeId;

}
