package test;

import static hcomb.eu.workflow.engine.FlowBuilder.from;
import static hcomb.eu.workflow.engine.FlowBuilder.on;
import hcomb.eu.workflow.engine.EasyFlow;
import hcomb.eu.workflow.engine.FlowBuilder;
import hcomb.eu.workflow.engine.StatefulContext;
import hcomb.eu.workflow.engine.Transition;
import hcomb.eu.workflow.engine.call.ContextHandler;
import hcomb.eu.workflow.engine.call.StateHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test02 {
	//test02.png
	
	public void run() throws Exception {
		
		Transition state_end = on("Events.onEmailSent").finish("States.END");

		Transition tr_sendMail = on("Events.onDocumentSaved").to("States.SEND_EMAIL");
		Transition state_sendMail = tr_sendMail.transit(state_end);
		
		Transition tr_saveDoc = on("Events.onDocumentCreated").to("States.SAVE_DOCUMENT");
		Transition state_saveDoc = tr_saveDoc.transit(state_sendMail);
		
		Transition tr_createPdf = on("Events.onPolicySigned").to("States.CREATE_PDF_DOCUMENT");
		Transition state_createPdf = tr_createPdf.transit(state_saveDoc);
		
		FlowBuilder<StatefulContext> start = from("States.NEW_POLICY_SIGNED");
		
		final EasyFlow<StatefulContext> flow = start.transit(state_createPdf);
	
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

		flow.whenEnter("States.NEW_POLICY_SIGNED", enterCtx);
		flow.whenLeave("States.NEW_POLICY_SIGNED", leaveCtx);

		flow.whenEnter("States.CREATE_PDF_DOCUMENT", enterCtx);
		flow.whenLeave("States.CREATE_PDF_DOCUMENT", leaveCtx);

		flow.whenEnter("States.SAVE_DOCUMENT", enterCtx);
		flow.whenLeave("States.SAVE_DOCUMENT", leaveCtx);
		
		flow.whenEnter("States.SEND_EMAIL", enterCtx);
		flow.whenLeave("States.SEND_EMAIL", leaveCtx);
		
		flow.whenEnter("States.END", enterCtx);
		flow.whenLeave("States.END", leaveCtx);

		flow.whenFinalState(new StateHandler<StatefulContext>() {
            public void call(String stateEnum, StatefulContext statefulContext) throws Exception {
                System.out.println("finished.");
                service.shutdown();            
            }
        });
		
		StatefulContext ctx = new StatefulContext();

		
		flow.trace().start(ctx);
        
		
        Thread.sleep(1000);
        ctx.trigger("Events.onPolicySigned");
        
        Thread.sleep(1000);
        ctx.trigger("Events.onDocumentCreated");
        
        Thread.sleep(1000);
        ctx.trigger("Events.onDocumentSaved");

        Thread.sleep(1000);
        ctx.trigger("Events.onEmailSent");
        
        Thread.sleep(1000);
        System.out.println("terminated? " + ctx.isTerminated());
	}
	
	public static void main(String[] args) throws Exception {
		
		new Test02().run();
		
	}
}
