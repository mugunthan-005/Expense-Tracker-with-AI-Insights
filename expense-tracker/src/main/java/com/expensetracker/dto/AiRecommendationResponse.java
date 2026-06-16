package com.expensetracker.dto;

import java.util.List;

public record AiRecommendationResponse(
        String month,
        List<String> recommendations
) {
}
