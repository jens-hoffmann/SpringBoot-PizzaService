/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author hoffi
 */
@Data
public class OrderItem {
    
    @NotNull
    private String dish;
    
    @NotNull
    @Min(value = 1, message = "No negative number of dishes allowed")
    @Max(value = 99, message = "Numbe of dishes exceed max allowed 99" )
    private int amount;
    
}
