package test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonMarshaller {

	public static void main(String[] args) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = mapper.readValue(new File("test02.json"), HashMap.class);
		
		String id = (String)map.get("id");
		String version = (String)map.get("version");
		
		System.out.println("parsing workflow "+ id + " version " + version);
		
		List<Map> states = (List)map.get("states");
		for (Map state : states) {
			String type = (String)state.get("type");
			String stateId = (String)state.get("id");
			String onEnter = (String)state.get("onEnter");
			String onLeave = (String)state.get("onLeave");
			System.out.println("state: "+stateId+", type: "+ type+", onEnter:"+onEnter+", onLeave:"+onLeave);
			
			List<Map> transitions = (List)state.get("transitions");
			if(transitions!=null)//end state has no transitions
				for (Map transition : transitions) {
					String event = (String)transition.get("event");
					String to = (String)transition.get("to");
					System.out.println("\ttransition: "+event+", to: "+to);
				}
		}
	}
	
}
