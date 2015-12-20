package test;

import static hcomb.eu.workflow.engine.FlowBuilder.from;
import static hcomb.eu.workflow.engine.FlowBuilder.on;
import hcomb.eu.workflow.engine.EasyFlow;
import hcomb.eu.workflow.engine.FlowBuilder;
import hcomb.eu.workflow.engine.StatefulContext;
import hcomb.eu.workflow.engine.Transition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestParser {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EasyFlow<StatefulContext> parseWorkflow(String file) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(new File(file),
				HashMap.class);

		String id = (String) map.get("id");
		String version = (String) map.get("version");

		System.out.println("parsing workflow " + id + " version " + version);

		List<Map> states = (List) map.get("states");
		
		FlowBuilder<StatefulContext> start = getStartState(states);
		
		Transition[] startTransitions = getStartStateTransitions(states);
		
		EasyFlow<StatefulContext> flow = start.transit(startTransitions);

		return flow;
	}


	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static Transition[] getStartStateTransitions(List<Map> states) {
		Set<String> navigated = new HashSet<String>();

		//System.out.println("== getStartStateTransitions");
		List<String> trNames = new ArrayList<String>();
		List<Transition> ret = new ArrayList<Transition>();
		for (Map state : states) {
			String type = (String) state.get("type");
			if ("start-state".equals(type)) {
				List<Map> transitions = (List) state.get("transitions");
				if (transitions != null)// end state has no transitions
					for (Map transition : transitions) {
						String event = (String) transition.get("event");
						String to = (String) transition.get("to");
						Transition tr = on(event).to(to);
						String checkSO = event + " - " + to; 
						if(!navigated.contains(checkSO)){
							navigated.add(checkSO);
							ret.add(tr);
							System.out.println("######### on("+event+").to("+to+")");
							Transition[] nextLevel = getTransitions(states, to, navigated);
							System.out.println("\t.transit("+printTransitions(nextLevel)+")");
							tr.transit(nextLevel);
						}
					}
			}
		}
		
	//	System.out.println("== getStartStateTransitions -->" + ret);
		return ret.toArray(new Transition[]{});
	}


	public static String printTransitions(Transition[] nextLevel) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < nextLevel.length; i++) {
			ret.append(nextLevel[i]+" ");
		}
		return ret.toString();
	}


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static Transition[] getTransitions(List<Map> states, String toSt, Set<String> navigated) {
	//	System.out.println("== getTransitions(states,"+toSt+")");
		List<String> trNames = new ArrayList<String>();
		List<Transition> ret = new ArrayList<Transition>();
		for (Map state : states) {
			String id = (String) state.get("id");
			if (toSt.equals(id)) {
				List<Map> transitions = (List) state.get("transitions");
				if (transitions != null)// end state has no transitions
					for (Map transition : transitions) {
						String event = (String) transition.get("event");
						String to = (String) transition.get("to");
						//System.out.println("=====TO: "+to);
						//Events evt = Events.valueOf(event);
						//States st = States.valueOf(to);
						boolean isEndState = checkIsEndState(states, to);
						//System.out.println("isEndState: " + isEndState);
						if(isEndState){
							System.out.println("######### on("+event+").finish("+to+")");
							Transition tr = on(event).finish(to);
							ret.add(tr);
						} else {
							System.out.println("######### on("+event+").to("+to+")");
							String checkSO = event + " - " + to; 
							if(!navigated.contains(checkSO)){
								navigated.add(checkSO);
								Transition tr = on(event).to(to);
								ret.add(tr);
								Transition[] nextLevel = getTransitions(states, to, navigated);
								if(nextLevel.length>0){
									System.out.println("\t.transit("+printTransitions(nextLevel)+")");
									tr.transit(nextLevel);
									
								}
							}
						}
						
					}
			}
		}

//		System.out.println("== getTransitions(states,"+toSt+") -> " + ret);
		return ret.toArray(new Transition[]{});
	}


	@SuppressWarnings("rawtypes")
	public static boolean checkIsEndState(List<Map> states, String stateName) {
//		System.out.println("== checkIsEndState(states,"+stateName+")");
		for (Map state : states) {
			String type = (String) state.get("type");
			String id = (String) state.get("id");
			if ("end-state".equals(type) && stateName.equals(id))
				return true;
		}
		return false;
	}


	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static FlowBuilder<StatefulContext> getStartState(List<Map> states) {
//		System.out.println("== getStartState(states)");
		for (Map state : states) {
			String type = (String) state.get("type");
			if ("start-state".equals(type)) {

				String stateId = (String) state.get("id");
				String onEnter = (String) state.get("onEnter");
				String onLeave = (String) state.get("onLeave");
				//System.out.println("state: " + stateId + ", type: " + type + ", onEnter:" + onEnter + ", onLeave:" + onLeave);

				List<Map> transitions = (List) state.get("transitions");
				if (transitions != null)// end state has no transitions
					for (Map transition : transitions) {
						String event = (String) transition.get("event");
						String to = (String) transition.get("to");
					//	System.out.println("\ttransition: " + event + ", to: " + to);
					}
				
				//FlowBuilder<StatefulContext> start = from(States.NEW_POLICY_SIGNED);
				FlowBuilder<StatefulContext> start = from(stateId);
				return start;
			}
		}
		throw new RuntimeException("cannot find start-state");
	}
}
