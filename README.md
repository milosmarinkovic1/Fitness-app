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

<img width="846" height="855" alt="Screenshot 2026-08-26 185148" src="https://github.com/user-attachments/assets/8b1a9713-5fff-4c7f-85fc-d4447a273275" /><img width="1872" height="875" alt="Screenshot 2026-08-26 185758" src="https://github.com/user-attachments/assets/ec6ef2cd-7d82-49fa-b974-47c0114febaf" />
<img width="1876" height="800" alt="Screenshot 2026-08-26 185721" src="https://github.com/user-attachments/assets/015dd98b-ca44-4c2b-b06e-6114e02cb6c0" />
<img width="1865" height="911" alt="Screenshot 2026-08-26 185659" src="https://github.com/user-attachments/assets/0fec159d-04d3-4bf7-891a-06f487a4ba6d" />
<img width="1855" height="837" alt="Screenshot 2026-08-26 185608" src="https://github.com/user-attachments/assets/6bf7b8aa-5834-4832-a0cf-e4638a25e52e" />
<img width="1881" height="911" alt="Screenshot 2026-08-26 185528" src="https://github.com/user-attachments/assets/36f806b0-7c8b-4a2b-9bdf-0a57f20a3305" />
<img width="1886" height="915" alt="Screenshot 2026-08-26 185635" src="https://github.com/user-attachments/assets/32214921-981c-44d3-834c-d06035142472" />
<img width="832" height="842" alt="Screenshot 2026-08-26 185830" src="https://github.com/user-attachments/assets/d8eafd9a-36fd-473f-ba99-d6cddadcad8d" />
<img width="842" height="516" alt="Screenshot 2026-08-26 185849" src="https://github.com/user-attachments/assets/1d300178-4011-4685-9845-afb2d6fbb44d" />

<img width="755" height="777" alt="Screenshot 2026-08-26 185313" src="https://github.com/user-attachments/assets/24c2fbed-baae-4b8f-9b97-606ff432ef4a" />


