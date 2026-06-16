package com.expensetracker.dto;

import com.expensetracker.entity.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate expenseDate,
        ExpenseCategory category
) {
}
