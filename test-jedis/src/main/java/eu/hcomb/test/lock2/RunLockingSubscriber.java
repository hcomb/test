package eu.hcomb.test.lock2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import eu.hcomb.common.redis.JedisConfig;
import eu.hcomb.test.JedisLock;

public class RunLockingSubscriber {

	public static void main(String[] args) throws Exception {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(poolConfig, JedisConfig.DEFAULT_HOST, JedisConfig.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null);
		final Jedis jedis = pool.getResource();
		
		boolean locked = false;
		Jedis lockJedis = pool.getResource();
		
		final JedisLock lock = new JedisLock(lockJedis, TestLockPublisher.TOPIC_NAME, 10000, 30000);

		while(!locked){
			
			System.out.print(" * trying to acquire lock... ");
			locked = lock.acquire();
			System.out.println(locked);
			if(locked)
				System.out.println(" * lock acquired");
		}
		
		new Thread(new Runnable(){
			public void run() {
				try{
					while(true){
						System.out.println(" * renewing..." + lock.renew());
						Thread.sleep(5000);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();;
		
		final LockingSubscriber2 sub = new LockingSubscriber2();
		System.out.println("* subscribing");
		jedis.subscribe(sub, TestLockPublisher.TOPIC_NAME);

		pool.close();
		
	}
}
