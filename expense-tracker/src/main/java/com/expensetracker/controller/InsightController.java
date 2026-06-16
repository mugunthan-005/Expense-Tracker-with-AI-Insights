package com.expensetracker.controller;

import com.expensetracker.dto.AiRecommendationResponse;
import com.expensetracker.service.InsightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insights")
public class InsightController {

    private final InsightService insightService;

    public InsightController(InsightService insightService) {
        this.insightService = insightService;
    }

    @GetMapping("/recommendations")
    public AiRecommendationResponse recommendations(@RequestParam int year, @RequestParam int month) {
        return insightService.getRecommendations("%d-%02d".formatted(year, month));
    }
}
