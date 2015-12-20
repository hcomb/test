package eu.hcomb.test.reliable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;

public class GetFromQueue {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();

        while(true){
          String payload = jedis.brpoplpush("queue.q2", "processing.queue.q2", 1000);
          System.out.println("Message received:" + payload);
          jedis.lrem("processing.queue.q2", 1, payload);
        }

	}
}
