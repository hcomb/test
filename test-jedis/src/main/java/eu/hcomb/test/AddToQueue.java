package eu.hcomb.test;

import eu.hcomb.common.redis.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class AddToQueue {

	public static void main(String[] args) throws Exception {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();

		for (int i = 0; i < 2000; i++) {
			Thread.sleep(100);
			jedis.rpush("TEST", "{\"id\":\""+i+"\",\"name\":\"pippo\"}");
		}

	}
}
