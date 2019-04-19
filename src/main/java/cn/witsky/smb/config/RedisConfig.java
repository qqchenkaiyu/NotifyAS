package cn.witsky.smb.config;

import cn.witsky.smb.pojo.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	@Bean
	public JedisPool getJedisPool(@Value("${spring.redis.host}")String hostName,
								  @Value("${spring.redis.port}")int port,
								  @Value("${spring.redis.pool.max-active}")int maxActive,
								  @Value("${spring.redis.pool.max-idle}")int maxIdle,
								  @Value("${spring.redis.pool.min-idle}")int minIdle){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMinIdle(minIdle);
		JedisPool jedisPool = new JedisPool(hostName,port);
		jedisPool.addObjects(minIdle);
		return jedisPool;
	}
	@Bean
	@Autowired
	public JedisClient getJedisClient(JedisPool pool){
		JedisClient jedisClient = new JedisClient();
		jedisClient.setJedisPool(pool);
		return jedisClient;
	}
}