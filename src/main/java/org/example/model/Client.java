package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private int id;

    @Size(min = 2, max = 32)
    private String name;

    @Email
    @Size(min = 5, max = 256)
    private String email;

    private List<Account> accounts;

    private List<Card> cards;
}