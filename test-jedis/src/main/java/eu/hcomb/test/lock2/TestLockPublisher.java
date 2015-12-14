package eu.hcomb.test.lock2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.hcomb.common.redis.JedisConfig;

public class TestLockPublisher {

	public static final String TOPIC_NAME = "lockabe.test.1";
	
	public static void main(String[] args) throws Exception {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		Jedis jedis = pool.getResource();
		
		ObjectMapper mapper = new ObjectMapper();
		
		for (int i = 0; i < 100; i++) {
			Thread.sleep(1000);
			jedis.publish(TOPIC_NAME, "hello!");
		}
		
		jedis.close();
		
	}

}
