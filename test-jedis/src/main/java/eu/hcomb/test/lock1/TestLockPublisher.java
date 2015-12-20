package eu.hcomb.test.lock1;

import java.util.UUID;

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
		
		for (int i = 0; i < 3; i++) {
			LockableObject obj = getLockable("la vispa teresa "+i);
			String msg = mapper.writeValueAsString(obj);
			Thread.sleep(100);
			jedis.publish(TOPIC_NAME, msg);
		}
		
		jedis.close();
		pool.close();
	}
	
	
	public static LockableObject getLockable(String data){
		LockableObject obj = new LockableObject();
		obj.setData(data);
		obj.setId(UUID.randomUUID().toString());
		return obj;
	}
}
