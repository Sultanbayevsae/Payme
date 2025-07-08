package org.example.payme.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private Double amount;

    @NotNull
    private LocalDate dueDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Service service;

    private Boolean isPaid = false;
}
