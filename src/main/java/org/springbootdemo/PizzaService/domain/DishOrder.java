package org.springbootdemo.PizzaService.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import java.util.UUID;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;



@Getter
@Setter
@Slf4j
public class DishOrder {
    private List<OrderItem> dishesOrder;
    private Float totalPrice;
    
    private String ipAdress;
    private String sessionId;

    @NotBlank(message="Delivery name is required")
    private String deliveryName;
    
    @NotBlank(message="Street is required")
    private String deliveryStreet;
    
    @Digits(integer=5, fraction=0, message="Invalid Uip Code")
    private String deliveryZipCode;
    
    @NotBlank(message="City is required")
    private String deliveryCity;

    public DishOrder() {
        this.sessionId = UUID.randomUUID().toString();
        log.info(" Create new DishOrder: " + this.sessionId);

        //this.dishesOrder = new ArrayList<>();
    }

}
