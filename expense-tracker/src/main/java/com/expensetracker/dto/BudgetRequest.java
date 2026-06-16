package com.expensetracker.dto;

import com.expensetracker.entity.ExpenseCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BudgetRequest(
        @NotBlank String month,
        ExpenseCategory category,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @NotNull @Min(1) @Max(100) Integer alertThresholdPercent
) {
}
