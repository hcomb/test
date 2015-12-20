package eu.hcomb.test;

import eu.hcomb.authn.LoginEvents;
import eu.hcomb.authz.UserEvents;
import eu.hcomb.common.redis.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RunSubscriber {

	public static void main(String[] args) {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final TestSubscriber sub = new TestSubscriber();
		final Jedis jedis = pool.getResource();
		jedis.subscribe(sub,
					LoginEvents.FAILED_LOGIN,
					LoginEvents.SUCCESS_LOGIN,
					UserEvents.CREATE,
					UserEvents.DELETE,
					UserEvents.LIST,
					UserEvents.READ,
					UserEvents.UPDATE,
					UserEvents.USER_LOGIN
				);
		
		pool.close();
		
	}
}
