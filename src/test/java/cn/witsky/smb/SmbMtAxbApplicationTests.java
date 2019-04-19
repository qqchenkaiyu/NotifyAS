package cn.witsky.smb;

import cn.witsky.smb.config.Config;
import cn.witsky.smb.pojo.JedisClient;
import cn.witsky.smb.pojo.apk.ApkConfig;
import cn.witsky.smb.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ConditionalOnBean(Config.class)
public class SmbMtAxbApplicationTests {
@Autowired
	RedisService  redisService;
	@Autowired
	JedisClient jedisClient;
//	@Autowired
//	Config config;

 	@Test
	public void contextLoads() {
		URI uri1 = URI.create("http://192.168.110.151:8080/test");
	//	URI uri = URI.create(config.getNotifyUrl());
		ApkConfig shuc = null;
		//try {
String temp="{\"extCriteriaTable\":{\"tp1\":{\"callCriteria\":{\"control\":\"0\"},\"smsCriteria\":{\"control\":\"0\"}},\"tp2\":[{\"serviceCriteria\":[{\"priority\":\"90\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"1\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.233:7003/apk/SubsequnentCriteria\",\"type\":\"0\"}}],\"serviceType\":\"AXB\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"11\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.233:7003/apk/SubsequnentCriteria\",\"type\":\"1\"}}],\"serviceType\":\"AXYB_AX\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"10\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"originating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.160:5201/nas/access\",\"type\":\"1\"}}],\"serviceType\":\"AXYB\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"10\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"originating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.160:5201/nas/access\",\"type\":\"1\"}}],\"serviceType\":\"SOP\"}],\"tp3\":{\"defaulthandle\":\"1\",\"displaylevel\":\"1\"}},\"secretkey\":\"secret_for_SHUC\",\"serviceFunction\":{\"dfattone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"},\"sulist\":[{\"serviceType\":\"AXYB_AX\",\"sfd\":{\"callfd\":{\"orig\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}},\"term\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}}},\"expiration\":\"86400\",\"extPrivatefd\":{\"extTone\":{\"extCollectTone\":{\"collectcount\":4,\"collecttone\":\"pc002\",\"collectwaittime\":20,\"illegaltone\":\"pc003\",\"inittone\":\"pc001\",\"initwaittime\":15}}},\"smsfd\":{\"orig\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"}},\"term\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"}}}}},{\"serviceType\":\"AX2B\",\"sfd\":{\"callfd\":{\"orig\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}},\"term\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}}},\"expiration\":\"86400\",\"extPrivatefd\":{\"extTone\":{\"extCollectTone\":{\"collectcount\":4,\"collecttone\":\"pc002\",\"collectwaittime\":20,\"illegaltone\":\"pc003\",\"inittone\":\"pc001\",\"initwaittime\":15}}},\"smsfd\":{\"orig\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"}},\"term\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"}}}}}],\"vsf\":{\"vsext\":\"mp3\",\"vspath\":\"http://192.168.110.186:5758\"}},\"servicePushCriteria\":{\"callPushCriteria\":{\"callFinishPush\":{\"interval\":60,\"pushurl\":\"http://192.168.150.233:19999/call/record/push/finish\",\"retrycount\":1,\"retryinterval\":60}}},\"serviceRouteTable\":{\"asRoute\":{\"notifyURL\":\"http://192.168.150.233:7003/apk/NotifyURL\",\"pushURL\":\"http://192.168.150.233:7003/apk/PushURL\",\"serviceURL\":\"http://192.168.150.233:7003/apk/ServiceURL\",\"subscribeURL\":\"http://192.168.150.233:7003/apk/SubscribeURL\"},\"recoveryRoute\":{\"notifyURL\":\"http://192.168.150.233:7003/apk/recovery/NotifyURL\",\"pushURL\":\"http://192.168.150.233:7003/apk/recovery/PushURL\",\"serviceURL\":\"http://192.168.150.233:7003/apk/recovery/ServiceURL\",\"subscribeURL\":\"http://192.168.150.233:7003/apk/recovery/SubscribeURL\"}},\"wholeAppkey\":\"SHUC\"}";
		String temp2="{\"extCriteriaT\":{\"tp1\":{\"callCriteria\":{\"control\":\"0\"},\"smsCriteria\":{\"control\":\"0\"}},\"tp2\":[{\"serviceCriteria\":[{\"priority\":\"90\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"1\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.233:7003/apk/SubsequnentCriteria\",\"type\":\"0\"}}],\"serviceType\":\"AXB\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"11\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.233:7003/apk/SubsequnentCriteria\",\"type\":\"1\"}}],\"serviceType\":\"AXYB_AX\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"10\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"originating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.160:5201/nas/access\",\"type\":\"1\"}}],\"serviceType\":\"AXYB\"},{\"serviceCriteria\":[{\"priority\":\"2\",\"serviceKey\":\"10\",\"triggerDetection\":[{\"conditionMethod\":\"call\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"terminating\"},{\"conditionMethod\":\"sms\",\"conditionType\":\"0\",\"sessionCase\":\"originating\"}],\"triggerDetectionURL\":{\"address\":\"http://192.168.150.160:5201/nas/access\",\"type\":\"1\"}}],\"serviceType\":\"SOP\"}],\"tp3\":{\"defaulthandle\":\"1\",\"displaylevel\":\"1\"}},\"secretkey\":\"secret_for_SHUC\",\"serviceFunction\":{\"dfattone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"},\"sulist\":[{\"serviceType\":\"AXYB_AX\",\"sfd\":{\"callfd\":{\"orig\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}},\"term\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}}},\"expiration\":\"86400\",\"extPrivatefd\":{\"extTone\":{\"extCollectTone\":{\"collectcount\":4,\"collecttone\":\"pc002\",\"collectwaittime\":20,\"illegaltone\":\"pc003\",\"inittone\":\"pc001\",\"initwaittime\":15}}},\"smsfd\":{\"orig\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"}},\"term\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"}}}}},{\"serviceType\":\"AX2B\",\"sfd\":{\"callfd\":{\"orig\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}},\"term\":{\"cid_display\":\"0\",\"record\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"},\"tone\":{\"collectdgts\":\"3\",\"finish\":\"4\",\"illegal\":\"1\",\"noright\":\"6\",\"restrict\":\"5\",\"ringback\":\"2\"}}},\"expiration\":\"86400\",\"extPrivatefd\":{\"extTone\":{\"extCollectTone\":{\"collectcount\":4,\"collecttone\":\"pc002\",\"collectwaittime\":20,\"illegaltone\":\"pc003\",\"inittone\":\"pc001\",\"initwaittime\":15}}},\"smsfd\":{\"orig\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"annc\":\"101\",\"ctrl\":\"0\",\"push\":\"0\"}},\"term\":{\"mo_channel\":\"0\",\"mt_channel\":\"0\",\"restrict\":{\"ctrl\":\"1\",\"push\":\"1\"}}}}}],\"vsf\":{\"vsext\":\"mp3\",\"vspath\":\"http://192.168.110.186:5758\"}},\"servicePushCriteria\":{\"callPushCriteria\":{\"callFinishPush\":{\"interval\":60,\"pushurl\":\"http://192.168.150.233:19999/call/record/push/finish\",\"retrycount\":1,\"retryinterval\":60}}},\"serviceRouteTable\":{\"asRoute\":{\"notifyURL\":\"http://192.168.150.233:7003/apk/NotifyURL\",\"pushURL\":\"http://192.168.150.233:7003/apk/PushURL\",\"serviceURL\":\"http://192.168.150.233:7003/apk/ServiceURL\",\"subscribeURL\":\"http://192.168.150.233:7003/apk/SubscribeURL\"},\"recoveryRoute\":{\"notifyURL\":\"http://192.168.150.233:7003/apk/recovery/NotifyURL\",\"pushURL\":\"http://192.168.150.233:7003/apk/recovery/PushURL\",\"serviceURL\":\"http://192.168.150.233:7003/apk/recovery/ServiceURL\",\"subscribeURL\":\"http://192.168.150.233:7003/apk/recovery/SubscribeURL\"}},\"wholeAppkey\":\"SHUC\"}";

//jedisClient.set("testJson1","2");
		//String testJson = jedisClient.get("testJson");
		String testJson = temp;
		//jedisClient.set("testJson",testJson);
		//Utilities.parseObject(testJson,ApkConfig.class);

		JSONObject parse6 = (JSONObject) JSONObject.parse("{}");
		parse6.toJavaObject(ApkConfig.class);
		long  start = System.currentTimeMillis();
		//JSONObject parse = (JSONObject) JSONObject.parse(testJson);
		JSONObject parse = (JSONObject) JSONObject.parse(testJson);
		//parse.toJavaObject(ApkConfig.class);
		JSONObject parse1 = (JSONObject) JSONObject.parse(temp2);
		JSONObject parse3 = (JSONObject) JSONObject.parse(testJson);

		//parse.toJavaObject(ApkConfig.class);
	//	ApkConfig t = JSONObject.toJavaObject((JSON) parse, ApkConfig.class);
		long  end = System.currentTimeMillis();
			System.out.println("parse shuc"+"花费了"+(end-start)+"毫秒");
		System.out.println(parse.toJSONString());
		//jedisClient.set("testJson1","2");
//			shuc = redisService.getApkConfigFromRedis("SHUC");
//			long  start = System.currentTimeMillis();
//			String s = JSONObject.toJSONString(shuc);
//			long  end = System.currentTimeMillis();
//			System.out.println("parse shuc"+"花费了"+(end-start)+"毫秒");
//			jedisClient.set("testJson",s );
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//System.out.println(shuc.toString());
	}

}
