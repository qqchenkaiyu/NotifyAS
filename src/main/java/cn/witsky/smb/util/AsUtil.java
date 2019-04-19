package cn.witsky.smb.util;

import cn.witsky.smb.Constant.Cons;
import cn.witsky.smb.Constant.Cons.SessionCase;
import cn.witsky.smb.Constant.RecordMode;
import cn.witsky.smb.Constant.ServiceKeyCons;
import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.core.constant.Str;
import cn.witsky.smb.core.utils.Utilities;
import cn.witsky.smb.pojo.CheckRes;
import cn.witsky.smb.pojo.apk.ApkConfig;
import cn.witsky.smb.pojo.communication.*;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuangYX
 * @date 2018/5/29 15:48
 */
public class AsUtil extends AsSlfUtil {

  private static Logger logger = LoggerFactory.getLogger(AsUtil.class);

  public static String generateSessionId(String sessionIdAddressInfo, String asRootUrl, String localSessionId) {
    SessionId sessionId = Utilities.parseObject(sessionIdAddressInfo, SessionId.class);
    sessionId.setA(asRootUrl);
    sessionId.setS(localSessionId);
    return sessionId.toJson();
  }

  public static boolean checkTestSessionResult(SlfSessionTest slfSessionTest) {
    if (slfSessionTest == null) {
      return false;
    }
    return Int.INTEGER_ZERO.equals(slfSessionTest.getCode());
  }

  /**
   *
   */
  public static String joinDestUrl(String rootUrl, String uri) {
    if (Objects.equals(rootUrl, CoreCons.EMPTY)) {
      return uri;
    } else if (Objects.equals(rootUrl, null)) {
      return CoreCons.EMPTY;
    } else if (rootUrl.endsWith(CoreCons.BACKSLASH) && uri.startsWith(CoreCons.BACKSLASH)) {
      return rootUrl.substring(0, rootUrl.length() - 2) + uri;
    } else if (!rootUrl.endsWith(CoreCons.BACKSLASH) && !uri.startsWith(CoreCons.BACKSLASH)) {
      return rootUrl + CoreCons.BACKSLASH + uri;
    } else {
      return rootUrl + uri;
    }
  }




  public static CheckRes getCheckRes(boolean ok, String msg) {
    return new CheckRes(ok, msg);
  }

  public static String getTp1CheckFailAnncode(ApkConfig apkConfig) {
    return null;
  }

  public static String getTp2CheckFailAnncode(ApkConfig apkConfig) {
    return null;
  }


  /**
   * 根据录音字段生成录音字段
   * <p>
   * 20170901A： ${号码1}_${号码1}_${号码2}_${号码3}_${呼叫标识} A_A_X_B_CALLID 例子：13000000000_13000000000_14000000000_15000000000_6359f2d3e1252d14
   * ${号码3}_${号码1}_${号码2}_${号码3}_${呼叫标识} B_A_X_B_CALLID 例子：15000000000_13000000000_14000000000_15000000000_6359f2d3e1252d14
   * <p>
   * 20171130A ${接入方标识}_${接入方子标识}_${主被叫标识}_${通话侧标识}_${号码1}_${号码2}_${号码3}_${时间戳}_${呼叫标识}_${板卡信息}
   * ${接入方标识}_${接入方子标识}_${主被叫标识}_${通话侧标识}_${号码1}_${号码2}_${号码3}_${时间戳}_${呼叫标识}_${板卡信息} 字段 接入方标识/接入方子标识/板卡信息 均可配置； 字段
   * 主被叫标识：0-主叫，1-被叫（针对A）； 字段 通话侧标识：A-A侧录音，B-B侧录音； 字段 时间戳：年月日时分秒
   *
   * @param recordDir 阿里模式需要，录音目录，witsky.astoslf-record-dir
   * @param callId 呼叫ID
   * @param telA A号码
   * @param telB B号码
   * @param telX X号码
   * @param recordFileType 录音文件类型，0： ${号码1}_${号码1}_${号码2}_${号码3}_${呼叫标识}  ； 1：${接入方标识}_${接入方子标识}_${主被叫标识}_${通话侧标识}_${号码1}_${号码2}_${号码3}_${时间戳}_${呼叫标识}_${板卡信息}
   * @param callType 呼叫类型
   * @param appName 接入方标识
   * @param subAppName 接入方子标识
   * @param recordMode 录音模式
   * @param timestamp 时间戳
   * @return AsToSlfRecordInfo or null
   */
  public static AsToSlfRecordInfo createRecordInfo(String recordDir, String callId, String telA, String telB,
      String telX,
      int recordFileType, String callType, String appName,
      String subAppName, String recordMode, String timestamp) {

    int recordModes = RecordMode.ALERTING_REC.equals(recordMode) ?
        RecordMode.TO_EASTCOM_ALERTING_REC_MODE : RecordMode.TO_EASTCOM_CONNECTED_REC_MODE;
    AsToSlfRecordInfo recordInfo = new AsToSlfRecordInfo(recordModes);
    List<String> namePrefixes = new ArrayList<>();

    if (recordFileType == Int.ZERO) {
      namePrefixes.add(recordDir + Joiner.on(CoreCons.XHX).join(telA, telA, telX, telB, callId));
      namePrefixes.add(recordDir + Joiner.on(CoreCons.XHX).join(telB, telA, telX, telB, callId));
    } else {
      namePrefixes.add(recordDir + Joiner.on(CoreCons.XHX).join(appName, subAppName, callType,
          CoreCons.A, telA, telX, telB, timestamp, callId));
      namePrefixes.add(recordDir + Joiner.on(CoreCons.XHX).join(appName, subAppName, callType,
          CoreCons.B, telA, telX, telB, timestamp, callId));
    }
    recordInfo.setNamePrefixes(namePrefixes);
    return recordInfo;
  }





  public static String createRecordUrl(String recordUrl, String fullAppkey,
      String callId, String telA, String telB,
      String timestamp, String recordUrlSuffix) {
    logger.debug("[createRecordUrl]recordUrl={},fullAppkey={},callId={},telA={},telB={},timestamp={},recordUrlSuffix={}",
            recordUrl, fullAppkey, callId, telA, telB, timestamp, recordUrlSuffix);
    return Joiner.on("").join(
        recordUrl,
        Joiner.on(CoreCons.XHX).join(
            fullAppkey,
            callId,
            telA,
            telB,
            timestamp
        ),
        recordUrlSuffix
    );

  }

  public static String createRecordUrl(String recordUrl, String vesion, String recordUrlSuffix, String loadFmt, String appkey, String caller,
                                 String called, String telX, String ts, String callId) {
    ArrayList<String> data = Lists.newArrayList(appkey, caller, called, telX, ts, callId);
    String backslash = recordUrl.endsWith(CoreCons.BACKSLASH) ? CoreCons.EMPTY : CoreCons.BACKSLASH;
    String fmtUrl;
    if (StringUtils.isBlank(loadFmt)) {
      fmtUrl = Joiner.on(CoreCons.XHX).join(data);
    } else {
      fmtUrl =Joiner.on(CoreCons.XHX).join(Lists.newArrayList(loadFmt.split(CoreCons.COMMA))
              .stream().map(e -> data.get(Integer.parseInt(e)))
              .collect(Collectors.toList()));
    }
    return Joiner.on("").join(
            recordUrl + backslash + vesion + CoreCons.BACKSLASH + ts + CoreCons.BACKSLASH,
            fmtUrl,
            recordUrlSuffix);
  }

  public static String createPushApk(String apk, String subApk) {
    if (StringUtils.isBlank(subApk)) {
      return apk;
    }
    return Joiner.on(CoreCons.XHX).join(apk, subApk);
  }

  private static String index;

  static {
    index = String.valueOf(new Random().nextInt(8) + 1);
  }

  public static String getIndex() {
    return index;
  }


  public static String parseSessionId(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return null;
    }
    try {
      SessionId sessionId = JSON.parseObject(text, SessionId.class);
      if (sessionId != null) {
        return sessionId.toJson();
      }
      return null;
    } catch (Exception e) {
      logger.error("parse_sessionId_ERROR,cause of = " + e.getMessage() + "; text=" + text);
      return null;
    }
  }

  public static String parseLocalSessionId(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return null;
    }
    try {
      SessionId sessionId = JSON.parseObject(text, SessionId.class);
      if (sessionId != null) {
        return sessionId.getS();
      }
      return null;
    } catch (Exception e) {
      logger.error("parse_sessionId_ERROR,cause of = " + e.getMessage() + "; text=" + text);
      return null;
    }
  }

  public static String[] splitAppkey(String appkey) {
    if (appkey == null) {
      return new String[]{"", ""};
    }

    List<String> s = Lists.newArrayList(Splitter.on("_").split(appkey));
    if (s.size() == Int.TWO) {
      return new String[]{s.get(0), s.get(1)};
    } else if (s.size() == Int.ONE) {
      return new String[]{s.get(0), "0000"};
    } else {
      return new String[]{s.get(0), appkey.substring(s.get(0).length())};
    }

  }

  public static Map<String, Object> getStateMachineContextData() {
    return Maps.newHashMap();
  }

  public static Map<String, Object> getStateMachineContextData(Object o) {
    HashMap<String, Object> map = Maps.newHashMap();
    map.put(Cons.DATA, o);
    return map;
  }

  public static Map<String, Object> getStateMachineContextData(Object data, String serviceKey) {
    HashMap<String, Object> map = Maps.newHashMap();
    if (data != null) {
      map.put(Cons.DATA, data);
    }
    map.put(ServiceKeyCons.SERVICE_KEY, serviceKey);
    return map;
  }

  public static Map<Object, Object> getStateMachineContextData(Object data, Object... o) {
    HashMap<Object, Object> map = Maps.newHashMap();
    if (data != null) {
      map.put(Cons.DATA, data);
    }
    for (int i = 0; i < o.length; i += 2) {
      if (i < o.length - 1) {
        map.put(o[i], o[i + 1]);
      }
    }
    return map;
  }

  public static Map<String, Object> getStateMachineContextDataOther(Object cause, Object status) {
    HashMap<String, Object> map = Maps.newHashMap();
    map.put(Cons.FINISH_CAUSE, cause);
    map.put(Cons.FINISH_STATUS, status);
    return map;
  }

  public static Map<String, Object> getStateMachineContextDataCause(Object cause) {
    HashMap<String, Object> map = Maps.newHashMap();
    map.put(Cons.CAUSE, cause);
    return map;
  }

  public static Map<Object, Object> getStateMachineContextDataRelease(Object... o) {
    HashMap<Object, Object> map = Maps.newHashMap();
    for (int i = 0;
        i < o.length;
        i += 2) {
      if (i < o.length - 1) {
        map.put(o[i], o[i + 1]);
      }
    }
    return map;
  }

  public static Map<String, Object> getStateMachineContextDataCheckFail(Object annCode, Object cause) {
    HashMap<String, Object> map = Maps.newHashMap();
    map.put(Cons.FINISH_CAUSE, cause);
    map.put(Cons.DATA_ANNCODE, annCode);
    return map;
  }

  public static Map<Object, Object> toMapObjectObject(Map<String,Object> map) {
    if (map == null) {
      return null;
    }
    return Maps.newHashMap(map);
  }


  public static boolean isSlfResOk(SlfRes res) {
    if (res == null) {
      return false;
    }
    return Objects.equals(Int.INTEGER_ZERO, res.getCode());
  }


  /**
   * @param starttime 呼叫接通开始时间
   * @param endtime 呼叫接通结束时间
   * @param dir 释放方向
   * @param cause 释放原因
   * @param hadReceivedRingingProgress 是否已接收到RingingProgress
   * @param ringingTime ringing时间戳
   * @param minRingDuringTime 最小振铃持续时间
   */
  public static String releaseFormat(long starttime, long endtime, int dir, int cause,
      boolean hadReceivedRingingProgress, long ringingTime, int minRingDuringTime) {
    logger.info(
        "JavaUtils.releaseFormat()." + "starttime = [" + starttime + "], endtime = [" + endtime + "], dir = [" + dir
            + "], cause = [" + cause + "], hadReceivedRingingProgress = [" + hadReceivedRingingProgress
            + "], ringingTime = [" + ringingTime + "], minRingDuringTime = [" + minRingDuringTime + "]");
    if (0 < cause) {
      if (endtime - starttime > Int.ZERO) {
        //通话时长大于0,释放方向1,2，0(超长60分钟）
        return Str.ONE;
      } else if (starttime == Int.ZERO || endtime - starttime == Int.ZERO) {

        switch (dir) {
          case Cons.RELEASE_DIR_CALLER:
            //释放方向 1
            switch (cause) {
              case 1:
                //6	空号	INVALID_NUMBER	通话时长=0,释放方向1、2，原因值1
                return Str.SIX;
              case 17:
                //2	被叫忙	BUSY 通话时长=0，释放方向1、2，原因值17

                //2018/10/24 19:40 : 需求更新，没有ring——被叫忙；有ring——被叫拒接；
                if (hadReceivedRingingProgress) {
                  return Str.FOUR;
                }
                return Str.TWO;
              case 19:
                //3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
                //1）释放方向1、2，原因值19；
                //2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
                return Str.THREE;
              case 16:
                //目前16与31的情形一致。
              case 31:
                if (hadReceivedRingingProgress) {
                  if (endtime - ringingTime > minRingDuringTime * 1000) {
                    //3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
                    //1）释放方向1、2，原因值19；
                    //2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
                    return Str.THREE;
                  } else {
                    //通话时长=0， 1)没有“ring”，释放方向1、2，原因值16,31
                    // 2)有“ring”且振铃时间小于20秒（releasetime-ringtime<20为可配置项）
                    return Str.FIVE;
                  }
                } else {
                  //5	主叫提前挂机	HANGUP	 通话时长=0，没有“ring”，释放方向1、2，原因值16,31
                  return Str.FIVE;
                }

              case 21:
                //4	被叫拒接	REJECT
                //通话时长=0，有“ring”，释放方向1、2，原因值21
                if (hadReceivedRingingProgress) {
                  return Str.FOUR;
                }
                return Str.SIXTEEN;

              case 20:
                //7	关机	POWER_OFF
                //（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
                return Str.SEVEN;
              case 27:
                //7	关机	POWER_OFF
                //（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
                return Str.SEVEN;
              default:
                return Str.SIXTEEN;
            }

          case Cons.RELEASE_DIR_CALLED:
            switch (cause) {
              case 1:
                //6	空号	INVALID_NUMBER	通话时长=0,释放方向1、2，原因值1
                return Str.SIX;
              case 17:
                //2	被叫忙	BUSY 通话时长=0，释放方向1、2，原因值17
                //2018/10/24 19:40 : 需求更新，没有ring——被叫忙；有ring——被叫拒接；
                if (hadReceivedRingingProgress) {
                  return Str.FOUR;
                }
                return Str.TWO;
              case 19:
                //3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
                //1）释放方向1、2，原因值19；
                //2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
                return Str.THREE;
              case 21:
                //4	被叫拒接	REJECT
                //通话时长=0，有“ring”，释放方向1、2，原因值21
                if (hadReceivedRingingProgress) {
                  return Str.FOUR;
                }
                return Str.SIXTEEN;
              case 16:
                //目前16与31的情形一致。
              case 31:
                if (hadReceivedRingingProgress) {
                  if (endtime - ringingTime > minRingDuringTime * 1000) {
                    //3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
                    //1）释放方向1、2，原因值19；
                    //2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
                    return Str.THREE;
                  } else {
                    //16	其他失败情形	OTHER
                    return Str.SIXTEEN;
                  }
                } else {
                  //5	主叫提前挂机	HANGUP	 通话时长=0，没有“ring”，释放方向1、2，原因值16,31
                  return Str.FIVE;
                }

              case 20:
                //7	关机	POWER_OFF
                //（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
                return Str.SEVEN;
              case 27:
                //7	关机	POWER_OFF
                //（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
                return Str.SEVEN;
              default:
                return Str.SIXTEEN;
            }

          case Cons.RELEASE_DIR_PLATFORM:
            //10	无绑定关系	TP_NO_BINDING	接口查询未返回正确真实号码		通话时长=0,释放方向0
            return Str.TEN;

          default:
            return Str.SIXTEEN;
        }
      }
    }
    return Str.SIXTEEN;

  }


  /**
   * 获取请求发送的响应
   */
  public static CheckRes getCheckSuccessRes(String tp, String msg) {
    return new CheckRes(true, createMsg(tp, msg));
  }

  /**
   * 获取请求未发送的响应
   */
  public static CheckRes getCheckFailRes(String tp, String msg) {
    return new CheckRes(false, createMsg(tp, msg));
  }

  private static String createMsg(String tp, String msg) {
    return String.format("[%s]%s", tp, msg);
  }

  /**
   * 检查收号信息合法性
   *
   * @param report SlfServiceExecuteReport - 收号信息
   */
  public static boolean checkExecuteReportInfo(SlfServiceExecuteReport report) {
    return report != null
        && report.getDgts() != null
        && report.getResult() != null
        && report.getResult().length == Int.TWO
        && report.getResult()[Int.ZERO] == Int.ZERO;

  }

  public static String switchRecMode(String sessionCase) {
    String recMode;
    switch (sessionCase) {
      case SessionCase.ORIGINATING:
        recMode = Cons.RecMode.LEFT;
        break;
      case SessionCase.TERMINATING:
        recMode = Cons.RecMode.RIGHT;
        break;
      default:
        recMode = Cons.RecMode.RIGHT;
        break;
    }
    return recMode;
  }

  public static boolean isResultOk(int[] result) {
    return null != result && result.length == Int.TWO && result[Int.ZERO] == Int.ZERO;
  }


}
