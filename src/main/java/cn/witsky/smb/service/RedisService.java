package cn.witsky.smb.service;

import cn.witsky.smb.Constant.RedisKeys;
import cn.witsky.smb.config.Config;
import cn.witsky.smb.core.constant.CoreCons;
import cn.witsky.smb.core.utils.Utilities;
import cn.witsky.smb.pojo.JedisClient;
import cn.witsky.smb.pojo.SecretNOInfo;
import cn.witsky.smb.pojo.apk.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by HuangYX on 2018/4/13 13:33.
 *
 * @author HuangYX
 */
@Service
public class RedisService {
private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

private final StringRedisTemplate stringRedisTemplate;
//private final StringRedisTemplate baseRedisTemplate;
private final Config config;
@Autowired
        JedisClient jedisClient;
        HashOperations<String, String, String> hash;

        public RedisService(StringRedisTemplate stringRedisTemplate,
        Config config) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.config = config;
       hash = stringRedisTemplate.opsForHash();
}
      //  @TimeCost
public ApkConfig getApkConfigFromRedis  (String apk) throws IOException {
        if (apk == null) {
        throw new NullPointerException("appkey cannot be null");
        }
        int index = apk.indexOf(CoreCons.XHX);
        if (index > 0) {
        apk = apk.substring(0, index);
        }

        //HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash()
             //  Map<String, String> entries2=      jedisClient.hgetAll(RedisKeys.Apk.APPKEY_INFO + apk);
        Map<String, String> entries = hash.entries(RedisKeys.Apk.APPKEY_INFO + apk);
        if (entries == null || entries.size() == 0) {
        return null;
        }
                long start = System.currentTimeMillis();
                final String wholeAppkeys = entries.get(RedisKeys.Apk.WHOLE_APPKEY);
                final String secretkey = entries.get(RedisKeys.Apk.SECRET_KEY);
                final ApkServiceRouteTable serviceRouteTable = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.SERVICE_ROUTE_TABLE), ApkServiceRouteTable.class);
                long  end = System.currentTimeMillis();
                logger.debug("parse serviceRouteTable"+"花费了"+(end-start)+"毫秒");

                final ApkExtCriteriaTable extCriteriaTable = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.EXT_CRITERIA_TABLE), ApkExtCriteriaTable.class);
                end = System.currentTimeMillis();
                logger.debug("parse extCriteriaTable"+"花费了"+(end-start)+"毫秒");
        logger.debug(entries.get(RedisKeys.Apk.EXT_CRITERIA_TABLE));
                final ApkServiceFunction serviceFunction = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.SERVICE_FUNCTION), ApkServiceFunction.class);
                end = System.currentTimeMillis();
                logger.debug("parse serviceFunction"+"花费了"+(end-start)+"毫秒");
        logger.debug(entries.get(RedisKeys.Apk.SERVICE_FUNCTION));
                final ApkServicePayment servicePayment = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.SERVICE_PAYMENT), ApkServicePayment.class);
                final ApkServicePushCriteria servicePushCriteria = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.SERVICE_PUSH_CRITERIA), ApkServicePushCriteria.class);
                final ApkServiceAgentCriteria serviceAgentCriteria = Utilities.parseObject(
                        entries.get(RedisKeys.Apk.SERVICE_AGENT_CRITERIA), ApkServiceAgentCriteria.class);
                end = System.currentTimeMillis();
                logger.debug("parse"+"花费了"+(end-start)+"毫秒");
                start = System.currentTimeMillis();
                ApkConfig build = ApkConfig.builder()
                        .wholeAppkey(wholeAppkeys)
                        .secretkey(secretkey)
                        .serviceRouteTable(serviceRouteTable)
                        .extCriteriaTable(extCriteriaTable)
                        .serviceFunction(serviceFunction)
                        .servicePayment(servicePayment)
                        .servicePushCriteria(servicePushCriteria)
                        .serviceAgentCriteria(serviceAgentCriteria)
                        .build();
                end = System.currentTimeMillis();
                logger.debug("build"+"花费了"+(end-start)+"毫秒");
        FileWriter fileWriter = new FileWriter(new File("C://1.txt"));
        IOUtils.write(JSONObject.toJSONString(build),fileWriter);
        fileWriter.close();
                return build;
        }


        public SecretNOInfo getSecretNOInfo(String telX) {
            try {
                    HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
                    String xInfoKey = RedisKeys.createXInfoKey(telX);
                    Map<String, String> entries = hashOperations.entries(xInfoKey);
                    SecretNOInfo secretNOInfo = new SecretNOInfo();
                    secretNOInfo.setAppkey(entries.get(RedisKeys.X.APPKEY));
                    secretNOInfo.setServiceType(entries.get(RedisKeys.X.SERVICETYPE));
                    secretNOInfo.setDnTag(entries.get(RedisKeys.X.DNTAG));
                    secretNOInfo.setSmcgt(entries.get(RedisKeys.X.SMCGT));
                    secretNOInfo.setAreaCode(entries.get(RedisKeys.X.AREACODE));
                    secretNOInfo.setDn(telX);
                    if (secretNOInfo.getServiceType() != null && secretNOInfo.getAppkey() != null && secretNOInfo.getSmcgt() != null
                            && secretNOInfo.getDnTag() != null) {
                            return secretNOInfo;
                    }
                    logger.info("GetSecretNOInfo[{}].some fields are null, entries = {}", telX, entries);
                    return null;
            } catch (Exception e) {
                    logger.error("RedisServiceImpl.getSecretNOInfo().EXCEPTION,data = {}, cause of = {}", telX, e.getMessage());
                    return null;
            }
	}
}
