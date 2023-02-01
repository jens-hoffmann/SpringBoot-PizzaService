/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    
    @NotNull
    private String dish;
    
    @NotNull
    @Min(value = 1, message = "No negative number of dishes allowed")
    @Max(value = 99, message = "Number of dishes exceed max allowed 99" )
    private int amount;

    public OrderItem() {
    }
}
