package eu.hcomb.test.divert;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;

public class GetFromQueue {

	public static void main(String[] args) {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();

		List<String> messages = null;
        while(true){
          messages = jedis.blpop(0,"test.queue");
          String payload = messages.get(1);
          System.out.println("Message received:" + payload);
        }

	}
}
