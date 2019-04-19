package cn.witsky.smb.pojo.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 1：案例的release通知中时间对应接口字段，
 * "alertingTime": 1516343781000,   -->ringTime 振铃时间
 * "connectingTime": 1516343785000,-->callOutTime 呼出时间//摘机时间
 * "startTime": 1516343771000,-->startTime 开始时间//callOutTime 呼出时间
 * "releaseTime": 1516343785000,-->releaseTime 挂机时间
 * <p>
 * 通话时长=releaseTime-connectingTime
 * <p>
 * 1）有通话的情况
 * |-----starttime---------->| 向被叫呼出
 * |<---alertingTime------- | 被叫振铃了
 * |<---connectingTime--- | 被叫摘机了
 * |<---releaseTime------> | 释放了
 * <p>
 * 2）没有通话，但有振铃的情况
 * |-----starttime---------->| 向被叫呼出
 * |<---alertingTime------- | 被叫振铃了
 * |<---releaseTime------> | 释放了
 * <p>
 * 2）没有通话，也没有振铃的情况
 * |-----starttime---------->| 向被叫呼出
 * |<---releaseTime------> | 释放了
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlfReleaseNotification {
    @NotBlank(message="callId不能为空")
    private String callId;
    @NotBlank(message="sessionId不能为空")
    private String sessionId;

    /**
     * 释放方向
     */
    @NotNull(message="releaseDirect不能为空")
    private Integer releaseDirect;

    /**
     * callOutTime
     * 开始时间
     */
    @NotNull(message="startTime不能为空")
    private Long startTime;

    /**
     * 振铃时间
     */
    @NotNull(message="alertingTime不能为空")
    private Long alertingTime;

    /**
     * 用户振铃时戳
     */
    @NotNull(message="ringingTime不能为空")
    private Long ringingTime;

    /**
     * 接通时间
     */
    @NotNull(message="connectingTime不能为空")

    private Long connectingTime;

    /**
     * 释放时间
     */
    @NotNull(message="releaseTime不能为空")
    private Long releaseTime;

    /**
     * 0：固定主叫侧；1~255，被叫侧标识；
     */
    @NotNull(message="invokeId不能为空")
    private Integer invokeId;

    /**
     * 释放原因,原因值
     */
    private Cause cause;

}
