package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by HuangYX on 2017/10/26 17:09.
 */
@lombok.Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AsToSlfContinue {

    String callId;

    String sessionId;

    // 呼叫处理0: abandon; 1: control, 可选字段
    Integer callHandle;

    // 业务标识
    int[] flags;

    // 处理级别
    Integer handleLevel;

    // 免单码，踢单标识,都是null，不传
    List<Integer> codeValues;

    public AsToSlfContinue(String callId, String sessionId, Integer callHandle, int[] flags, Integer handleLevel) {
        this.callId = callId;
        this.sessionId = sessionId;
        this.callHandle = callHandle;
        this.flags = flags;
        this.handleLevel = handleLevel;
    }
}
