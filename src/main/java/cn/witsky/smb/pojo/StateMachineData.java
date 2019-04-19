package cn.witsky.smb.pojo;

import lombok.Data;
import org.joda.time.DateTimeUtils;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

/**
 * @author zhanghaifeng
 * @time 2017年09月13日 下午3:27
 */
@Data
public class StateMachineData {

    private UntypedStateMachine stateMachine;

    private long createMillis;
    private long pickUpMillis;
    private long callDuration;

    StateMachineData(UntypedStateMachine stateMachine) {

        this.stateMachine = stateMachine;
        this.createMillis = DateTimeUtils.currentTimeMillis();
    }
}
