package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransferDTO;
import org.example.service.AmountTransferService;
import org.example.validator.TransferDTOValidator;
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

    private final TransferDTOValidator transferDTOValidator;

    @PostMapping
    public ResponseEntity<String> transferAmount(@RequestBody TransferDTO transferDTO) throws SQLException {
        if (!transferDTOValidator.isCardExistsByCardNumber(transferDTO.getFromCardNumber())) {
            throw new RuntimeException("Sender's card number: " + transferDTO.getFromCardNumber() + " not found");
        } else if (!transferDTOValidator.isCardExistsByCardNumber(transferDTO.getToCardNumber())) {
            throw new RuntimeException("Recipient's card number: " + transferDTO.getToCardNumber() + " not found");
        }

        BigDecimal remainingBalance = amountTransferService.transferAmount(transferDTO.getFromCardNumber(),
                transferDTO.getToCardNumber(),
                transferDTO.getAmount());
        return new ResponseEntity<>("Transfer successful. Remaining balance: " + remainingBalance, HttpStatus.OK);
    }
}
