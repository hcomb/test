package eu.hcomb.test;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.inject.Binder;
import com.google.inject.Guice;

import eu.hcomb.common.jedis.JedisModule;
import eu.hcomb.common.web.BaseApp;

public class TestApp extends BaseApp<TestConfig> {

	public static void main(String[] args) throws Exception {
		new TestApp().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<TestConfig> bootstrap) {
		
	}
	
	public void configure(Binder binder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void run(TestConfig configuration, Environment environment) throws Exception {

		injector = Guice.createInjector(this, new JedisModule(configuration));
		
		environment.jersey().register(injector.getInstance(HelloWorldResource.class));
		
		JedisPool pool = injector.getInstance(JedisPool.class);
		final TestSubscriber sub = new TestSubscriber();
		final Jedis jedis = pool.getResource();
		
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	                log.info("Subscription starting.");
	        		jedis.subscribe(sub, "test");
	                log.info("Subscription ended.");
	            } catch (Exception e) {
	            	log.error("Subscribing failed.", e);
	            }
	        }
	    }).start();
		
	}


}
