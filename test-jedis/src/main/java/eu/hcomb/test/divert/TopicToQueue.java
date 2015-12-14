package eu.hcomb.test.divert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class TopicToQueue extends JedisPubSub {

	private Jedis jedis;
	private String destination;
	
	public TopicToQueue(Jedis jedis, String destination) {
		super();
		this.jedis = jedis;
		this.destination = destination;
	}

	@Override
	public void onMessage(String channel, String message) {
			System.out.println("forwarding message:" + message);
			jedis.rpush(destination, message);
	}

	public void onPMessage(String pattern, String channel, String message) {}
	public void onSubscribe(String channel, int subscribedChannels) {}
	public void onUnsubscribe(String channel, int subscribedChannels) {}
	public void onPUnsubscribe(String pattern, int subscribedChannels) {}
	public void onPSubscribe(String pattern, int subscribedChannels) {}

	
}
