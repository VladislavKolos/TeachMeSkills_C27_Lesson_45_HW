package org.example.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int id;

    @Size(min = 28, max = 28)
    private String accountNumber;

    private BigDecimal balance;

    private Client client;

    private List<Card> cards;
}