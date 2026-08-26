#Fitness Tracking Web Application

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
- Chart.js (weight progress visualization)

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
 
- Body Measurements tracking:
  - Weight, body fat, muscle mass
  - Waist, chest, arm, leg circumference
  - CRUD operations (create, read, update, delete)
  - Interactive weight progress chart (Chart.js)
  - Only one measurement per day allowed

## How to Run

### Prerequisites
- Java 17
- Node.js
- Docker Desktop

### Backend
```bash
cd fitness-app
# Pokreni bazu (PostgreSQL)
docker compose up -d

# Pokreni backend
./mvnw spring-boot:runng-boot:run
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

## Screenshots

<img width="1876" height="800" alt="Screenshot 2026-08-26 185721" src="https://github.com/user-attachments/assets/7b59534d-0d78-44a4-9656-7fb67ab57f87" />
<img width="1865" height="911" alt="Screenshot 2026-08-26 185659" src="https://github.com/user-attachments/assets/1171e715-a73e-43f2-855b-a1163b17307a" />
<img width="1872" height="875" alt="Screenshot 2026-08-26 185758" src="https://github.com/user-attachments/assets/e2d100d2-aa0e-4df9-8582-7a7c5c61efa5" />
<img width="1855" height="837" alt="Screenshot 2026-08-26 185608" src="https://github.com/user-attachments/assets/02bacecb-ddbc-4b5d-bde1-f11b54189d76" />
<img width="1881" height="911" alt="Screenshot 2026-08-26 185528" src="https://github.com/user-attachments/assets/7915d4b8-763f-4319-903d-60e435434ffd" />
<img width="1886" height="915" alt="Screenshot 2026-08-26 185635" src="https://github.com/user-attachments/assets/ef9b163c-002b-4e87-98e0-95e6853e5c92" />
<img width="832" height="842" alt="Screenshot 2026-08-26 185830" src="https://github.com/user-attachments/assets/0475b515-b664-434a-acb0-3475a1c23daa" />
<img width="842" height="516" alt="Screenshot 2026-08-26 185849" src="https://github.com/user-attachments/assets/22665f74-117d-4c8e-81fd-f9e793e2e7d0" />
<img width="755" height="777" alt="Screenshot 2026-08-26 185313" src="https://github.com/user-attachments/assets/6cbea00f-da26-4bd5-9a56-7d27efb1c6ff" />
<img width="846" height="855" alt="Screenshot 2026-08-26 185148" src="https://github.com/user-attachments/assets/5b049594-001c-4ca9-8193-4142c9740f7a" />

