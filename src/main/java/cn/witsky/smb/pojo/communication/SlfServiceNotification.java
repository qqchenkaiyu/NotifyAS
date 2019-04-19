package cn.witsky.smb.pojo.communication;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/7/21.
 */
@Data
public class SlfServiceNotification {
    // 类型：0-进展，1-释放
    @NotNull(message="type不能为空")
    private Integer type;

    // 类型=释放时必需
@Valid
    private SlfReleaseNotification releaseNotification;

    // 类型=进展时必需
    @Valid
    private SlfProgressNotification progressNotification;

}
