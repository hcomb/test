package eu.hcomb.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisPubSub;

public class TestSubscriber extends JedisPubSub {

	protected Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void onMessage(String channel, String message) {
		log.info("Message received. Channel: "+channel+", Msg: "+ message);
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
