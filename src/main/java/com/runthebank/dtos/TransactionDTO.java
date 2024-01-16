package com.runthebank.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(

        UUID id,
        BigDecimal value,
        String senderDocument,

        String receiverDocument,

        String agency

) {
}
