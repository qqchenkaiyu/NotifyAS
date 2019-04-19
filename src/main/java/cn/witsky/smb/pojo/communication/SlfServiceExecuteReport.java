package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlfServiceExecuteReport {
    @NotBlank(message = "callId不能为空")
    private String callId;
    @NotBlank(message = "sessionId不能为空")

    private String sessionId;
    /** 结果*/
    @NotEmpty(message = "result不能为空")
    private Integer[] result;

    /** 按键信息*/
    private String dgts;

}
