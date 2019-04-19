package cn.witsky.smb.Constant;

/**
 * Created by HuangYX on 2018/4/3 9:16.
 *
 * @author HuangYX
 */
public interface Cons {

  String OK = "OK";
  String PHASE_OK = "OK";
  String ERROR_CAUSE = "ERROR,cause=";
  String MISSING_CALL_DATA = "missing_callData";
  String MISSING_CALLER_NUMBER = "missing_callerNumber";
  String MISSING_CALLED_NUMBER = "missing_calledNumber";
  String MISSING_CALL_ID = "missing_callId";
  String MISSING_CALL_TIME = "missing_callTime";
  String MISSING_CODING = "missing_coding";
  String MISSING_CONTENT = "missing_content";
  String MISSING_SLF_RELEASE_NOTIFICATION = "missing_SlfReleaseNotification";


  String SMS_CONTENT_RELACE_TELEPHONE = "\\{telephone\\}";

  String SMS_CONTENT_RELACE_CONTENT = "\\{短信内容\\}";

  String SMS_CONTENT_CONTAIN_REAL_NUMBER_TEMPLATE = "%s(来自%s)";

  String DATA_ANNCODE = "anncode";

  String CALL = "call";
  String SMS = "sms";
  String DATA = "data";
  String EXCEPTION_RECEIVED = "exception-received";
  String EXCEPTION_BODY = "exception-body";

  String CAUSE = "cause";
  String FINISH_CAUSE = "finish-cause";

  String FINISH_STATUS = "finish-status";
  Object FINISH_STATUS_OK = "OK";
  Object FINISH_STATUS_OTHER = "OTHER";

  String SUBMIT_OK = "Submit OK";

  //SMS
  int SMS_ENCODING_CODE_FOR_7_BIT = 0;
  int SMS_ENCODING_CODE_FOR_8_BIT = 4;
  int SMS_ENCODING_CODE_FOR_UCS2 = 8;
  int SMS_ENCODING_CODE_FOR_UTF8 = 16;

  int SMS_SUBMIT_WAY_FOR_MO = 1;

  int RELEASE_DIR_CALLER = 1;
  int RELEASE_DIR_CALLED = 2;
  int RELEASE_DIR_PLATFORM = 0;

  String PUSH_ROUTE = "pushRoute";
  String RECOVERY_PUSH_ROUTE = "recoveryPushRoute";

  int NUMBER_WITH_86_LENGTH = 13;
  int NUMBER_WITHOUT_86_LENGTH = 11;

  interface TriggerDetection {

    String NOTIFYURL = "notifyURL";
    String SUBSCRIBERURL = "subscribeURL";
    String SERVICEURL = "serviceURL";
    String PUSHURL = "pushURL";
  }

  interface TriggerResult {

    String RELEASE = "release";
    String CONTINUE = "continue";
    String CONNECT = "connect";
  }

  interface ServiceMode {
    String CALL = "1";
    String CALL_STR = "call";
    String SMS = "0";
    String SMS_STR = "sms";
  }
  interface CallStatus {
    String ANSWER = "ANSWER";
    String BUSY = "BUSY";
    String REJECT = "REJECT";
    String NORESPONSE = "NORESPONSE";
    String ABSENT = "ABSENT";
    String UNKNOWN = "UNKNOWN";
    String SUSPEND = "SUSPEND";
    String HANGUP = "HANGUP";


	  String OTHER = "OTHER";
  }

  interface CallType {

    String CALLER = "0";
    String TERMINATE = "1";
    String SENDER = "2";
    String RECEIVER = "3";
    String PS_CALLER = "128";
    String AX_SH_127 = "127";
    String CALL_REJECT = "20";
    String SMS_REJECT = "30";
  }

  interface SmmtChannel {

    String POINT_TO_POINT = "1";
    String ONLINE_TO_RECEIVE = "2";
    String POINT_TO_POINT_AND_ONLINE_TO_RECEIVE = "3";
    String INTERCEPT = "4";
  }

  interface DisplayMode {

    String SECRET_NUMBER = "0";
    String REAL_NUMBER = "1";
    String AUTO_BIND_NUMBER = "2";
  }

  interface HeaderFiled {

    String APPKEY = "appkey";
    String TS = "ts";
    String MSGDGT = "msgdgt";

    String ACCESS = "witsky-access-id";
    String SLF_ROOT_URL = "witsky-slf-root-url";
    String X = "witsky-x-mdn";
    String VENDOR_ROUTE = "witsky-vendor-route";

    String TEMP_HEADER_TYPE = "temp-header-type";
    String XDR_ID = "witsky-xdr-id";
    String DIALOG_FROM = "witsky-dialog-from";
    String DIALOG_TO = "witsky-dialog-to";
    String GLOBAL_ID = "witsky-dialog-globalId";
    String LOG_GIGEST = "witsky-log-digest";
    String CCF_ID = "witsky-ccf-id";
    String ACCESS_ID = "witsky-access-id";
    String SERVICE_TYPE = "witsky-service-type";
    String VENDOR_APPKEY = "witsky-vendor-appkey";
  }

  interface Sopen {

    String VOICE = "voice";
    String SMS = "sms";

    int TRANSFERMS = 3;
    int QUERY = 2;
    int ANN = 1;
    int NON = 0;

    String SMS_SERVICE_SURE = "1";

    String  CONTROL_CONTINUE = "CONTINUE";
    String  CONTROL_REJECT = "REJECT";
    String  CONTROL_IVR = "IVR";

    String CONTROL_XDR_CONTINUE = "0";
    String CONTROL_XDR_REJECT = "1";
    String CONTROL_XDR_IVR = "2";

  }

  interface Push {
    String SURE = "1";
  }

  interface Restrict {

    interface Axyb {

      String ALLOW_A_CALLOUT = "0";
      String DENY_A_CALLOUT = "1";
    }

    interface Axb {

      String OTHER_RESTRICT = "1";
      String A_AND_OTHER_RESTRICT = "2";
      String B_AND_OTHER_RESTRICT = "3";
      String ALL_RESTRICT = "6";
    }

    interface Axn {

      String OTHER_RESTRICT = "0";
      String A_AND_OTHER_RESTRICT = "4";
      String N_AND_OTHER_RESTRICT = "5";
      String ALL_RESTRICT = "6";
    }
  }

  interface Control {
    String SURE = "1";
    String NON = "0";
  }

  interface ConditionType {
    /**
     * 触发
     */
    String TRIGGER = "0";
    /**
     * 抑制触发
     */
    String NON_TRIGGER = "1";
  }

  interface QueryControlOperate {

    String CONTINUE = "CONTINUE";
    String REJECT = "REJECT";
    String IVR = "IVR";
  }

  interface CallBack{
    String REJECT = "0";
    String CALLBACK = "1";
    String TELB = "2";
  }

  interface Tp {
    String TP1 = "TP1";
    String TP2 = "TP2";
    String TP3 = "TP3";
  }

  interface SubmitResult {
    String success = "0";
    String failed = "1";
  }

  interface RecMode{
    String LEFT = "1";
    String RIGHT = "2";
    String MIXING = "3";
  }

  interface SessionCase{
    /**
     * A主叫
     */
    String ORIGINATING = "originating";
    /**
     * 非A主叫
     */
    String TERMINATING = "terminating";
  }

  interface Channel{
    String LEFT = "0";
    String RIGHT = "1";
  }

  interface RequestMethod{
    String SERVICECONNECT = "/serviceConnect";
    String SERVICEPLAYANDCOLLECT = "/servicePlayAndCollect";
    String SERVICERELEASE = "/serviceRelease";
    String SESSIONTEST = "/sessionTest";
    String CALLOUTREQUEST = "/callOutRequest";
    //String CALLOUTREQUEST = "/serviceConnect";
    String NOTIFYCATIONRESULTREPORT = "/NotificationResultReport";
  }
}
