package org.springbootdemo.PizzaService.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@RequestScope
public class Payment {

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
                message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    public Payment() {
    }
}
