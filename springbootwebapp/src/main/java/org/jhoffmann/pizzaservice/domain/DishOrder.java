package org.jhoffmann.pizzaservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Slf4j
@Entity
public class DishOrder {



    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    private String businesskey;

    @OneToMany(                     // Logische Mapping-Annotation
            mappedBy = "order",       // Profile hat keine Mapping-Information, daher 'schau' bei Photo und profile nache
            fetch = FetchType.EAGER,    // Wenn Profile geladen wird, sollen die Fotos mitgeladen werden (Standard ist LAZY)
            cascade = CascadeType.ALL)
    private List<OrderItem> dishesOrder;

    private Float totalPrice;
    
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

    public DishOrder() {
        businesskey = UUID.randomUUID().toString();
        log.info(" Create new DishOrder: " + this.sessionId);
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "id=" + id +
                ", businesskey='" + businesskey + '\'' +
                ", totalPrice=" + totalPrice +
                ", ipAdress='" + ipAdress + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", deliveryName='" + deliveryName + '\'' +
                ", deliveryStreet='" + deliveryStreet + '\'' +
                ", deliveryZipCode='" + deliveryZipCode + '\'' +
                ", deliveryCity='" + deliveryCity + '\'' +
                '}';
    }
}
