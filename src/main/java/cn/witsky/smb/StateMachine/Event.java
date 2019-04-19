package cn.witsky.smb.StateMachine;

/**
 * @author zhanghaifeng
 * @time 2017年08月17日 下午2:49
 */
public enum Event {

    // 准备调用放音收号
    prepareToConnect,
    receiveAlerting,
    timeoutForAlert,
    receiveRinging,
    timeoutForRing,
    receiveConnnecting,
    timeoutForConnnect,
    receiveRelease,
    playEnd,
    autoReplay,

    testSessionFail,
    timeToTestSession,
    ReleaseWithReplay,
    ReleaseWithNoplay,
    timeoutForReport,

    timeoutToMaxDuring,
    progressReceived,
   askForReplayKey, PA, connectSuccess, intoFail,

}
