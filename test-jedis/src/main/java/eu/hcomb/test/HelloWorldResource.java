package eu.hcomb.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import eu.hcomb.common.service.RedisService;


@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

	@Inject
	RedisService redisService;
	
	@GET
	@Timed
	public String sayHello(){
		
		redisService.publish("test", "hello!");
		
		System.out.println("active: " + redisService.getNumActive());
		
		return "CIAO ";
	}
	
}
