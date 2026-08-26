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

## Screenshots

<img width="846" height="855" alt="Screenshot 2026-08-26 185148" src="https://github.com/user-attachments/assets/825f6f09-1c22-45ec-9190-057c33a2f7b0" />
<img width="755" height="777" alt="Screenshot 2026-08-26 185313" src="https://github.com/user-attachments/assets/9450db74-b3e4-4852-afb5-8aa56ac1027e" />

<img width="1855" height="837" alt="Screenshot 2026-08-26 185608" src="https://github.com/user-attachments/assets/ef659ebf-c817-4aae-ad94-b2025244f8f7" />
<img width="1886" height="915" alt="Screenshot 2026-08-26 185635" src="https://github.com/user-attachments/assets/df13ae9a-83e9-4d29-a23f-a5048a6595be" />
<img width="1865" height="911" alt="Screenshot 2026-08-26 185659" src="https://github.com/user-attachments/assets/259a7838-c5d1-4170-8384-cd7631e2e7bc" />
<img width="1876" height="800" alt="Screenshot 2026-08-26 185721" src="https://github.com/user-attachments/assets/9a57c69b-66ba-4e56-8fc4-61c11a039272" />
<img width="1872" height="875" alt="Screenshot 2026-08-26 185758" src="https://github.com/user-attachments/assets/7990d21d-604d-45b5-8095-e1894110c34b" />
<img width="832" height="842" alt="Screenshot 2026-08-26 185830" src="https://github.com/user-attachments/assets/433818e9-7e10-4171-8669-1f9d7f4843b1" />
<img width="842" height="516" alt="Screenshot 2026-08-26 185849" src="https://github.com/user-attachments/assets/22c4ef38-e13c-4690-b81e-cd19c6473550" />
<img width="1881" height="911" alt="Screenshot 2026-08-26 185528" src="https://github.com/user-attachments/assets/c537c280-8877-431c-8e9e-7b3f80c14deb" />








