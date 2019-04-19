package cn.witsky.smb.util;

import cn.witsky.smb.Constant.AsCons;
import cn.witsky.smb.Constant.Cons;
import cn.witsky.smb.pojo.apk.ApkVSF;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2019/4/13.
 */
public class ResultUtil {
	public static JSONObject getNotiResult(String subID) {
		JSONObject result = new JSONObject();
		result.put("code", AsCons.SLFRES_CODE_OK);
		result.put("message", AsCons.SLFRES_MSG_OK);

		JSONObject data = new JSONObject();
		data.put("subid",subID);
		result.put("data",data);
		return  result;
	}
//	public static String getStatus(Cause cause) {
//if(cause==null)return null;
//		if(Objects.equals(cause.getPlan(), Mode.Q850)){
//			return  Cons.CallStatus.REJECT;
//		}else{
//			switch (cause.getReason()){
//				case Mode.UNKNOWN:return  Cons.CallStatus.UNKNOWN;
//				case Mode.NORESPONSE:return  Cons.CallStatus.NORESPONSE;
//				case Mode.BUSY:return  Cons.CallStatus.BUSY;
//				case Mode.ABSENT:return  Cons.CallStatus.ABSENT;
//				case Mode.SUSPEND:return  Cons.CallStatus.SUSPEND;
//				case Mode.REJECT:return  Cons.CallStatus.ANSWER;
//				default:return  Cons.CallStatus.REJECT;
//			}
//		}
//	}
	/**

	 * @param dir 释放方向
	 * @param cause 释放原因
	 * @param hadReceivedRingingProgress 是否已接收到RingingProgress

	 */
	public static String getStatus(boolean isTalked, int dir, int cause,
									   boolean hadReceivedRingingProgress,boolean timeOutWhenRing) {
//		logger.info(
//				"JavaUtils.releaseFormat()." + "starttime = [" + starttime + "], endtime = [" + endtime + "], dir = [" + dir
//						+ "], cause = [" + cause + "], hadReceivedRingingProgress = [" + hadReceivedRingingProgress
//						+ "], ringingTime = [" + ringingTime + "], minRingDuringTime = [" + minRingDuringTime + "]");
		if (0 < cause) {
			if (isTalked) {
				//通话时长大于0,释放方向1,2，0(超长60分钟）
				return Cons.CallStatus.ANSWER;
			} else if (!isTalked) {

				switch (dir) {
					case Cons.RELEASE_DIR_CALLER:
						//释放方向 1
						switch (cause) {
							case 1:
								//6	空号	INVALID_NUMBER	通话时长=0,释放方向1、2，原因值1
								return Cons.CallStatus.UNKNOWN;
							case 17:
								//2	被叫忙	BUSY 通话时长=0，释放方向1、2，原因值17

								//2018/10/24 19:40 : 需求更新，没有ring——被叫忙；有ring——被叫拒接；
								if (hadReceivedRingingProgress) {
									return Cons.CallStatus.REJECT;
								}
								return Cons.CallStatus.BUSY;
							case 19:
								//3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
								//1）释放方向1、2，原因值19；
								//2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
								return Cons.CallStatus.NORESPONSE;
							case 16:
								//目前16与31的情形一致。
							case 31:
								if (hadReceivedRingingProgress) {
									//if (endtime - ringingTime > minRingDuringTime * 1000) {
									if (timeOutWhenRing) {
										//3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
										//1）释放方向1、2，原因值19；
										//2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
										return Cons.CallStatus.NORESPONSE;
									} else {
										//通话时长=0， 1)没有“ring”，释放方向1、2，原因值16,31
										// 2)有“ring”且振铃时间小于20秒（releasetime-ringtime<20为可配置项）
										return  Cons.CallStatus.HANGUP;
									}
								} else {
									//5	主叫提前挂机	HANGUP	 通话时长=0，没有“ring”，释放方向1、2，原因值16,31
									return Cons.CallStatus.HANGUP;
								}

							case 21:
								//4	被叫拒接	REJECT
								//通话时长=0，有“ring”，释放方向1、2，原因值21
								if (hadReceivedRingingProgress) {
									return Cons.CallStatus.REJECT;
								}
								return Cons.CallStatus.OTHER;

							case 20:
								//7	关机	POWER_OFF
								//（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
								return Cons.CallStatus.ABSENT;
							case 27:
								//7	关机	POWER_OFF
								//（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
								return Cons.CallStatus.ABSENT;
							default:
								return Cons.CallStatus.OTHER;
						}

					case Cons.RELEASE_DIR_CALLED:
						switch (cause) {
							case 1:
								//6	空号	INVALID_NUMBER	通话时长=0,释放方向1、2，原因值1
								return Cons.CallStatus.UNKNOWN;
							case 17:
								//2	被叫忙	BUSY 通话时长=0，释放方向1、2，原因值17
								//2018/10/24 19:40 : 需求更新，没有ring——被叫忙；有ring——被叫拒接；
								if (hadReceivedRingingProgress) {
									return Cons.CallStatus.REJECT;
								}
								return Cons.CallStatus.BUSY;
							case 19:
								//3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
								//1）释放方向1、2，原因值19；
								//2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
								return Cons.CallStatus.NORESPONSE;
							case 21:
								//4	被叫拒接	REJECT
								//通话时长=0，有“ring”，释放方向1、2，原因值21
								if (hadReceivedRingingProgress) {
									return Cons.CallStatus.REJECT;
								}
								return Cons.CallStatus.OTHER;
							case 16:
								//目前16与31的情形一致。
							case 31:
								if (hadReceivedRingingProgress) {
									if (timeOutWhenRing) {
										//3	被叫无应答	NO_ANSWER 通话时长=0，(有两个情况)
										//1）释放方向1、2，原因值19；
										//2）有“ring”，主叫挂机，原因值16,31，且振铃时间大于20秒（releasetime-ringtime>20为可配置项
										return Cons.CallStatus.NORESPONSE;
									} else {
										//16	其他失败情形	OTHER
										return Cons.CallStatus.OTHER;
									}
								} else {
									//5	主叫提前挂机	HANGUP	 通话时长=0，没有“ring”，释放方向1、2，原因值16,31
									return Cons.CallStatus.HANGUP;
								}

							case 20:
								//7	关机	POWER_OFF
								//（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
								return Cons.CallStatus.ABSENT;
							case 27:
								//7	关机	POWER_OFF
								//（不准，很多转来电提醒）通话时长=0,释放方向1，2，原因值,20，27
								return Cons.CallStatus.ABSENT;
							default:
								return Cons.CallStatus.OTHER;
						}



					default:
						return Cons.CallStatus.OTHER;
				}
			}
		}
		return Cons.CallStatus.OTHER;

	}

	public static String getAnnFileName(ApkVSF vsf, String code) {
		String result=null;
		if(code.contains(".")){
			result=vsf.getVspath()+"/"+code;
		}else{
			result=vsf.getVspath()+"/"+code+"."+vsf.getVsext();
		}

		return  result;
	}
}
