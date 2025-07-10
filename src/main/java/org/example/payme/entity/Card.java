package org.example.payme.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    @Column(nullable = false)
    private String cardNumber;

    @Pattern(regexp = "\\d{2}/\\d{2}", message = "Expiry date must be in MM/YY")
    private String expiryDate;

    @NotBlank
    @Size(min = 4, max = 6)
    private String password;

    private Double balance;

    @NotBlank
    private String cardHolder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
