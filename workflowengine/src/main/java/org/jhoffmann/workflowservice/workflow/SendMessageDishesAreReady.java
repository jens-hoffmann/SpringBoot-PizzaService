package org.jhoffmann.workflowservice.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.jhoffmann.workflowservice.domain.OrderItemPOJO;
import org.jhoffmann.workflowservice.services.JmsDispatcherService;

import javax.inject.Named;
import java.util.Map;
import java.util.Set;


@Slf4j
@Named
public class SendMessageDishesAreReady implements JavaDelegate {
    private final JmsDispatcherService jmsDispatcherService;

    public SendMessageDishesAreReady(JmsDispatcherService jmsDispatcherService) {
        this.jmsDispatcherService = jmsDispatcherService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String businessKey = delegateExecution.getBusinessKey();

        try {
            Set<String> variableNames = delegateExecution.getVariableNames();
            log.info("SendMessageDishesAreReady: variableNames: "+ variableNames.toString());
            if (variableNames.contains("orderItem")) {
                JsonValue retrievedTypedObjectValue =  delegateExecution.getVariableTyped("orderItem");
                log.info("SendMessageDishesAreReady: retrievedTypedObjectValue: " + retrievedTypedObjectValue.toString()) ;
                jmsDispatcherService.sendOrderItemToWebApp(retrievedTypedObjectValue.getValueSerialized());
            }

        } catch (Exception ex) {
            log.error("SendMessageDishesAreReady: No Valid OrderItemPOJO for Businesskey " + businessKey, ex);
            throw new RuntimeException(ex);
        }
    }
}
