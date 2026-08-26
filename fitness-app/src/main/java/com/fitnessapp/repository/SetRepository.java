package com.fitnessapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fitnessapp.model.Set;

public interface SetRepository extends JpaRepository<Set,Long>{

    List<Set> findByWorkoutWorkoutId (Long workoutId);


    @Query(nativeQuery = true, value = """
    SELECT 
        e.name AS exercise_name,
        MAX(s.weight) AS max_weight,
        (SELECT w2.workout_date 
         FROM workouts w2 
         JOIN sets s2 ON w2.workout_id = s2.workout_id
         WHERE s2.exercise_id = e.exercise_id 
         AND s2.weight = MAX(s.weight)
         AND w2.user_id = u.user_id
         LIMIT 1) AS achieved_date
    FROM users u
    JOIN workouts w ON u.user_id = w.user_id
    JOIN sets s ON w.workout_id = s.workout_id
    JOIN exercises e ON s.exercise_id = e.exercise_id
    WHERE u.user_id = :userId
    GROUP BY e.exercise_id, e.name, u.user_id
    ORDER BY max_weight DESC
    """)
List<Object[]> findPersonalRecordsByUserId(@Param("userId") Long userId);

//:userId — parametar koji Spring zameni sa stvarnom vrednošću
//@Param("userId") — vezuje parametar metode za :userId u upitu

/*
Ne postoji Java klasa koja odgovara ovom rezultatu.Zato koristimo Object[], 
 svaki red je niz objekata:
Object[0] = "Bench Press"     (String)
Object[1] = 100.00            (BigDecimal)
Object[2] = 2026-08-06...     (Timestamp)



Baza vraća List<Object[]> — sirovi podaci, niz objekata bez strukture
lista nizova objekata!!!!!

SetService konvertuje svaki Object[] red u 
WeightTrendDTO — daje mu strukturu i nazive

Spring automatski pretvara WeightTrendDTO u JSON i šalje frontendu
*/

@Query(nativeQuery = true, value = """
    SELECT 
        e.name AS exercise_name,
        w.workout_date,
        s.weight,
        LAG(s.weight) OVER (
            PARTITION BY e.exercise_id 
            ORDER BY w.workout_date
        ) AS previous_weight,
        COALESCE(s.weight - LAG(s.weight) OVER (
            PARTITION BY e.exercise_id 
            ORDER BY w.workout_date
        ), 0) AS difference
    FROM users u
    JOIN workouts w ON u.user_id = w.user_id
    JOIN sets s ON w.workout_id = s.workout_id
    JOIN exercises e ON s.exercise_id = e.exercise_id
    WHERE u.user_id = :userId
    ORDER BY e.name, w.workout_date
    """)
List<Object[]> findWeightTrendByUserId(@Param("userId") Long userId);


@Query(nativeQuery = true, value = """
    SELECT 
        w.workout_id,
        w.workout_date,
        COALESCE(SUM(s.weight * s.reps), 0) AS total_volume
    FROM users u
    JOIN workouts w ON u.user_id = w.user_id
    LEFT JOIN sets s ON w.workout_id = s.workout_id
    WHERE u.user_id = :userId
    GROUP BY w.workout_id, w.workout_date
    ORDER BY w.workout_date DESC
    """)
List<Object[]> findWorkoutVolumeByUserId(@Param("userId") Long userId);

//COALESCE(SUM(...), 0)	Ako trening nema setova, vraća 0 umesto NULL
//LEFT JOIN	Omogućava da se vide i treninzi bez setova

// max reps 
@Query(value = """
    WITH ranked_sets AS (
        SELECT 
            e.name AS exercise_name,
            s.reps,
            s.weight,
            ROW_NUMBER() OVER (
                PARTITION BY e.exercise_id 
                ORDER BY s.reps DESC, s.weight DESC
            ) AS rn
        FROM users u
        JOIN workouts w ON u.user_id = w.user_id
        JOIN sets s ON w.workout_id = s.workout_id
        JOIN exercises e ON s.exercise_id = e.exercise_id
        WHERE u.user_id = :userId
    )
    SELECT 
        exercise_name,
        reps AS max_reps,
        weight AS weight_at_max_reps
    FROM ranked_sets
    WHERE rn = 1
    ORDER BY exercise_name
    """, nativeQuery = true)
List<Object[]> findMaxRepsByUserId(@Param("userId") Long userId);

}