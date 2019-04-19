package cn.witsky.smb.core.constant;

/**
 * Created by HuangYX on 2018/4/8 10:12.
 *
 * @author HuangYX
 */
public interface LogCons {
    String PROTOCOL = "protocol";
    String HTTP = "HTTP";
    String IF = "IF";

    String IF_TELCOMAPI = "telcomapi";
    String IF_SERVICEAPI = "serviceapi";
    String IF_ORCHESTRATE = "orchestrate";
    String IF_CDR = "cdr";
    String IF_VOICESERVICE = "voiceservice";
    String IF_RECORDSERVICE = "recordservice";

    String ICP = "ICP";


    String body = "body";


    String BODY_REQUEST = "request_detail";
    String BODY_REQUEST_TIMESTAMP = "request_timestamp";
    String BODY_RESPONSE = "response_detail";
    String BODY_RESPONSE_TIMESTAMP = "response_timestamp";
    String BODY_PROCESS_COST_TIME = "cost_millis";

    String JOURNAL_NAME_EVENT_LOG = "eventLog";
    String JOURNAL_ID_EVENT_LOG = "01";
}
