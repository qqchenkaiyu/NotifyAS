package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2017/7/21.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AsToSlfConnect {

    private String callId;

    private String sessionId;

    // 主叫号码
    private String displayDn;

    // 被叫号码
    private String calledDn;

    // 业务标识，可选
    private int[] serviceFlag;

    // 提示音信息，可选
//    List<AsToSlfFilePosition> serviceAnnCodeInfo;
    private AnnCode serviceAnnCodeInfo;

    // 录音信息，可选
    private AsToSlfRecordInfo recordInfo;

    // 改发号码信息，可选
    private SlfRedirNumberInfo redirNumberInfo;

    // 编解码信息，可选
    private Integer codec;

    // 处理级别
    private Integer handleLevel;

    // 0：固定主叫侧；1~255，被叫侧标识；
    private Integer invokeId;

    // 0: abandon; 1: control
    private Integer callHandle;

    public void copy(AsToSlfConnect connect){
        if (connect == null) {
            return;
        }
        this.callId = connect.getCallId();
        this.sessionId = connect.getSessionId();
        this.displayDn = connect.getDisplayDn();
        this.calledDn = connect.getCalledDn();
        this.serviceFlag = connect.getServiceFlag();
        this.serviceAnnCodeInfo = connect.getServiceAnnCodeInfo();
        this.recordInfo = connect.getRecordInfo();
        this.redirNumberInfo = connect.getRedirNumberInfo();
        this.codec = connect.getCodec();
        this.handleLevel = connect.getHandleLevel();
        this.invokeId = connect.getInvokeId();
        this.callHandle = connect.getCallHandle();
    }
}
