package eu.hcomb.test;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;

public class ListObjects {

	public static void main(String[] args) throws Exception {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();

		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println(key);
		}
		
		pool.close();

	}
}
