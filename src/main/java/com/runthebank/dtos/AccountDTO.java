package com.runthebank.dtos;

import com.runthebank.domain.Customer;
import com.runthebank.domain.Enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO(


        String agency,

        UUID customerID,

        BigDecimal balance
) {
}
