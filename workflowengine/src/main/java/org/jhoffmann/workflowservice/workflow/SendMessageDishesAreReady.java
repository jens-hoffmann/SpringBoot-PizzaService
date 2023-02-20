package org.jhoffmann.workflowservice.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jhoffmann.workflowservice.services.JmsDispatcherService;

import javax.inject.Named;


@Named
public class SendMessageDishesAreReady implements JavaDelegate {
    private final JmsDispatcherService jmsDispatcherService;

    public SendMessageDishesAreReady(JmsDispatcherService jmsDispatcherService) {
        this.jmsDispatcherService = jmsDispatcherService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String businessKey = delegateExecution.getBusinessKey();
        jmsDispatcherService.sendMessage("SendMessageDishesAreReady: " + businessKey);

    }
}
