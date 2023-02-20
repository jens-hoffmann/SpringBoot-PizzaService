package org.jhoffmann.workflowservice.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemPOJO {
    private String businesskey;
    private String dishkey;
    private String dishname;
    private int amount;
}
