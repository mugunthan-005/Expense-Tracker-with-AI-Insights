package com.expensetracker.dto;

import com.expensetracker.entity.ExpenseCategory;

import java.math.BigDecimal;

public record BudgetAlertResponse(
        Long budgetId,
        String month,
        ExpenseCategory category,
        BigDecimal budgetAmount,
        BigDecimal spentAmount,
        BigDecimal spentPercentage,
        boolean thresholdReached,
        boolean exceeded
) {
}
