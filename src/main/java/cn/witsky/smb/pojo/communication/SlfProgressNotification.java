package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlfProgressNotification {
    @NotBlank(message="callId不能为空")
    private String callId;
    @NotBlank(message="sessionId不能为空")
    private String sessionId;
    /* 事件类型 */
    @NotNull(message="eventType不能为空")
    private Integer eventType;

    /* 按键信息，可选 */
    private String dgts;
    /* 0: abandon; 1: control, 可选字段 */
    private Integer callHandle;

    private Cause cause;

    private SlfRedirNumberInfo redirNumberInfo;
}
