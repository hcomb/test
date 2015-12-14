package eu.hcomb.test.lock1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;

public class RunLockingSubscriber {

	public static void main(String[] args) {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();
		Jedis lockJedis = pool.getResource();
		final LockingSubscriber sub = new LockingSubscriber(lockJedis);
		jedis.subscribe(sub, TestLockPublisher.TOPIC_NAME);
		
	}
}
