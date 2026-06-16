package com.expensetracker.dto;

import com.expensetracker.entity.ExpenseCategory;

import java.math.BigDecimal;
import java.util.Map;

public record MonthlyReportResponse(
        String month,
        BigDecimal totalSpent,
        long transactionCount,
        Map<ExpenseCategory, BigDecimal> categoryBreakdown
) {
}
