package com.expensetracker.service;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.dto.ExpenseResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) {
        Expense expense = toEntity(request);
        return toResponse(expenseRepository.save(expense));
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> listExpenses(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = (startDate != null && endDate != null)
                ? expenseRepository.findByExpenseDateBetween(startDate, endDate)
                : expenseRepository.findAll();
        return expenses.stream().map(this::toResponse).toList();
    }

    @Transactional
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Expense not found"));
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setExpenseDate(request.expenseDate());
        expense.setCategory(request.category());
        return toResponse(expenseRepository.save(expense));
    }

    @Transactional
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Expense not found");
        }
        expenseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesByMonth(String month) {
        var yearMonth = YearMonthParser.parse(month);
        var start = yearMonth.atDay(1);
        var end = yearMonth.atEndOfMonth();
        return expenseRepository.findByExpenseDateBetween(start, end);
    }

    private Expense toEntity(ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setExpenseDate(request.expenseDate());
        expense.setCategory(request.category());
        return expense;
    }

    private ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory()
        );
    }
}
