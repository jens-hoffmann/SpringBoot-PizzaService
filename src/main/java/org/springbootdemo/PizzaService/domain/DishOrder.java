package org.springbootdemo.PizzaService.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springbootdemo.PizzaService.controller.ShoppingCart;

@Data
public class DishOrder {
    //private List<OrderItem> dishesOrder;
    private ShoppingCart shoppingCart;
    
    private String totalPrice;
    
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
    
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
                message="Must be formatted MM/YY")
    private String ccExpiration;
    
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;    
    
    public DishOrder() {
        this.sessionId = UUID.randomUUID().toString();
        //this.dishesOrder = new ArrayList<>();
    }
    
    public void getTotalPriceOfShoppingCart() {
        
        /*float price = 0.0F;
        for ( OrderItem item : shoppingCart.getContent()) {
            //item.getDish()
            
        }*/
        
    }
}
