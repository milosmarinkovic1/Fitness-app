<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'
import api from '../api/axios'

const authStore = useAuthStore()
const router = useRouter()


const personalRecords = ref<Array<{exerciseName: string, maxWeight: number}>>([])
//ovo je lista objekata koji imaju exerciseName (string) i maxWeight (number)


const weightTrend = ref<Array<{exerciseName: string, workoutDate: string, weight: number, previousWeight: number, difference: number}>>([])
const workoutVolume = ref<Array<{workoutId: number, workoutDate: string, totalVolume: number}>>([])
const maxReps = ref<Array<{exerciseName: string, maxReps: number, weightAtMaxReps: number}>>([])

onMounted(async () => {
  const response = await api.get('/api/analytics/personal-records')
  personalRecords.value = response.data
})

function handleLogout() {
  authStore.logout()
  router.push('/login')
}

const workouts = ref<Array<{workoutId: number, workoutDate: string, duration: number, feeling: number, note: string}>>([])

onMounted(async () => {
  const [prResponse, workoutsResponse, trendResponse, volumeResponse, maxRepsResponse]
   = await Promise.all([
    //Promise all
    //salje oba zahteva istovremeno umesto jedan pa drugi ,brže učitavanje
  api.get('/api/analytics/personal-records'),
  api.get('/api/workouts'),
  api.get('/api/analytics/weight-trend'),
  api.get('/api/analytics/workout-volume'),
  api.get('/api/analytics/max-reps')
])

personalRecords.value = prResponse.data
workouts.value = workoutsResponse.data
weightTrend.value = trendResponse.data
workoutVolume.value = volumeResponse.data
maxReps.value = maxRepsResponse.data
  
})

function formatDate(dateString: string): string {
  const date = new Date(dateString)
  return date.toLocaleDateString('sr-RS', {
    day: '2-digit',
    month: '2-digit', 
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

</script>

<template>
  <div>
    <nav class="navbar">
      <span class="navbar-brand">Fittnes app</span>
      <div style="display: flex; align-items: center; gap: 1rem;">
        <span class="navbar-user">{{ authStore.userEmail }}</span>
        <button class="btn-danger" @click="handleLogout">Logout</button>
      </div>
    </nav>

    <div class="container">
      <div class="page-header">
        <h1>Dashboard</h1>
        <div style="display: flex; gap: 0.75rem;">
          <RouterLink to="/workout/new" class="btn btn-secondary">
            New Workout
          </RouterLink>
          <RouterLink to="/measurements" class="btn btn-secondary">
            Body Measurements
          </RouterLink>
        </div>
      </div>

      <div class="grid-2">
        <div class="card">
          <h2>Personal Records</h2>
          <ul>
            <li v-for="pr in personalRecords" :key="pr.exerciseName">
              <span>{{ pr.exerciseName }}</span>
              <span class="badge badge-blue" style="margin-left: auto;">{{ pr.maxWeight }} kg</span>
            </li>
            <li v-if="personalRecords.length === 0" class="empty-state">No records yet</li>
          </ul>
        </div>

        <div class="card">
          <h2>Max Reps</h2>
          <ul>
            <li v-for="mr in maxReps" :key="mr.exerciseName">
              <span>{{ mr.exerciseName }}</span>
              <span class="badge badge-green" style="margin-left: auto;">{{ mr.maxReps }} reps @ {{ mr.weightAtMaxReps }} kg</span>
            </li>
            <li v-if="maxReps.length === 0" class="empty-state">No data yet</li>
          </ul>
        </div>
      </div>

      <div class="card">
        <h2>My Workouts</h2>
        <ul>
          <li v-for="workout in workouts" :key="workout.workoutId">
            <span>{{ formatDate(workout.workoutDate) }}</span>
            <span class="neutral" style="margin-left: auto;">{{ workout.duration }} min</span>
            <span class="badge badge-blue">{{ workout.feeling }}/5</span>
            <span v-if="workout.note" class="neutral">{{ workout.note }}</span>
          </li>
          <li v-if="workouts.length === 0" class="empty-state">No workouts yet</li>
        </ul>
      </div>

      <div class="card">
        <h2>Weight Trend</h2>
        <ul>
          <li v-for="trend in weightTrend" :key="trend.workoutDate + trend.exerciseName">
            <span>{{ trend.exerciseName }}</span>
            <span class="neutral">{{ formatDate(trend.workoutDate) }}</span>
            <span style="margin-left: auto;">{{ trend.weight }} kg</span>
            <span v-if="trend.difference > 0" class="positive">+{{ trend.difference }}</span>
            <span v-if="trend.difference < 0" class="negative">{{ trend.difference }}</span>
            <span v-if="trend.difference === 0" class="neutral">—</span>
          </li>
          <li v-if="weightTrend.length === 0" class="empty-state">No trend data yet</li>
        </ul>
      </div>

      <div class="card">
        <h2>Workout Volume</h2>
        <ul>
          <li v-for="vol in workoutVolume" :key="vol.workoutId">
            <span>{{ formatDate(vol.workoutDate) }}</span>
            <span class="badge badge-blue" style="margin-left: auto;">{{ vol.totalVolume }} kg total</span>
          </li>
          <li v-if="workoutVolume.length === 0" class="empty-state">No volume data yet</li>
        </ul>
      </div>

    </div>
  </div>
</template>