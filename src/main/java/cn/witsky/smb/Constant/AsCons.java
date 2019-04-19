package cn.witsky.smb.Constant;

import cn.witsky.smb.pojo.communication.*;

/**
 * @author HuangYX
 * @date 2018/5/29 10:17
 */
public interface AsCons {
    String TO_SLF_CONNECT_URL = "serviceConnect";
    String TO_SLF_PLAY_AND_COLLECT_URL = "servicePlayAndCollect";
    String TO_SLF_RELEASE_URL = "serviceRelease";
    String TO_SLF_SMS_SUBMIT_URL = "serviceSmsSubmit";
    String TO_SLF_TEST_URL = "sessionTest";
    String TO_SLF_CONTINUE_URL = "serviceContinue";
    String TO_BSS_TRIGGER_DETECTION_URL = "triggerdetection";
    String TO_APP_SOPEN_QUERY = "/v2/x/sopen/query";


    String EXTRA_URL_FOR_SMS_PUSH = "smEvtRecordPush";
    String EXTRA_URL_FOR_CALL_PUSH = "callEvtRecordPush";

    String EXTRA_URL_SERVICE = "/service/";


    int SLFRES_CODE_OK = 0;
    String SLFRES_MSG_OK = "success";

    int SLFRES_CODE_FAIL = 1;
    String SLFRES_MSG_FAIL = "fail";

    int SLFRES_CODE_INVALID_PARAMS = 101;
    String SLFRES_MSG_INVALID_PARAMS = "参数错误";


    int SLFRES_CODE_FORBIDDEN = 105;
    String SLFRES_MSG_FORBIDDEN = "无权呼叫";

    int SLFRES_CODE_PROCESS_ERROR = 103;
    String SLFRES_MSG_PROCESS_ERROR = "处理错误";

    String SLFRES_MSG_LACK_OF_RESOURCE = "状态机资源不足";

    String HEADER_ROOT_URL = "witsky-slf-root-url";

    /**
     * 平台释放
     */
    int TAG_RELEASE_DIRECT_PLATFORM = 0;

    /**
     * 经咨询薛哥：
     * AS下发disconnect的时候，plan填0，reason填31。
     */
    Integer TAG_DISCONNECT_CAUSE_REASON = 31;

    Integer TAG_DISCONNECT_CAUSE_PLAN = 0;

    Integer TAG_INVOKLEID_CALLING = 0;

    /**
     * 需要对被叫呼叫强控制
     */
    Integer TAG_NEED_CALLHANDLE = 1;
    /**
     * 不需要强控制
     */
    Integer TAG_NOT_NEED_CALLHANDLE = 0;


    String TAG_TRIGGER_TWICE_TURN_CALL = "1";
    

    String SUBMIT_OK_FOR_PUSH= "0";

    String APK_DEFAULT_SUFFIX = "0000";


    SlfRes SLFRES_FOR_OK = new SlfRes(0, "SUCCESS");
    SlfRes SLFRES_FOR_INVALID_PARAMETERS = new SlfRes(101, "参数无效");
    SlfRes SLFRES_FOR_PROCESS_ERROR = new SlfRes(103, "处理错误");
    SlfRes SLFRES_FOR_TS_ERROR = new SlfRes(108, "时间戳超时");
    SlfRes SLFRES_FOR_TS_WRONG = new SlfRes(109, "时间戳格式错误");
}
