package eu.hcomb.test.divert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;

public class RunTopicToQueue {

	public static void main(String[] args) throws Exception {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();

		final TopicToQueue sub = new TopicToQueue(pool.getResource(), "test.queue");
		System.out.println("* subscribing - " + RunTopicToQueue.class);
		jedis.subscribe(sub, "test.topic");

		pool.close();
	}
}
