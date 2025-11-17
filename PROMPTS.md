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

## Next Prompts (Coming Soon)
To be documented as new prompts are provided...
