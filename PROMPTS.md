# SmartBudget App - AI Development Prompts

This document contains all prompts given to the AI model for the SmartBudget App project, ordered chronologically.

---

## PROMPT 1: BMAD Analysis Phase - Initial Requirements and Java Tech Stack

**Date:** November 17, 2025

**Objective:** Conduct the BMAD Analysis Phase for the SmartBudget App project.

**Instructions:**

Act as an AI-First Developer applying the BMAD methodology (Analysis Phase). The goal is to build a fully functional web application called 'SmartBudget App' for personal finance management.

**IMPORTANT CONSTRAINTS:**
1. **Target Language:** Java must be used for the Backend.
2. **Target Audience:** I am currently learning Java, so please answer as for a junior/intern, focusing on the simplest and most common best practices for rapid development and clean code.

**Required Features:**
1. Add income and expense records.
2. Categorize transactions (e.g., Rent, Salary, Transport).
3. Display summaries and visual charts of spending.
4. (Optional) AI-based suggestions for budget optimization.

**Deliverables:**
1. **High-Level Functional Requirements (User Stories).**
2. **Recommended Tech Stack** (Backend: Java/Spring Boot; Frontend/Database) and justification, keeping the junior level in mind.
3. **Core Data Model Draft** (Entities and attributes needed for transactions and categories).

**Format Requirements:**
The output must be structured clearly under the headings: 
1. Functional Requirements
2. Tech Stack Recommendation
3. Core Data Model

**Status:** ✅ Completed

---

## PROMPT 2: BMAD Planning Phase - Detailed Technical Design

**Date:** November 17, 2025

**Objective:** Develop the BMAD Planning Phase for backend design, including REST API specifications and database schema.

**Instructions:**

Act as a Senior Backend Architect using the BMAD Planning Phase. Based on the Java/Spring Boot stack and the Core Data Model defined in the previous analysis (check README.md for context), provide detailed specifications for the backend.

**Deliverables:**
1. **RESTful API Endpoint Specification:** For the core CRUD operations (User, Category, Transaction). Specify the HTTP method, endpoint URL, and a brief description for each endpoint.
2. **Database Schema Design (SQL DDL):** Generate the necessary PostgreSQL (or MySQL) SQL commands (CREATE TABLE) for the three main entities: `users`, `categories`, and `transactions`, including primary keys, foreign keys, and constraints (like NOT NULL or UNIQUE).

**Format Requirements:**
The output must be structured clearly under the headings: 
1. RESTful API Endpoints
2. Database Schema (SQL DDL)

**Additional Context:**
- Refer to the Core Data Model defined in PROMPT 1 (see README.md).
- Include query parameters and examples for list endpoints (filtering by date range, type, etc.).
- Provide comprehensive SQL DDL with constraints, indexes, and clear comments for junior developers.

**Status:** ✅ Completed

---

## PROMPT 3: BMAD Solutioning Phase - Backend Entities and Repositories (PostgreSQL Focus)

**Date:** November 17, 2025

**Objective:** Execute the BMAD Solutioning Phase by generating Java entity classes and Spring Data JPA repositories.

**Instructions:**

Act as an AI-First Developer executing the BMAD Solutioning Phase. The project uses Java 17+ with Spring Boot 3.x and Spring Data JPA.

**Constraints:**
1. **Database:** Exclusively use PostgreSQL data types and conventions where applicable.
2. **Base Package:** All classes must be under the package `com.smartbudget`.

**Deliverables:**
1. **JPA Entity Classes:**
   - `User.java`
   - `Category.java`
   - `Transaction.java`
   - Use Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) for brevity.
   - Ensure correct JPA annotations (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@OneToMany`, `@ManyToOne`, `@Enumerated`, etc.).
   - Implement the relationships (e.g., User to Transactions) and use the `BIGSERIAL` equivalent (Long/long and appropriate JPA strategy).

2. **Spring Data JPA Repository Interfaces:**
   - `UserRepository.java` (extends JpaRepository)
   - `CategoryRepository.java` (extends JpaRepository)
   - `TransactionRepository.java` (extends JpaRepository)
   - Include custom query methods for filtering, aggregation, and business logic (e.g., find by date range, sum income/expenses).

**Additional Context:**
- Refer to the SQL DDL schema from PROMPT 2 for table structure and constraints.
- Include proper javadoc comments for clarity.
- Use Jakarta Persistence annotations (new standard) instead of older javax.persistence.

**File Structure:**
```
src/main/java/com/smartbudget/
├── entity/
│   ├── User.java
│   ├── Category.java
│   ├── Transaction.java
│   └── TransactionType.java (Enum)
└── repository/
    ├── UserRepository.java
    ├── CategoryRepository.java
    └── TransactionRepository.java
```

**Status:** ✅ Completed

---

## PROMPT 4: BMAD Solutioning Phase - Project Setup (Maven and Spring Boot)

**Date:** November 17, 2025

**Objective:** Set up the core Spring Boot project files (Maven `pom.xml`, main application class, and `application.properties`) for a Java 17+, Spring Boot 3.x, PostgreSQL project using base package `com.smartbudget`.

**Instructions:**

Act as an AI-First Developer setting up the core Spring Boot application. We are using Java 17+, Spring Boot 3.x, PostgreSQL, and the base package `com.smartbudget`.

**Deliverables:**
1. **`pom.xml`:** Full Maven configuration containing dependencies for Web, Data JPA, PostgreSQL driver, Lombok, Springdoc OpenAPI (Swagger), and testing.
2. **`SmartBudgetApplication.java`:** Main Spring Boot application class in package `com.smartbudget`.
3. **`application.properties`:** Configuration file with a local PostgreSQL connection (`postgres`/`password`, database `smartbudget_db`, port 5432) and JPA DDL generation enabled.

**Format Requirements:**
Structure the output with separate code blocks for `pom.xml`, `SmartBudgetApplication.java`, and `application.properties`.

**Additional Context:**
- These files should be added to the repository under their standard Maven locations.
- Use Springdoc OpenAPI starter for automatic Swagger UI.

**Status:** ✅ Completed

---

## PROMPT 5: BMAD Solutioning Phase - DTOs and Service Layer

**Date:** November 17, 2025

**Objective:** Continue BMAD Solutioning by creating DTOs and Service Layer for Transaction and Category.

**Instructions:**

Act as an AI-First Developer continuing the Solutioning Phase. The project uses Java/Spring Boot, the base package `com.smartbudget`, and the entities/repositories previously generated.

**Constraints:**
1. **Database:** PostgreSQL conventions (already applied to entities/repositories).
2. **Base Package:** All new classes must be under `com.smartbudget`.

**Deliverables:**
1. **DTOs (with validation):**
   - `TransactionDto.java`
   - `CategoryDto.java`
   - `SummaryDto.java`
2. **Service layer (interfaces & implementations):**
   - `TransactionService.java`, `TransactionServiceImpl.java`
   - `CategoryService.java`, `CategoryServiceImpl.java`

**Notes:**
- DTO validation uses Jakarta Bean Validation annotations.
- Services implement basic mapping between entities and DTOs, and throw `ResourceNotFoundException` when appropriate.

**Status:** ✅ Completed

---

## PROMPT 6: BMAD Solutioning Phase - REST Controllers

**Date:** November 17, 2025

**Objective:** Create REST controller classes for Transaction and Category endpoints.

**Instructions:**

Act as an AI-First Developer creating the final backend layer. The project uses Spring Boot, the base package `com.smartbudget`, and the services/DTOs generated in PROMPT 5.

**Constraints:**
1. Controllers must be in the package `com.smartbudget.controller`.
2. Use the generated Service layer interfaces (e.g., `TransactionService`, `CategoryService`).
3. Handle request bodies using the generated DTOs.
4. Use `@Validated` and standard Spring REST annotations (`@RestController`, `@RequestMapping`, etc.).

**Deliverables:**
1. **`TransactionController.java`** – CRUD endpoints + advanced operations:
   - `POST /api/transactions` – Create transaction
   - `GET /api/transactions/{id}` – Get by ID
   - `GET /api/transactions` – List all for user
   - `GET /api/transactions/range` – Filter by date range
   - `GET /api/transactions/summary` – Aggregated summary
   - `PUT /api/transactions/{id}` – Update transaction
   - `DELETE /api/transactions/{id}` – Delete (soft-delete)

2. **`CategoryController.java`** – CRUD endpoints:
   - `POST /api/categories` – Create category
   - `GET /api/categories/{id}` – Get by ID
   - `GET /api/categories` – List by user
   - `PUT /api/categories/{id}` – Update category
   - `DELETE /api/categories/{id}` – Delete category

**Status:** ✅ Completed

---

## PROMPT 7: BMAD Testing Phase - Controller Integration Tests

**Date:** November 17, 2025

**Objective:** Develop integration tests for the REST Controller layer using JUnit 5 and Mockito.

**Instructions:**

Act as an AI-First Developer executing the Testing Phase for the SmartBudget application. We will use JUnit 5 and Mockito for integration testing.

**Constraints:**
1. Use `@WebMvcTest` annotation targeting `TransactionController`.
2. Mock `TransactionService` using `@MockBean`.
3. Use `MockMvc` to perform API calls (GET, POST, PUT, DELETE).
4. Properly serialize/deserialize JSON using `ObjectMapper`.

**Deliverables:**
1. **`TransactionControllerIntegrationTest.java`** – Test class with the following test methods:
   - `testCreateTransaction_success` – POST request with valid `TransactionDto` → HTTP 201 Created
   - `testGetTransaction_success` – GET by ID → HTTP 200 OK
   - `testGetTransaction_notFound` – GET non-existent ID → HTTP 404
   - `testGetTransactionSummary_success` – GET summary with aggregated results → HTTP 200 OK with totals and breakdown
   - `testUpdateTransaction_success` – PUT request → HTTP 200 OK
   - `testDeleteTransaction_success` – DELETE request → HTTP 204 No Content
   - `testCreateTransaction_invalidData` – POST with missing required field → HTTP 400 Bad Request
   - `testCreateTransaction_negativeAmount` – POST with negative amount → HTTP 400 Bad Request

**Key Practices:**
- Use `@BeforeEach` to set up mock data.
- Use `mockMvc.perform()` to simulate HTTP requests.
- Assert HTTP status codes and response JSON content using `jsonPath()`.
- Mock service methods using `when()`, `doNothing()`, etc.

**Status:** ✅ Completed

---

## PROMPT 8: BMAD Testing Phase - Service Layer Unit Tests

**Date:** November 17, 2025

**Objective:** Develop unit tests for the business logic layer (Service layer) using JUnit 5 and Mockito.

**Instructions:**

Act as an AI-First Developer completing the Testing Phase. We will now focus on the business logic layer using standard JUnit 5 and Mockito.

**Constraints:**
1. Target `TransactionServiceImpl` class for testing.
2. Use `@InjectMocks` to inject the implementation class under test.
3. Mock `TransactionRepository`, `UserRepository`, and `CategoryRepository` using `@Mock`.
4. Use `@ExtendWith(MockitoExtension.class)` for Mockito initialization.

**Deliverables:**
1. **`TransactionServiceImplTest.java`** – Unit test class with the following test methods:
   - `testSaveTransaction_success` – Verify save() maps DTO to Entity and calls repository.save()
   - `testSaveTransaction_userNotFound` – Verify exception when user doesn't exist
   - `testSaveTransaction_categoryNotFound` – Verify exception when category doesn't exist
   - `testFindById_success` – Verify findById() returns correct DTO
   - `testFindById_notFound` – Verify exception when transaction doesn't exist
   - `testCalculateSummary_returnsCorrectTotals` – Test summary aggregation with income/expense totals and category breakdown
   - `testDeleteTransaction_success` – Verify delete() soft-deletes (marks is_deleted=true)
   - `testDeleteTransaction_notFound` – Verify exception when transaction doesn't exist
   - `testFindAllByUserId_success` – Verify findAllByUserId() returns list of DTOs

**Key Practices:**
- Use `@BeforeEach` for test data setup (entities, DTOs).
- Mock repository methods using `when()` and `thenReturn()`.
- Assert on results using `assertEquals()`, `assertNotNull()`, `assertThrows()`.
- Verify repository interactions using `verify()` and check call counts.
- Test both success and failure scenarios (exceptions).

**Status:** ✅ Completed

---

## Next Prompts (Coming Soon)
To be documented as new prompts are provided...





