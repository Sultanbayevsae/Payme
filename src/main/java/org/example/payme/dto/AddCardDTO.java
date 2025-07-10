package org.example.payme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCardDTO {
    private String CardNumber;
    private String expiryDate;
    private String password;
    private String cardHolder;
    private Long userId;
}
