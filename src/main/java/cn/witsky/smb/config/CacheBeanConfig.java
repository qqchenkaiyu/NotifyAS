package cn.witsky.smb.config;

import cn.witsky.smb.core.constant.Int;
import cn.witsky.smb.pojo.SecretNOInfo;
import cn.witsky.smb.pojo.apk.ApkConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author HuangYX
 */
@Configuration
@EnableCaching
public class CacheBeanConfig {

    private final Config config;

    @Autowired
    public CacheBeanConfig(Config config) {
        this.config = config;
    }


    /**
     * 缓存放音文件信息
     *
     * @return 放音文件cache
     */
    @Bean(name = "cacheForAnnCode")
    public Cache<String, String> guavaCacheForAnnCode() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(Int.ONE, TimeUnit.DAYS);
        return cacheBuilder.build();
    }


    /**
     * 缓存中间虚拟号码信息
     *
     * @return 中间虚拟号码cache
     */
    @Bean(name = "cacheForApkConfig")
    public Cache<String, ApkConfig> guavaCacheForApkInfo() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(Int.TWO, TimeUnit.MINUTES);
        return cacheBuilder.build();
    }
    /**
     * 缓存中间虚拟号码信息
     *
     * @return 中间虚拟号码cache
     */
    @Bean(name = "cacheForTelXInfo")
    public Cache<String, SecretNOInfo> guavaCacheForTelXInfo() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(Int.TEN, TimeUnit.MINUTES);
        return cacheBuilder.build();
    }
}
