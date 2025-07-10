package org.example.payme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardVerifyDTO {
    private String cardNumber;
    private String password;
}
