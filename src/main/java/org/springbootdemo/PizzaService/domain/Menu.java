/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.springbootdemo.PizzaService.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 *
 * @author hoffi
 */
@Data
public class Menu {
    
    private List<Dish> dishList = new ArrayList<>();
    
    private Menu() {   
    }

    public Menu(List<Dish> dishList) {
        this.dishList = dishList;
    }

    
    
}
