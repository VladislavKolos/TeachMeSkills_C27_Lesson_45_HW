package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Card;
import org.example.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller for working with cards.
 */
@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardByID(@PathVariable int id) throws SQLException {
        Card card = cardService.getCardByID(id);

        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Card>> getCardsByAccountID(@PathVariable int accountId) throws SQLException {
        List<Card> cards = cardService.getCardsByAccountID(accountId);

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
