/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.jhoffmann.pizzaservice.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @NotNull
    private String businesskey;

    @NotNull
    @Size(min=3 , message="Name must be least 3 characters long")
    private String name;
    
    @NotNull
    private float price ;


    public Dish() {
        businesskey = UUID.randomUUID().toString();
    }

    public Dish(String name, float price) {
        this();
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", businesskey='" + businesskey + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
