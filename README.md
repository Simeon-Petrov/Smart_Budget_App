# Smart_Budget_App
AI-First Development Exam Project: Smart Budget App using BMAD.

---

## BMAD Analysis Phase - SmartBudget App

### 1. Functional Requirements (User Stories)

#### Core User Stories

**US-1: Add Income Record**
- **As a** user,
- **I want to** add income transactions with amount, date, category, and optional notes,
- **So that** I can track my earnings and monitor income sources.
- **Acceptance Criteria:**
  - User can enter income amount (positive number)
  - User can select/create income category (e.g., Salary, Freelance, Bonus)
  - User can set transaction date
  - Optional notes field for additional details
  - Confirmation message upon successful addition

**US-2: Add Expense Record**
- **As a** user,
- **I want to** add expense transactions with amount, date, category, and optional notes,
- **So that** I can track my spending and identify expense patterns.
- **Acceptance Criteria:**
  - User can enter expense amount (positive number)
  - User can select/create expense category (e.g., Rent, Food, Transport, Entertainment)
  - User can set transaction date
  - Optional notes field for additional details
  - Confirmation message upon successful addition

**US-3: Manage Categories**
- **As a** user,
- **I want to** create, view, and manage transaction categories,
- **So that** I can organize my transactions in a way that makes sense for my budget.
- **Acceptance Criteria:**
  - User can create custom categories
  - User can assign categories to transactions
  - Default categories are provided (predefined)
  - Categories can be edited or deleted
  - Categories are color-coded for visual distinction

**US-4: View Transaction Summary**
- **As a** user,
- **I want to** see a summary of my income and expenses by category and time period,
- **So that** I can understand my financial status at a glance.
- **Acceptance Criteria:**
  - Summary displays total income, total expenses, and net balance
  - Summary can be filtered by date range (daily, weekly, monthly, yearly)
  - Summary shows breakdown by category
  - Summary is updated in real-time after adding transactions

**US-5: View Visual Charts**
- **As a** user,
- **I want to** see visual representations (pie charts, bar charts) of my spending and income,
- **So that** I can easily identify spending patterns and trends.
- **Acceptance Criteria:**
  - Pie chart showing expense distribution by category
  - Pie chart showing income distribution by category
  - Bar chart showing spending trends over time
  - Charts are interactive and responsive
  - Charts update when transactions are added/modified

**US-6: Edit/Delete Transactions**
- **As a** user,
- **I want to** edit or delete existing transactions,
- **So that** I can correct mistakes or remove unwanted records.
- **Acceptance Criteria:**
  - User can select and edit transaction details
  - User can delete transactions with confirmation
  - Changes are reflected in summaries and charts immediately
  - Deleted transactions are removed from all reports

**US-7: (Optional) AI Budget Suggestions**
- **As a** user,
- **I want to** receive AI-based suggestions for budget optimization,
- **So that** I can improve my financial habits and spending decisions.
- **Acceptance Criteria:**
  - AI analyzes spending patterns
  - Suggestions are based on category trends
  - Recommendations include saving opportunities
  - Suggestions are displayed on dashboard
  - User can dismiss or act on suggestions

---

### 2. Tech Stack Recommendation

#### Backend: Java + Spring Boot
**Why Spring Boot?**
- **Rapid Development:** Spring Boot provides ready-made configurations and starters, reducing boilerplate code—perfect for learning Java with practical project output.
- **REST API Easy:** Built-in support for creating RESTful APIs with minimal code.
- **Data Persistence:** Spring Data JPA simplifies database operations, abstracting complex SQL for beginners.
- **Security:** Spring Security handles authentication and authorization without complex setup.
- **Community & Documentation:** Extensive resources and examples available, ideal for junior developers.
- **Industry Standard:** Most Java job roles require Spring Boot knowledge.

#### Frontend: React.js or Vue.js
**Why React?**
- **Component-Based:** Easy to learn and understand for building UI components.
- **Chart Libraries:** Easy integration with Chart.js or Recharts for visualizations.
- **Ecosystem:** Rich npm packages for UI, state management, and styling.
- **Beginner-Friendly:** JSX syntax is intuitive, and learning curve is manageable.

**Alternative: Vue.js**
- Simpler syntax than React
- Easier for beginners to pick up
- Still powerful for building interactive dashboards

#### Database: PostgreSQL (or MySQL)
**Why PostgreSQL?**
- **Free & Open-Source:** No licensing costs.
- **ACID Compliance:** Ensures data integrity for financial transactions.
- **JSON Support:** Can store flexible data structures if needed.
- **Scalability:** Suitable for growing application needs.
- **Integration:** Seamless integration with Spring Boot via JPA/Hibernate.

**Alternative: MySQL**
- Simpler setup for beginners
- Widely used and well-documented
- Slightly easier for first-time database users

#### ORM Framework: Spring Data JPA + Hibernate
- **Why:** Eliminates raw SQL, auto-generates database schema from Java entities, reduces code complexity.

#### API Documentation: Springdoc OpenAPI (Swagger)
- **Why:** Auto-generates interactive API documentation, helps in testing and frontend-backend integration.

#### Build Tool: Maven
- **Why:** Simple dependency management, easy for beginners, widely used in Java projects.

#### Recommended Development Stack Summary
```
Backend:         Java 17+ (LTS) + Spring Boot 3.x
Frontend:        React.js 18+ or Vue.js 3+
Database:        PostgreSQL 14+ or MySQL 8+
ORM:            Spring Data JPA + Hibernate
Build Tool:      Maven
Container:       Docker (optional, for deployment)
Testing:         JUnit 5, Mockito
API Docs:        Springdoc OpenAPI (Swagger)
```

---

### 3. Core Data Model Draft

#### Entity Relationship Overview
```
User
├── Transactions (1:N relationship)
│   ├── Category (N:1 relationship)
│   └── Type (Income/Expense)
└── Categories (1:N relationship)
```

#### Entity: User
```java
// Primary user account
- userId (PK): UUID or Long
- username: String (unique)
- email: String (unique)
- passwordHash: String
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

#### Entity: Category
```java
// Transaction categories (Income or Expense)
- categoryId (PK): UUID or Long
- userId (FK): Long (user who created it)
- categoryName: String (e.g., "Salary", "Rent", "Food")
- categoryType: Enum [INCOME, EXPENSE]
- color: String (hex color for UI representation, e.g., "#FF5733")
- description: String (optional)
- createdAt: LocalDateTime
- isDefault: Boolean (true for predefined categories)
```

#### Entity: Transaction
```java
// Individual income/expense records
- transactionId (PK): UUID or Long
- userId (FK): Long (user who owns the transaction)
- categoryId (FK): Long (links to Category)
- amount: BigDecimal (always positive, type determines direction)
- type: Enum [INCOME, EXPENSE]
- description: String (e.g., "Monthly salary", "Grocery shopping")
- notes: String (optional, user notes)
- transactionDate: LocalDate (when the transaction occurred)
- createdAt: LocalDateTime (when record was created)
- updatedAt: LocalDateTime
```

#### Entity: BudgetSummary (View/DTO - not stored in DB)
```java
// Computed data for summaries and reports
- summaryId: UUID
- userId (FK): Long
- totalIncome: BigDecimal
- totalExpense: BigDecimal
- netBalance: BigDecimal
- periodStart: LocalDate
- periodEnd: LocalDate
- categoryBreakdown: Map<String, BigDecimal>
```

#### Key Relationships & Constraints
1. **One User → Many Transactions:** Users can have multiple transactions
2. **One User → Many Categories:** Users can create multiple categories
3. **One Category → Many Transactions:** Categories can be used for multiple transactions
4. **Data Integrity:**
   - Soft delete for transactions (add `isDeleted` flag) for audit trail
   - Foreign key constraints to ensure referential integrity
   - Unique constraint on username and email
   - Amount must be positive (validation in entity/service layer)

#### Database Tables Summary
| Table | Purpose |
|-------|---------|
| `users` | Store user accounts and credentials |
| `categories` | Store transaction categories (income/expense) |
| `transactions` | Store all income/expense records |

#### Sample Data for Each Entity

**Sample User:**
```
userId: 1
username: "john_doe"
email: "john@example.com"
```

**Sample Categories:**
```
categoryId: 1, categoryName: "Salary", categoryType: INCOME, color: "#27AE60"
categoryId: 2, categoryName: "Rent", categoryType: EXPENSE, color: "#E74C3C"
categoryId: 3, categoryName: "Food", categoryType: EXPENSE, color: "#F39C12"
categoryId: 4, categoryName: "Freelance", categoryType: INCOME, color: "#3498DB"
```

**Sample Transaction:**
```
transactionId: 1
userId: 1
categoryId: 1
amount: 3000.00
type: INCOME
description: "Monthly salary"
transactionDate: 2025-11-17
```

---

## Next Steps (Design Phase - Coming Next)
1. Database schema design with SQL scripts
2. RESTful API endpoint specifications
3. Frontend UI/UX wireframes
4. Authentication & authorization flow
5. Error handling and validation strategy

---

## BMAD Planning Phase - Backend Design (API + DB)

### 1. RESTful API Endpoints

Below are suggested REST endpoints for core CRUD operations and summaries. Keep endpoints simple and RESTful. Use JSON request/response bodies.

- **Create User:** `POST /api/users`
  - Description: Create a new user account.
  - Request: `{ "username": "john", "email": "john@example.com", "password": "secret" }`
  - Response: `201 Created` with created user DTO (without password).

- **Get User:** `GET /api/users/{userId}`
  - Description: Retrieve user profile (safe fields only).
  - Response: `200 OK` with user DTO.

- **Update User:** `PUT /api/users/{userId}`
  - Description: Update user profile (email, username, etc.).
  - Request: partial or full user DTO.

- **Delete User:** `DELETE /api/users/{userId}`
  - Description: Soft-delete or remove a user (consider soft-delete for audit).

- **List Categories (by user):** `GET /api/users/{userId}/categories`
  - Description: Returns categories for the user, includes defaults.

- **Create Category:** `POST /api/users/{userId}/categories`
  - Description: Add a new category for the user.
  - Request: `{ "name": "Food", "type": "EXPENSE", "color": "#F39C12" }`

- **Get Category:** `GET /api/categories/{categoryId}`

- **Update Category:** `PUT /api/categories/{categoryId}`

- **Delete Category:** `DELETE /api/categories/{categoryId}`
  - Note: Prevent deletion if category is used in transactions or offer reassign option.

- **List Transactions (by user):** `GET /api/users/{userId}/transactions`
  - Description: Supports query parameters: `startDate`, `endDate`, `categoryId`, `type` (INCOME/EXPENSE), `page`, `size`.
  - Example: `GET /api/users/1/transactions?startDate=2025-11-01&endDate=2025-11-30&type=EXPENSE`

- **Create Transaction:** `POST /api/users/{userId}/transactions`
  - Description: Add income or expense record.
  - Request: `{ "amount": 25.50, "categoryId": 3, "type": "EXPENSE", "transactionDate": "2025-11-17", "notes": "Lunch" }`

- **Get Transaction:** `GET /api/transactions/{transactionId}`

- **Update Transaction:** `PUT /api/transactions/{transactionId}`

- **Delete Transaction (soft):** `DELETE /api/transactions/{transactionId}`
  - Description: Soft-delete and mark `is_deleted=true` so reports can exclude it but data remains.

- **Summary / Aggregation:** `GET /api/users/{userId}/summary?startDate=&endDate=`
  - Description: Returns `totalIncome`, `totalExpense`, `netBalance`, and `categoryBreakdown` within the period.

- **Charts Data:** `GET /api/users/{userId}/charts?startDate=&endDate=&chart=pie|bar&mode=income|expense`
  - Description: Returns datasets formatted for Chart.js or Recharts (labels + values).

- **(Optional) AI Suggestions:** `GET /api/users/{userId}/suggestions?startDate=&endDate=`
  - Description: Returns AI-generated recommendations (e.g., reduce dining out by X%). This can be implemented as a separate service calling a model.

### 2. Database Schema (SQL DDL)

Below are PostgreSQL-compatible CREATE TABLE statements for the three main entities. These prioritize clarity for junior developers and include primary keys, foreign keys, NOT NULL, UNIQUE, basic checks, and indexes.

```sql
-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME','EXPENSE')),
    color VARCHAR(7), -- hex color, e.g. #FF5733
    description TEXT,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    UNIQUE (user_id, name)
);

-- Transactions table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    amount NUMERIC(14,2) NOT NULL CHECK (amount > 0),
    type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME','EXPENSE')),
    description VARCHAR(255),
    notes TEXT,
    transaction_date DATE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Helpful indexes
CREATE INDEX idx_transactions_user_date ON transactions(user_id, transaction_date);
CREATE INDEX idx_categories_user ON categories(user_id);
```

Notes and recommendations:
- Use `BIGSERIAL` for simple auto-increment integer IDs; for stronger uniqueness across services prefer `UUID`.
- The `category_id` is nullable in `transactions` to allow deleting categories: prefer reassigning or soft-deleting categories instead of hard delete.
- Keep amount as `NUMERIC(14,2)` to represent currency safely.
- Maintain `updated_at` via application code or database triggers.
- Add migration scripts (Flyway or Liquibase) for schema changes in real projects.

---

## Next Steps (Suggested)
1. Create Spring Boot project skeleton with entities (`User`, `Category`, `Transaction`) annotated with JPA.
2. Implement repository interfaces using Spring Data JPA.
3. Implement DTOs and simple controllers for endpoints above.
4. Add input validation (`@Valid`) and exception handlers.
5. Add tests for repository and controller layers.
