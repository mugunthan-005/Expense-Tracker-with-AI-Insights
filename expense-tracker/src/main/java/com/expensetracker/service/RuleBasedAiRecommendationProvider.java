package com.expensetracker.service;

import com.expensetracker.dto.MonthlyReportResponse;
import com.expensetracker.entity.ExpenseCategory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class RuleBasedAiRecommendationProvider implements AiRecommendationProvider {

    @Override
    public List<String> generateRecommendations(MonthlyReportResponse report, List<String> budgetSignals) {
        List<String> recommendations = new ArrayList<>();

        budgetSignals.forEach(signal -> recommendations.add("Budget alert: " + signal));

        report.categoryBreakdown().entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue()))
                .ifPresent(top -> recommendations.add(
                        "Top spending category is " + top.getKey() + " at " + top.getValue() + ". Consider a 10% reduction next month."
                ));

        BigDecimal entertainmentSpend = report.categoryBreakdown().getOrDefault(ExpenseCategory.ENTERTAINMENT, BigDecimal.ZERO);
        if (entertainmentSpend.compareTo(BigDecimal.valueOf(300)) > 0) {
            recommendations.add("Entertainment spend is high. Try setting a weekly cap to stay within goals.");
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Spending looks balanced. Keep tracking daily expenses to maintain control.");
        }

        return recommendations;
    }
}
