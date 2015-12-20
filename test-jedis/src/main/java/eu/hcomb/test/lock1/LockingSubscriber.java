package eu.hcomb.test.lock1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.hcomb.test.JedisLock;

public class LockingSubscriber extends JedisPubSub {
	
	Jedis jedis;
	ObjectMapper mapper = new ObjectMapper();
	
	public LockingSubscriber(Jedis jedis) {
		this.jedis = jedis;
	}
	
	@Override
	public void onMessage(String channel, String message) {
		try {

			LockableObject obj = mapper.readValue(message, LockableObject.class);
			System.out.println("received message, id: "+obj.getId()+", data: " + obj.getData());
			
			JedisLock lock = new JedisLock(jedis, obj.getId(), 10000, 30000);
			
			System.out.println("* acquiring lock " + lock.getLockKeyPath() + " - " + lock.getLockUUID() + "  - "+System.currentTimeMillis());
			boolean check = lock.acquire();
			System.out.println("* acquired lock " + lock.getLockKeyPath() + " - " + lock.getLockUUID() + "  - "+System.currentTimeMillis());
			try {
				Thread.sleep(3000);
				System.out.println("check: "+ check);
				System.out.println("processing message: " + obj.getData());
			} finally {
				System.out.println("* releasing lock " + lock.getLockKeyPath() + " - " + lock.getLockUUID() + "  - "+System.currentTimeMillis());
			  lock.release();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	
}
