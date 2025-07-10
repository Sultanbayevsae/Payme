package org.example.payme.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;


    @ManyToOne
    @JoinColumn(name = "receiver_card_id")
    private Card receiverCard;

    @Positive
    private Double amount;

    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Card card;

    @NotBlank
    private String status;

}
