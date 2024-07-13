package org.example.dto;

import jakarta.validation.constraints.Size;
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

    @Size(min = 16, max = 16)
    private String fromCardNumber;

    @Size(min = 16, max = 16)
    private String toCardNumber;

    private BigDecimal amount;
}
