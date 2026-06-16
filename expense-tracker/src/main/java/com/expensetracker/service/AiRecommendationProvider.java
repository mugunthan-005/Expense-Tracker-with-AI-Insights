package com.expensetracker.service;

import com.expensetracker.dto.MonthlyReportResponse;

import java.util.List;

public interface AiRecommendationProvider {
    List<String> generateRecommendations(MonthlyReportResponse report, List<String> budgetSignals);
}
