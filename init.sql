-- =====================================================================
-- PostgreSQL DDL za FITNES APLIKACIJU
-- =====================================================================
-- NAPOMENA ZA SPRING BOOT:
-- spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

-- KREIRANJE ENUM TIPOVA
-- =====================================================================
CREATE TYPE user_status AS ENUM ('ACTIVE', 'LOCKED', 'PENDING');

-- =====================================================================
-- KREIRANJE TABELA
-- =====================================================================

-- 1. KORISNICI
CREATE TABLE users (
    user_id           SERIAL PRIMARY KEY,
    full_name         VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL UNIQUE,
    password_hash     VARCHAR(255) NOT NULL,
    date_of_birth     DATE,
    gender            CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    last_login        TIMESTAMP,
    account_status    user_status DEFAULT 'ACTIVE',
    registration_date DATE DEFAULT CURRENT_DATE
);

-- 2. KATEGORIJE
CREATE TABLE categories (
    category_id       SERIAL PRIMARY KEY,
    name              VARCHAR(50) NOT NULL UNIQUE
);

-- 3. VEŽBE
CREATE TABLE exercises (
    exercise_id       SERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL UNIQUE,
    description       VARCHAR(500),
    category_id       INTEGER NOT NULL REFERENCES categories(category_id) ON DELETE RESTRICT
);

-- 4. ŠABLONI TRENINGA (PLANOVI)
CREATE TABLE plans (
    plan_id           SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    name              VARCHAR(100) NOT NULL,
    description       VARCHAR(500),
    created_date      DATE DEFAULT CURRENT_DATE
);

-- 5. VEŽBE U OKVIRU ŠABLONA
CREATE TABLE plan_exercises (
    plan_exercise_id  SERIAL PRIMARY KEY,
    plan_id           INTEGER NOT NULL REFERENCES plans(plan_id) ON DELETE CASCADE,
    exercise_id       INTEGER NOT NULL REFERENCES exercises(exercise_id) ON DELETE RESTRICT,
    order_number      INTEGER NOT NULL,
    target_sets       INTEGER,
    target_reps       INTEGER,
    target_weight     NUMERIC(5,2),
    rest_time         INTEGER,
    CONSTRAINT unique_order_per_plan UNIQUE (plan_id, order_number),
    CONSTRAINT unique_exercise_per_plan UNIQUE (plan_id, exercise_id)
);

-- 6. REALIZOVANI TRENINZI
CREATE TABLE workouts (
    workout_id        SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    plan_id           INTEGER NULL REFERENCES plans(plan_id) ON DELETE SET NULL,
    workout_date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    duration          INTEGER,
    feeling           INTEGER CHECK (feeling BETWEEN 1 AND 5),
    note              VARCHAR(500)
);

-- 7. SERIJE (UNUTAR TRENINGA)
CREATE TABLE sets (
    set_id            SERIAL PRIMARY KEY,
    workout_id        INTEGER NOT NULL REFERENCES workouts(workout_id) ON DELETE CASCADE,
    exercise_id       INTEGER NOT NULL REFERENCES exercises(exercise_id) ON DELETE RESTRICT,
    set_number        INTEGER NOT NULL,
    weight            NUMERIC(5,2),
    reps              INTEGER,
    CONSTRAINT unique_set_per_workout_exercise UNIQUE (workout_id, exercise_id, set_number)
);

-- 8. MERENJA TELA
CREATE TABLE body_measurements (
    measurement_id    SERIAL PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    measure_date      DATE NOT NULL DEFAULT CURRENT_DATE,
    weight            NUMERIC(5,2),
    body_fat          NUMERIC(3,1),
    muscle_mass       NUMERIC(5,2),
    waist_cm          NUMERIC(4,1),
    chest_cm          NUMERIC(4,1),
    arm_cm            NUMERIC(4,1),
    leg_cm            NUMERIC(4,1),
    note              VARCHAR(500),
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_user_measure_date UNIQUE (user_id, measure_date)
);

-- =====================================================================
-- INDEKSI (Samo oni koji su zaista potrebni)
-- =====================================================================

CREATE INDEX idx_exercises_category ON exercises(category_id);
CREATE INDEX idx_plans_user ON plans(user_id);
CREATE INDEX idx_plan_exercises_exercise ON plan_exercises(exercise_id);
CREATE INDEX idx_workouts_user ON workouts(user_id);
CREATE INDEX idx_workouts_plan ON workouts(plan_id);
CREATE INDEX idx_workouts_date ON workouts(workout_date);
CREATE INDEX idx_sets_exercise ON sets(exercise_id);
CREATE INDEX idx_body_measurements_date ON body_measurements(measure_date);

-- =====================================================================
-- POČETNI PODACI (KATEGORIJE + VEŽBE)
-- =====================================================================

INSERT INTO categories (name) VALUES
('Chest'),
('Back'),
('Legs'),
('Shoulders'),
('Arms'),
('Core'),
('Cardio');

INSERT INTO exercises (name, description, category_id) VALUES
('Bench Press', 'Lezi na ravnu klupu, spusti šipku do grudi, potisni nagore do potpunog ispravljanja ruku.', 1),
('Incline Dumbbell Press', 'Lezi na kosu klupu (45°), potisni bučice nagore, lagano ih spoji na vrhu.', 1),
('Chest Dips', 'Osloni se na paralelne šipke, spusti telo dole dok osetiš istezanje u grudima, potisni se nagore.', 1),
('Pull Ups', 'Vis na zgibovima, povuci telo nagore dok brada ne pređe šipku, polako se spusti dole.', 2),
('Deadlift', 'Stani ispred šipke, savij kolena, uhvati šipku, podigni je ispravljajući leđa i noge.', 2),
('Barbell Row', 'Nagni trup napred pod uglom od 45°, povuci šipku ka stomaku, stisni lopatice.', 2),
('Squat', 'Stani sa šipkom na ramenima, spusti kukove dole kao da sedaš na stolicu, vrati se u uspravan stav.', 3),
('Leg Press', 'Sedi u spravu, odgurni platformu nogama, polako je spuštaj do grudi.', 3),
('Romanian Deadlift', 'Drži šipku u rukama, lagano savij kolena, spuštaj šipku niz noge do osećaja istezanja u zadnjoj loži.', 3),
('Shoulder Press', 'Stani ili sedi, potisni bučice/šipku iznad glave do potpunog ispravljanja ruku.', 4),
('Lateral Raises', 'Stani uspravno, podigni bučice bočno do visine ramena, polako spusti.', 4),
('Face Pulls', 'Na sajli, povuci uže ka licu, stisni lopatice i zadrži sekundu.', 4),
('Bicep Curl', 'Stani, savij lakat, podigni bučicu prema ramenu, polako spusti.', 5),
('Tricep Pushdown', 'Na sajli, pritisni šipku nadole ka butinama, zadrži sekundu, vrati nazad.', 5),
('Plank', 'Osloni se na podlaktice i prste, drži telo u potpunoj ravnoj liniji, stegni stomak.', 6),
('Russian Twist', 'Sedi na podu sa savijenim kolenima, podigni stopala, rotiraj torzo levo-desno sa bučicom.', 6),
('Running', 'Trčanje na traci ili u prirodi, održavaj konstantan ritam.', 7),
('Jump Rope', 'Preskakanje vijače, održavaj ritam, skači na prstima.', 7);

