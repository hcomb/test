package test;

import hcomb.eu.workflow.engine.EasyFlow;
import hcomb.eu.workflow.engine.StatefulContext;
import hcomb.eu.workflow.engine.call.ContextHandler;
import hcomb.eu.workflow.engine.call.StateHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestJsonMarshaller03 {
	
	
	public static void main(String[] args) throws Exception {
		EasyFlow<StatefulContext> flow = TestParser.parseWorkflow("test03.json");
		
		ContextHandler<StatefulContext> enterCtx = new ContextHandler<StatefulContext>() {
			public void call(StatefulContext context) throws Exception {
				System.out.println(" * entering "+context.getState());
			}
		};
		ContextHandler<StatefulContext> leaveCtx = new ContextHandler<StatefulContext>() {
			public void call(StatefulContext context) throws Exception {
				System.out.println(" * leaving "+context.getState());
			}
		};

		final ExecutorService service = Executors.newSingleThreadExecutor();
		flow.executor(service);

		flow.whenEnter("NEW_POLICY_SIGNED", enterCtx);
		flow.whenLeave("NEW_POLICY_SIGNED", leaveCtx);

		flow.whenEnter("CREATE_PDF_DOCUMENT", enterCtx);
		flow.whenLeave("CREATE_PDF_DOCUMENT", leaveCtx);

		flow.whenEnter("SAVE_DOCUMENT", enterCtx);
		flow.whenLeave("SAVE_DOCUMENT", leaveCtx);
		
		flow.whenEnter("SEND_EMAIL", enterCtx);
		flow.whenLeave("SEND_EMAIL", leaveCtx);
		
		flow.whenEnter("END", enterCtx);
		flow.whenLeave("END", leaveCtx);

		flow.whenFinalState(new StateHandler<StatefulContext>() {
            public void call(String stateEnum, StatefulContext statefulContext) throws Exception {
                System.out.println("finished.");
                service.shutdown();            
            }
        });

		StatefulContext ctx = new StatefulContext();
        //ctx.setState(States.SAVE_DOCUMENT);
		flow.trace().start(ctx);
        
		
        Thread.sleep(1000);
        ctx.trigger("onPolicySigned");
        
        Thread.sleep(1000);
        ctx.trigger("onDocumentCreated");

        Thread.sleep(1000);
        ctx.trigger("testBack");

        Thread.sleep(1000);
        ctx.trigger("onDocumentCreated");

        Thread.sleep(1000);
        ctx.trigger("onDocumentSaved");

        Thread.sleep(1000);
        ctx.trigger("onEmailSent");
        
        Thread.sleep(1000);
        System.out.println("terminated? " + ctx.isTerminated());
	}
	

}
