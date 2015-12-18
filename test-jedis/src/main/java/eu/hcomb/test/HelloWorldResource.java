package eu.hcomb.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import redis.clients.jedis.JedisPool;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import eu.hcomb.common.service.RedisService;


@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

	@Inject
	protected RedisService redisService;

	@Inject
	protected JedisPool pool;
	
	@GET
	@Timed
	public String sayHello(){
		
		redisService.publish(pool, "test", "hello!");
		
		System.out.println("active: " + pool.getNumActive());
		
		return "CIAO ";
	}
	
}
