package cn.witsky.smb.StateMachine;

/**
 * @author zhanghaifeng
 * @time 2017年08月17日 下午3:58
 */
public enum State {

    // 初始
    idle,
    waitAlerting, //
    waitRinging,
    waitConnect,
    connected,
waitReport,
    released,

}
