package com.expensetracker.controller;

import com.expensetracker.dto.BudgetAlertResponse;
import com.expensetracker.dto.BudgetRequest;
import com.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdate(@Valid @RequestBody BudgetRequest request) {
        budgetService.upsertBudget(request);
    }

    @GetMapping("/alerts")
    public List<BudgetAlertResponse> alerts(@RequestParam int year, @RequestParam int month) {
        return budgetService.getAlerts("%d-%02d".formatted(year, month));
    }
}
