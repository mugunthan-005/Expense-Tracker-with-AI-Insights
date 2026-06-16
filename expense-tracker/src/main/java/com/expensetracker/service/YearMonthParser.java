package com.expensetracker.service;

import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public final class YearMonthParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private YearMonthParser() {
    }

    public static YearMonth parse(String month) {
        try {
            return YearMonth.parse(month, FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(BAD_REQUEST, "Month must be in yyyy-MM format");
        }
    }
}
