package eu.hcomb.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisPubSub;

public class TestSubscriber extends JedisPubSub {

	protected Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void onMessage(String channel, String message) {
		log.info("onMessage: "+channel+","+ message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		log.info("onPMessage: "+pattern+","+channel+","+ message);
		
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		log.info("onSubscribe: "+channel+","+subscribedChannels);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		log.info("onUnsubscribe: "+channel+","+subscribedChannels);
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		log.info("onPUnsubscribe: "+pattern+","+subscribedChannels);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		log.info("onPSubscribe: "+pattern+","+subscribedChannels);
	}

	
}
