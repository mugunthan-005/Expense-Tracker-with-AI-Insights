# Expense Tracker with AI Insights

Java Spring Boot backend for tracking expenses, generating monthly reports, monitoring budgets, and providing AI-style spending recommendations.

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Web + Validation
- Spring Data JPA
- H2 (in-memory DB)

## Project Structure
- Backend code: `/home/runner/work/Expense-Tracker-with-AI-Insights/Expense-Tracker-with-AI-Insights/expense-tracker`
- Main package: `com.expensetracker`

## Features
- Expense CRUD (`/api/expenses`)
- Monthly report (`/api/reports/monthly`)
- Budget setup + alerts (`/api/budgets`, `/api/budgets/alerts`)
- AI recommendations (`/api/insights/recommendations`)

## Run Locally
```bash
cd /home/runner/work/Expense-Tracker-with-AI-Insights/Expense-Tracker-with-AI-Insights/expense-tracker
mvn spring-boot:run
```

## Test
```bash
cd /home/runner/work/Expense-Tracker-with-AI-Insights/Expense-Tracker-with-AI-Insights/expense-tracker
mvn test
```

## API Examples
### 1) Create Expense
`POST /api/expenses`
```json
{
  "description": "Groceries",
  "amount": 120.50,
  "expenseDate": "2026-06-15",
  "category": "FOOD"
}
```

### 2) List Expenses
`GET /api/expenses`

### 3) Monthly Report
`GET /api/reports/monthly?year=2026&month=6`

### 4) Create/Update Budget
`POST /api/budgets`
```json
{
  "month": "2026-06",
  "category": "FOOD",
  "amount": 400,
  "alertThresholdPercent": 80
}
```

### 5) Budget Alerts
`GET /api/budgets/alerts?year=2026&month=6`

### 6) AI Recommendations
`GET /api/insights/recommendations?year=2026&month=6`

## Notes
- Uses in-memory H2 DB; data resets on restart.
- AI recommendations currently use a rule-based provider behind an abstraction interface for easy future API integration.

## Next Iteration Ideas
- Authentication/authorization (JWT)
- Persistent DB (PostgreSQL) + migrations
- Notification channels for alerts (email/push)
- Frontend dashboard and advanced analytics
