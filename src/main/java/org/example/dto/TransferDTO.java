package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * "Data Transfer Object" (DTO) for transferring amounts between cards.
 *  This class encapsulates the details required for the translation operation.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    private long fromCardNumber;

    private long toCardNumber;

    private BigDecimal amount;
}
