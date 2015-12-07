package eu.hcomb.test.web;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    private final AtomicLong counter;

    public HelloWorldResource() {
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public String sayHello(@QueryParam("name") Optional<String> name) {
    	if(name.isPresent())
    		return "hello " + name.get() + " ("+counter.incrementAndGet()+")";
    	else
    		return "hello stranger ("+counter.incrementAndGet()+")";
    		
    }
}