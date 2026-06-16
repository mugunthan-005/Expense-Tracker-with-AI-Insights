package com.expensetracker.controller;

import com.expensetracker.dto.MonthlyReportResponse;
import com.expensetracker.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly")
    public MonthlyReportResponse monthly(@RequestParam int year, @RequestParam int month) {
        return reportService.getMonthlyReport("%d-%02d".formatted(year, month));
    }
}
