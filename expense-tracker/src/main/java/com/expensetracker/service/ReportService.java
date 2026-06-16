package com.expensetracker.service;

import com.expensetracker.dto.MonthlyReportResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.ExpenseCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ExpenseService expenseService;

    public ReportService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public MonthlyReportResponse getMonthlyReport(String month) {
        List<Expense> expenses = expenseService.getExpensesByMonth(month);
        Map<ExpenseCategory, BigDecimal> categoryBreakdown = new EnumMap<>(ExpenseCategory.class);
        BigDecimal total = BigDecimal.ZERO;

        for (Expense expense : expenses) {
            total = total.add(expense.getAmount());
            categoryBreakdown.merge(expense.getCategory(), expense.getAmount(), BigDecimal::add);
        }

        return new MonthlyReportResponse(month, total, expenses.size(), categoryBreakdown);
    }
}
