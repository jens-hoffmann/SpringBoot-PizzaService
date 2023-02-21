package org.jhoffmann.pizzaservice.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orderitem")
public class OrderItem {

    enum DishStatus { NEW, COOKED, DELIVERED }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @NotNull
    private String businesskey;

    @ManyToOne
    @JoinColumn( name = "dishorder_fk")
    private DishOrder order;

    @NotNull
    private String dishkey;

    @NotNull
    private String dishname;


    @NotNull
    @Min(value = 1, message = "No negative number of dishes allowed")
    @Max(value = 99, message = "Number of dishes exceed max allowed 99" )
    private int amount;

    @NotNull
    private float price;

    @Enumerated(EnumType.STRING)
    DishStatus dishStatus;

    public OrderItem() {
        businesskey = UUID.randomUUID().toString();
        this.dishStatus = DishStatus.NEW;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", businesskey='" + businesskey + '\'' +
                ", dishkey='" + dishkey + '\'' +
                ", dishname='" + dishname + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", dishStatus=" + dishStatus +
                '}';
    }
}
