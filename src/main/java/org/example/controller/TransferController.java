package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransferDTO;
import org.example.service.AmountTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Controller for transferring funds between cards.
 */
@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final AmountTransferService amountTransferService;

    @PostMapping
    public ResponseEntity<String> transferAmount(@RequestBody TransferDTO transferDTO) throws SQLException {
        BigDecimal remainingBalance = amountTransferService.transferAmount(
                transferDTO.getFromCardNumber(),
                transferDTO.getToCardNumber(),
                transferDTO.getAmount()
        );

        if (remainingBalance != null) {
            return new ResponseEntity<>("Transfer successful. Remaining balance: "
                    + remainingBalance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Transfer failed",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
