package eu.hcomb.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import redis.clients.jedis.JedisPool;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;


@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

	@Inject
	JedisPool jedisPool;
	
	@GET
	@Timed
	public String sayHello(){
		
		jedisPool.getResource().publish("test", "hello!");
		
		return "CIAO ";
	}
	
}
