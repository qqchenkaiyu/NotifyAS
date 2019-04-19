package cn.witsky.smb.pojo.communication;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@ToString
public class SlfSessionTest {
    @NotNull(message="callId不能为空")
    private String callId;
    @NotNull(message="sessionId不能为空")
    private String sessionId;
    @NotNull(message="sequence不能为空")
    private Long sequence;
    private Integer code;

    public SlfSessionTest(String callId, String sessionId, Long sequence, Integer code) {
        this.callId = callId;
        this.sessionId = sessionId;
        this.sequence = sequence;
        this.code = code;
    }

    public SlfSessionTest(String callId, String sessionId, Integer code) {
        this.callId = callId;
        this.sessionId = sessionId;
        this.code = code;
    }

    public SlfSessionTest(String callId, String sessionId) {
        this.callId = callId;
        this.sessionId = sessionId;
    }

    public SlfSessionTest(Integer code) {
        this.code = code;
    }

    public SlfSessionTest() {
    }
}
