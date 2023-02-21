package org.jhoffmann.workflowservice.workflow;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
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
        OrderItemPOJO newOrderItem = new OrderItemPOJO();
        String businessKey = delegateExecution.getBusinessKey();

        try {
            Object orderItemObj = delegateExecution.getVariable("orderItem");
            log.info("SendMessageDishesAreReady: processVariables: " + orderItemObj.toString()) ;
            OrderItemPOJO orderItem = (OrderItemPOJO) orderItemObj;
            String serializedString = new ObjectMapper().writeValueAsString(orderItem);
            jmsDispatcherService.sendOrderItemToWebApp(serializedString);
        } catch (ClassCastException cce) {
            log.error("SendMessageDishesAreReady: No Valid OrderItemPOJO for Businesskey " + businessKey);
        }
    }
}
