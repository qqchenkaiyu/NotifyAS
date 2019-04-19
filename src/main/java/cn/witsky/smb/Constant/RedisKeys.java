package cn.witsky.smb.Constant;

import lombok.NonNull;

/**
 * Created by HuangYX on 2018/4/4 10:09.
 *
 * @author HuangYX
 */
public interface RedisKeys {

    String REDIS_ANNCODE_FILE_MAPPING = "anncode:file";
    String REDIS_KEY_DN_INFO = "DN:INFO:";

    interface Online{
        String CALL_PRE = "ONLINE:CALL:";
        String SMS_PRE = "ONLINE:SMS:";

        String FIELD_RID = "requestId";
        String FIELD_CALLED_DN = "calledDn";
    }

    interface Apk{
        String APPKEY_INFO = "APPKEY:INFO:";
        String WHOLE_APPKEY = "wholeAppkey";
        String SECRET_KEY = "secretkey";
        String SERVICE_ROUTE_TABLE = "serviceRouteTable";
        String EXT_CRITERIA_TABLE = "extCriteriaTable";
        String SERVICE_FUNCTION = "serviceFunction";
        String SERVICE_PAYMENT = "servicePayment";
        String SERVICE_PUSH_CRITERIA = "servicePushCriteria";
        String SERVICE_AGENT_CRITERIA = "serviceAgentCriteria";
    }

    interface X {
        String SMCGT = "smcgt";
        String APPKEY = "appkey";
        String SERVICETYPE = "serviceType";
        String DNTAG = "dnTag";
        String AREACODE = "areaCode";
        String IMSI = "imsi";
        String STATUS = "status";
        String AX_BID = "axBid";
        String XYB_PRE = "xyb_";
        String AX_PRE = "ax_";

        String X_INFO_PRE = "X:INFO:";
        String X_INFO_FORMAT = X_INFO_PRE + "%s:%s";
        String X_BIND_PRE = "X:BIND:";
        String X_BIND_FORMAT = X_BIND_PRE + "%s:%s";

        String CALLBACK = "callback";
        String CALLBACK_EXT = CALLBACK + "_%s";
    }

    interface Sopen {
        String SOPEN_PRE = "SOPEN:";
        String SOPEN_KEY_FORMAT = "SOPEN:%s:%s:%s";
    }

    static String createXInfoKey(@NonNull String number) {
        String dnSegment;
        if (number.length() > 2) {
            dnSegment = number.substring(0, 3);
        } else {
            dnSegment = number;
        }
        return String.format(X.X_INFO_FORMAT, dnSegment, number);
    }
    static String createXBindKey(@NonNull String number) {
        String dnSegment;
        if (number.length() > 2) {
            dnSegment = number.substring(0, 3);
        } else {
            dnSegment = number;
        }
        return String.format(X.X_BIND_FORMAT, dnSegment, number);
    }

    static String createSopenKey(@NonNull String number, @NonNull String appkey) {
        String dnSegment;
        if (number.length() > 2) {
            dnSegment = number.substring(0, 3);
        } else {
            dnSegment = number;
        }
        return String.format(Sopen.SOPEN_KEY_FORMAT, appkey, dnSegment, number);
    }
}
