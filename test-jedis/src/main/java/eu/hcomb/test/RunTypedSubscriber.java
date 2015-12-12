package eu.hcomb.test;

import eu.hcomb.common.redis.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RunTypedSubscriber {

	public static void main(String[] args) {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final TokenSubscriber sub = new TokenSubscriber();
		final Jedis jedis = pool.getResource();
		jedis.subscribe(sub, "test");

	}
}
