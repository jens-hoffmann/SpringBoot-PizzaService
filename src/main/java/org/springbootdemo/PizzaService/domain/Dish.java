/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.domain;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dish {

    @NotNull
    @Size(min=3 , message="Name must be least 3 characters long")
    private String name;
    
    @NotNull
    private float price ;


    public Dish() {
    }

    public Dish(String name, float price) {
        this.name = name;
        this.price = price;
    }
}
