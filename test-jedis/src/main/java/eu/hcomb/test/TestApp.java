package eu.hcomb.test;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;

import com.google.inject.Binder;
import com.google.inject.Guice;

import eu.hcomb.common.redis.JedisModule;
import eu.hcomb.common.service.RedisService;
import eu.hcomb.common.web.BaseApp;

@SuppressWarnings("deprecation")
public class TestApp extends BaseApp<TestConfig> {

	public static void main(String[] args) throws Exception {
		new TestApp().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<TestConfig> bootstrap) {
		
	}
	
	public void configure(Binder binder) {
				
	}
	
	@Override
	public void run(TestConfig configuration, Environment environment) throws Exception {

		injector = Guice.createInjector(this, new JedisModule(configuration, environment));
		
		environment.jersey().register(injector.getInstance(HelloWorldResource.class));
				
		final TestSubscriber sub = new TestSubscriber();
		final RedisService redis = injector.getInstance(RedisService.class);
		final JedisPool pool = injector.getInstance(JedisPool.class);
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	                log.info("Subscription starting.");
	                redis.setupSubscription(pool, sub, "test");
	                log.info("Subscription ended.");
	            } catch (Exception e) {
	            	log.error("Subscribing failed.", e);
	            }
	        }
	    }).start();
		
	}


}
