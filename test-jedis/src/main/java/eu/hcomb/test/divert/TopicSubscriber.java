package eu.hcomb.test.divert;

import redis.clients.jedis.JedisPubSub;

public class TopicSubscriber extends JedisPubSub {
		
	@Override
	public void onMessage(String channel, String message) {
			System.out.println("received message:" + message);
	}

	public void onPMessage(String pattern, String channel, String message) {}
	public void onSubscribe(String channel, int subscribedChannels) {}
	public void onUnsubscribe(String channel, int subscribedChannels) {}
	public void onPUnsubscribe(String pattern, int subscribedChannels) {}
	public void onPSubscribe(String pattern, int subscribedChannels) {}

	
}
