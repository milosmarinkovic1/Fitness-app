<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import api from '../api/axios'

// ============================================================
// STATE
// ============================================================
const feeling = ref<number | null>(null)
const note = ref('')
const duration = ref<number | null>(null)
const isLoading = ref(false)
const error = ref('')
const isSubmitting = ref(false)

// Lista vežbi iz baze (za padajući meni)
const exerciseList = ref<Array<{ exerciseId: number; name: string }>>([])

// Dinamička lista vežbi koje korisnik dodaje u trening
const exercises = ref<
  Array<{
    exerciseId: number | null
    weight: number | null
    reps: number | null
    setNumber: number | null
  }>
>([{ exerciseId: null, weight: null, reps: null, setNumber: null }])

const router = useRouter()
const authStore = useAuthStore()

// ============================================================
// METHODS
// ============================================================

// Dohvati listu vežbi sa backenda
async function fetchExercises() {
  try {
    const response = await api.get('/api/exercises')
    exerciseList.value = response.data
  } catch (err) {
    console.error('Error fetching exercises:', err)
  }
}

// Dodaj novu praznu vežbu u listu
function addExercise() {
  exercises.value.push({
    exerciseId: null,
    weight: null,
    reps: null,
    setNumber: null
  })
}

// Ukloni vežbu iz liste (ostavi barem jednu)
function removeExercise(index: number) {
  if (exercises.value.length > 1) {
    exercises.value.splice(index, 1)
  }
}

// Glavna funkcija za kreiranje treninga
async function handleCreateWorkout() {
  // 1. Validacija osnovnih podataka
  if (!feeling.value || !duration.value) {
    error.value = 'Please fill in all required fields'
    return
  }

  if (feeling.value < 1 || feeling.value > 5) {
    error.value = 'Feeling must be between 1 and 5'
    return
  }

  if (duration.value < 1) {
    error.value = 'Duration must be greater than 0 minutes'
    return
  }

  // 2. Validacija vežbi (dozvoljava 0 za weight)
  const hasEmptyExercise = exercises.value.some(
    (ex) =>
      !ex.exerciseId ||
      ex.weight === null ||
      ex.weight === undefined ||
      !ex.reps ||
      !ex.setNumber
  )
  
  if (hasEmptyExercise) {
    error.value = 'Please fill in all exercise fields'
    return
  }

  // 3. Slanje podataka
  isLoading.value = true
  error.value = ''
  isSubmitting.value = true

  try {
    // KORAK 1: Kreiraj trening
    const workoutResponse = await api.post('/api/workouts', { // prvi POST
      feeling: feeling.value,
      note: note.value || '',
      duration: duration.value
    })

    const workoutId = workoutResponse.data.workoutId

    // KORAK 2: Dodaj sve serije (vežbe) u trening
    for (const ex of exercises.value) {
      await api.post(`/api/workouts/${workoutId}/sets`, { // drugi POST
        exerciseId: ex.exerciseId,
        weight: ex.weight,
        reps: ex.reps,
        setNumber: ex.setNumber
      })
    }

    // KORAK 3: Preusmeri na Dashboard
    router.push('/dashboard')
  } catch (err: any) {
    error.value =
      err.response?.data?.message || 'Error creating workout'
  } finally {
    isLoading.value = false
    isSubmitting.value = false
  }
}

// ============================================================
// LIFECYCLE
// ============================================================
onMounted(() => {
  fetchExercises()
})
</script>

<template>
  <div class="form-container">
    <h2>Create New Workout</h2>

    <form @submit.prevent="handleCreateWorkout">
      <!-- ====== BASIC INFO ====== -->
      <div class="form-group">
        <label for="feeling">Feeling (1-5)</label>
        <input
          id="feeling"
          v-model="feeling"
          type="number"
          min="1"
          max="5"
          placeholder="How did you feel?"
          required
        />
      </div>

      <div class="form-group">
        <label for="duration">Duration (minutes)</label>
        <input
          id="duration"
          v-model="duration"
          type="number"
          min="1"
          placeholder="e.g. 60"
          required
        />
      </div>

      <div class="form-group">
        <label for="note">Note (optional)</label>
        <textarea
          id="note"
          v-model="note"
          placeholder="e.g. Great workout, new PR!"
          rows="3"
        />
      </div>

      <!-- ====== EXERCISES ====== -->
      <div class="exercises-section">
        <h3>Exercises</h3>

        <div
          v-for="(ex, index) in exercises"
          :key="index"
          class="exercise-row"
        >
          <div class="exercise-fields">
            <!-- Dropdown za vežbu -->
            <select v-model="ex.exerciseId" required>
              <option :value="null">Select exercise</option>
              <option
                v-for="exercise in exerciseList"
                :key="exercise.exerciseId"
                :value="exercise.exerciseId"
              >
                {{ exercise.name }}
              </option>
            </select>

            <!-- Set number -->
            <input
              v-model="ex.setNumber"
              type="number"
              min="1"
              placeholder="Set"
              required
            />

            <!-- Reps -->
            <input
              v-model="ex.reps"
              type="number"
              min="1"
              placeholder="Reps"
              required
            />

            <!-- Weight -->
            <input
              v-model="ex.weight"
              type="number"
              step="0.5"
              min="0"
              placeholder="Weight (kg)"
              required
            />
            <small class="hint">0 = bodyweight exercise</small>

            <!-- Dugme za uklanjanje -->
            <button
              type="button"
              @click="removeExercise(index)"
              class="btn-remove"
              :disabled="exercises.length === 1"
            >
              ✕
            </button>
          </div>
        </div>

        <button type="button" @click="addExercise" class="btn-add-exercise">
          + Add exercise
        </button>
      </div>

      <!-- ====== ERROR ====== -->
      <div v-if="error" class="error">{{ error }}</div>

      <!-- ====== SUBMIT ====== -->
      <button type="submit" class="btn-primary" :disabled="isSubmitting">
        {{ isSubmitting ? 'Creating...' : 'Create Workout' }}
      </button>

      <p style="text-align: center; margin-top: 1rem">
        <RouterLink to="/dashboard">Back to Dashboard</RouterLink>
      </p>
    </form>
  </div>
</template>

<style scoped>

/* --- Kontejner forme --- */
.form-container {
  max-width: 680px !important;  /* šira forma */
  padding: 3rem !important;     /* više prostora unutra */
  margin: 3rem auto;
  background-color: #1a1a1a;
  border: 1px solid #2a2a2a;
  border-radius: 16px;
}

/* --- Naslov --- */
.form-container h2 {
  font-size: 2rem !important;
  font-weight: 700;
  color: #ffffff;
  text-align: center;
  margin-bottom: 2rem;
  border: none;
  text-transform: none;
  letter-spacing: 0;
}

/* --- Podnaslov sekcije --- */
.exercises-section h3 {
  font-size: 1.2rem !important;
  color: #cccccc;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  margin-bottom: 1.2rem;
  border-bottom: 2px solid #2a2a2a;
  padding-bottom: 0.6rem;
}

/* --- Labele --- */
.form-group label,
.exercises-section label {
  font-size: 0.95rem !important;
  font-weight: 600;
  color: #aaaaaa;
  margin-bottom: 0.5rem;
}

/* --- Inputi, select, textarea --- */
.form-container input,
.form-container select,
.form-container textarea {
  font-size: 1.05rem !important;
  padding: 0.8rem 1.2rem !important;
  border-radius: 10px !important;
  background-color: #111111;
  border: 1px solid #333;
  color: #f0f0f0;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-container input:focus,
.form-container select:focus,
.form-container textarea:focus {
  border-color: #4a9eff;
  box-shadow: 0 0 0 3px rgba(74, 158, 255, 0.2);
  outline: none;
}

/* --- Povećaj visinu textarea --- */
.form-container textarea {
  min-height: 120px;
}

/* --- Razmaci između polja --- */
.form-group {
  margin-bottom: 1.5rem !important;
}

/* --- Sekcija vežbi --- */
.exercises-section {
  background-color: #141414;
  border: 1px solid #2a2a2a;
  border-radius: 12px;
  padding: 1.8rem !important;
  margin: 2rem 0 !important;
}

/* --- Red sa vežbom --- */
.exercise-row {
  margin-bottom: 1rem !important;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #252525;
}

.exercise-row:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

/* --- Polja unutar reda --- */
.exercise-fields {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem !important;
  align-items: center;
}

.exercise-fields select,
.exercise-fields input {
  flex: 1 1 120px;
  min-width: 80px;
  font-size: 0.95rem !important;
  padding: 0.7rem 0.8rem !important;
  border-radius: 8px !important;
}

.exercise-fields select {
  flex: 2 1 160px;
}

/* --- Hint (0 = bodyweight) --- */
.hint {
  font-size: 0.8rem !important;
  color: #777;
  margin-top: 0.2rem;
  flex-basis: 100%;
}

/* --- Dugme za uklanjanje vežbe --- */
.btn-remove {
  background-color: #2a1a1a;
  color: #ff6b6b;
  border: 1px solid #ff6b6b;
  border-radius: 50%;
  width: 36px;
  height: 36px;
  padding: 0;
  font-size: 1.2rem;
  line-height: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}

.btn-remove:hover:not(:disabled) {
  background-color: #ff6b6b;
  color: #fff;
}

.btn-remove:disabled {
  opacity: 0.25;
  cursor: not-allowed;
}

/* --- Dugme "Dodaj vežbu" --- */
.btn-add-exercise {
  background-color: #1a2a1a;
  color: #4caf50;
  border: 1px solid #4caf50;
  border-radius: 8px;
  padding: 0.6rem 1.5rem !important;
  font-size: 1rem !important;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
  margin-top: 0.8rem;
}

.btn-add-exercise:hover {
  background-color: #4caf50;
  color: #fff;
}

/* --- Glavno dugme "Create Workout" --- */
.btn-primary {
  font-size: 1.1rem !important;
  padding: 0.9rem !important;
  border-radius: 10px !important;
  background-color: #4a9eff;
  color: #fff;
  width: 100%;
  border: none;
  font-weight: 700;
  cursor: pointer;
  transition: background 0.2s, transform 0.1s;
  margin-top: 0.5rem;
}

.btn-primary:hover:not(:disabled) {
  background-color: #2d7dd2;
}

.btn-primary:active {
  transform: scale(0.98);
}

.btn-primary:disabled {
  background-color: #2a2a2a;
  color: #555;
  cursor: not-allowed;
}

/* --- Greška --- */
.error {
  font-size: 0.95rem !important;
  padding: 0.8rem 1.2rem !important;
  background-color: #2a1a1a;
  border-left: 4px solid #ff4a4a;
  border-radius: 8px;
  margin-bottom: 1.2rem;
}

/* --- Link za povratak --- */
.form-container p {
  margin-top: 1.5rem;
  text-align: center;
}

.form-container a {
  font-size: 0.95rem;
  color: #4a9eff;
  text-decoration: none;
  transition: color 0.2s;
}

.form-container a:hover {
  color: #7ab8ff;
  text-decoration: underline;
}

/* --- Podešavanje za manje ekrane --- */
@media (max-width: 640px) {
  .form-container {
    padding: 1.5rem !important;
    margin: 1rem;
  }

  .exercise-fields {
    flex-direction: column;
    gap: 0.5rem !important;
  }

  .exercise-fields select,
  .exercise-fields input {
    width: 100%;
    flex: none;
  }

  .btn-remove {
    align-self: flex-end;
  }
}
</style>