package org.jhoffmann.pizzaservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class OrderItemPOJO implements Serializable {

    @JsonCreator
    public OrderItemPOJO() {
    }

    @JsonCreator
    public OrderItemPOJO(@JsonProperty("businesskey") String businesskey,
                         @JsonProperty("dishkey") String dishkey,
                         @JsonProperty("dishname") String dishname,
                         @JsonProperty("orderId") String orderId,
                         @JsonProperty("amount") int amount) {
        this.businesskey = businesskey;
        this.dishkey = dishkey;
        this.dishname = dishname;
        this.orderId = orderId;
        this.amount = amount;
    }

    @JsonProperty("businesskey")
    private String businesskey;

    @JsonProperty("dishkey")
    private String dishkey;

    @JsonProperty("dishname")
    private String dishname;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("amount")
    private int amount;
}
