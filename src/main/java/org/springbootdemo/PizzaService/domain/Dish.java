/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.domain;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class Dish {

    @NotNull
    @Size(min=3 , message="Name must be least 3 characters long")
    private String name;
    
    @NotNull
    private float price ;
        

    public Dish(String name, float price) {
        this.name = name;
        this.price = price;
    }
}
