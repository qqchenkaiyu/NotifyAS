package cn.witsky.smb.service;

import cn.witsky.smb.annotation.TimeCost;
import cn.witsky.smb.pojo.SecretNOInfo;
import cn.witsky.smb.pojo.apk.ApkConfig;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * 缓存服务。需要通过缓存回调服务提供缓存查找功能；
 * 如只需缓存不需要回调服务的，直接注入缓存使用即可。
 *
 * @author HuangYX
 */
@Service
public class CacheService {
private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
private  RedisService redisService;
private  Cache<String, ApkConfig> apkConfigCache;
    private     Cache<String, SecretNOInfo> telXInfoCache;

@Autowired
public CacheService(
        @Qualifier("cacheForTelXInfo") Cache<String, SecretNOInfo> telXInfoCache,
        @Qualifier("cacheForApkConfig") Cache<String, ApkConfig> apkConfigCache, RedisService redisService) {
        this.apkConfigCache = apkConfigCache;
        this.redisService = redisService;
    this.telXInfoCache = telXInfoCache;


        }
    @TimeCost
    ApkConfig getApkConfig(String appkey) throws ExecutionException {
            return apkConfigCache.get(appkey, () -> redisService.getApkConfigFromRedis(appkey));
    }
    public SecretNOInfo getSecretNOInfo(final String telX) {
        try {
            return telXInfoCache.get(telX, () -> redisService.getSecretNOInfo(telX));
        } catch (Exception e) {
            logger.error("EXCEPTION@getSecretNOInfo(),cause of={}",e.getMessage());
        }
        return null;
    }


}
