package eu.hcomb.test;

import java.security.SecureRandom;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;

import eu.hcomb.authn.dto.TokenDTO;
import eu.hcomb.common.redis.JedisConfig;
import eu.hcomb.common.service.RedisService;
import eu.hcomb.common.service.impl.RedisServiceJedisImpl;

public class RunPublisher implements Module {
	
	RedisService redis;
	
	public void configure(Binder binder) {

		binder
			.bind(RedisService.class)
			.to(RedisServiceJedisImpl.class);
	}

	@Provides
	public JedisPool getPool(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		return pool;
	}
	
	public void setup() throws JsonProcessingException{
		Injector injector = Guice.createInjector(this);
		redis = injector.getInstance(RedisService.class);
	}
	
	public Long run() throws JsonProcessingException{
		TokenDTO test = new TokenDTO();
		test.setExpire(new SecureRandom().nextLong());
		test.setValid(true);
		test.setValue("aaaaaa");
		return redis.publish("test", test);
	}
	
	
	public static void main(String[] args) throws Exception {
		
		RunPublisher pub = new RunPublisher();
		pub.setup();
		System.out.println(pub.run());
		System.out.println(pub.run());
		System.out.println(pub.run());

	}
}

