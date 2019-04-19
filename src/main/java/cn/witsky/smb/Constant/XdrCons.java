package cn.witsky.smb.Constant;

/**
 * @author HuangYX
 * @date 2019/1/3 19:21
 */
public interface XdrCons {


  interface ProceedingType {
    /**
     * 正常接续
     */
    String NORMAL_CONNECT = "0";
    /**
     * 用户白名单
     */
    String USER_WHITE_LIST = "1";

    /**
     * 系统黑名单
     */
    String SYSTEM_BLACK_LIST = "10";
    /**
     * 用户黑名单"
     */
    String USER_BLACK_LIST = "11";

    /**
     * 非系统白名单
     */
    String NO_SYS_WHITE_LIST = "12";

    //2x：查询类

    /**
     * 21：查询超时
     */
    String QUERY_TIMEOUT = "21";

    /**
     * 22：查询，释放
     */
    String QUERY_REJECT = "22";

    /** 短信 */
    String QUERY_SMCGT_ERROR = "41";


    String NON_BIND = "99";
  }

  interface CallMode {

      /**
       * 0：大网直呼
       */
      String DIRECT_CALL = "0";
      /**
       * 1：在线呼出
       */
      String ONLINE_CALL_OUT = "1";
      /**
       * 2：DTMF呼出
       */
      String DTMF_CALL_OUT = "2";
      /**
       * 3：呼叫转接
       */
      String TRANSFER_CALL = "3";
      /**
       * 11：双呼
       */
      String TWICE_CALL = "11";
  }

  interface SmsMode {
      String POINT_TO_POINT = "1";
      String ONLINE_TO_SEND = "2";
      String ONLINE_TO_RECEIVE = "3";
      String POINT_TO_POINT_AND_ONLINE_TO_RECEIVE = "4";
      String POINT_TO_POINT_AND_REPORT = "5";
      String ONLINE_TO_RECEIVE_AND_REPORT = "6";
      String INTERCEPT = "9";
  }

  interface CallType {
      String UNKNOWN = "0";
      String A_CALLIN = "1";
      String A_CALLED = "2";
  }

  interface Platform{
      String PLATFORM_ID_CCF = "%s_ASF-%s_CCF-%s";
      String PLATFORM_ID = "%s_ASF-%s";
  }
}
