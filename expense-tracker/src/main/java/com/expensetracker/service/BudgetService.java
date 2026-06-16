package com.expensetracker.service;

import com.expensetracker.dto.BudgetAlertResponse;
import com.expensetracker.dto.BudgetRequest;
import com.expensetracker.entity.Budget;
import com.expensetracker.entity.Expense;
import com.expensetracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseService expenseService;

    public BudgetService(BudgetRepository budgetRepository, ExpenseService expenseService) {
        this.budgetRepository = budgetRepository;
        this.expenseService = expenseService;
    }

    @Transactional
    public void upsertBudget(BudgetRequest request) {
        String normalizedMonth = YearMonthParser.parse(request.month()).toString();
        Budget budget = budgetRepository.findByMonthAndCategory(normalizedMonth, request.category())
                .orElseGet(Budget::new);

        budget.setMonth(normalizedMonth);
        budget.setCategory(request.category());
        budget.setAmount(request.amount());
        budget.setAlertThresholdPercent(request.alertThresholdPercent());

        budgetRepository.save(budget);
    }

    @Transactional(readOnly = true)
    public List<BudgetAlertResponse> getAlerts(String month) {
        String normalizedMonth = YearMonthParser.parse(month).toString();
        List<Budget> budgets = budgetRepository.findByMonth(normalizedMonth);
        List<Expense> expenses = expenseService.getExpensesByMonth(normalizedMonth);

        return budgets.stream()
                .map(budget -> buildAlertResponse(budget, expenses))
                .toList();
    }

    private BudgetAlertResponse buildAlertResponse(Budget budget, List<Expense> expenses) {
        BigDecimal spent = expenses.stream()
                .filter(expense -> budget.getCategory() == null || expense.getCategory() == budget.getCategory())
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal spentPercentage = spent
                .multiply(BigDecimal.valueOf(100))
                .divide(budget.getAmount(), 2, RoundingMode.HALF_UP);

        boolean thresholdReached = spentPercentage.compareTo(BigDecimal.valueOf(budget.getAlertThresholdPercent())) >= 0;
        boolean exceeded = spent.compareTo(budget.getAmount()) > 0;

        return new BudgetAlertResponse(
                budget.getId(),
                budget.getMonth(),
                budget.getCategory(),
                budget.getAmount(),
                spent,
                spentPercentage,
                thresholdReached,
                exceeded
        );
    }
}
