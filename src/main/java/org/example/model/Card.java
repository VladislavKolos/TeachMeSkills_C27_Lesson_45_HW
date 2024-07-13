package org.example.model;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private int id;

    @Size(min = 16, max = 16)
    private String cardNumber;

    private Account account;

    private Client client;
}