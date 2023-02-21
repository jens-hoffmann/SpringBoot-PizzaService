package org.jhoffmann.workflowservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemPOJO implements Serializable {

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
