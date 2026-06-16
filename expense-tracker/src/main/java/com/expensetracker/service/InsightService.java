package com.expensetracker.service;

import com.expensetracker.dto.AiRecommendationResponse;
import com.expensetracker.dto.MonthlyReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsightService {

    private final ReportService reportService;
    private final BudgetService budgetService;
    private final AiRecommendationProvider recommendationProvider;

    public InsightService(ReportService reportService,
                          BudgetService budgetService,
                          AiRecommendationProvider recommendationProvider) {
        this.reportService = reportService;
        this.budgetService = budgetService;
        this.recommendationProvider = recommendationProvider;
    }

    public AiRecommendationResponse getRecommendations(String month) {
        MonthlyReportResponse report = reportService.getMonthlyReport(month);

        List<String> budgetSignals = budgetService.getAlerts(month).stream()
                .filter(alert -> alert.thresholdReached() || alert.exceeded())
                .map(alert -> (alert.category() == null ? "Overall" : alert.category().name())
                        + " spending is " + alert.spentPercentage() + "% of budget")
                .toList();

        List<String> recommendations = recommendationProvider.generateRecommendations(report, budgetSignals);
        return new AiRecommendationResponse(month, recommendations);
    }
}
