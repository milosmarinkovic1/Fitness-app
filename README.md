# FitTrack — Fitness Tracking Web Application

A full-stack fitness tracking application built with Spring Boot and Vue 3.

## Tech Stack

**Backend**
- Java 17 + Spring Boot 3
- Spring Security + JWT Authentication
- Spring Data JPA + Hibernate
- PostgreSQL
- Native SQL queries with window functions (LAG, ROW_NUMBER, RANK)

**Frontend**
- Vue 3 + TypeScript
- Pinia (state management)
- Vue Router 4
- Axios

**Infrastructure**
- Docker + Docker Compose (PostgreSQL)

## Features

- JWT-based authentication (register/login)
- Workout session logging with exercises and sets
- Analytics dashboard:
  - Personal Records (max weight per exercise)
  - Weight Trend (LAG window function)
  - Workout Volume (SUM aggregation)
  - Max Reps (ROW_NUMBER window function)

## How to Run

### Prerequisites
- Java 17
- Node.js
- Docker Desktop

### Backend
```bash
cd fitness-app
docker compose up -d
cd fitness-app
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

Backend runs on `http://localhost:8080`  
Frontend runs on `http://localhost:5173`

## Database Schema

8 tables: `users`, `categories`, `exercises`, `plans`, `plan_exercises`, `workouts`, `sets`, `body_measurements`