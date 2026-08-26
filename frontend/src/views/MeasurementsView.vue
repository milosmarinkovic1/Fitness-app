<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'  
import { useRouter } from 'vue-router'
import api from '../api/axios'


import { Chart, registerables } from 'chart.js'
Chart.register(...registerables)

// ============================================================
// STATE
// ============================================================
const measureDate = ref<string | null>(null)
const weight = ref<number | null>(null)
const bodyFat = ref<number | null>(null)
const muscleMass = ref<number | null>(null)
const waistCm = ref<number | null>(null)
const chestCm = ref<number | null>(null)
const armCm = ref<number | null>(null)
const legCm = ref<number | null>(null)
const note = ref('')
const isLoading = ref(false)
const error = ref('')
const editingId = ref<number | null>(null)

const measurements = ref<Array<{
  measurementId: number
  measureDate: string
  weight: number | null
  bodyFat: number | null
  muscleMass: number | null
  waistCm: number | null
  chestCm: number | null
  armCm: number | null
  legCm: number | null
  note: string | null
}>>([])

// ===== DODATO =====
const chartRef = ref<HTMLCanvasElement | null>(null)
let chartInstance: Chart | null = null

const router = useRouter()

// ============================================================
// METHODS
// ============================================================

// ===== DODATO =====
function createChart() {
  if (!chartRef.value) return

  const valid = measurements.value
    .filter(m => m.weight != null)
    .sort((a, b) => new Date(a.measureDate).getTime() - new Date(b.measureDate).getTime())

  if (valid.length === 0) return

  const labels = valid.map(m => {
    const d = new Date(m.measureDate)

    return d.toLocaleDateString('en-GB', { 
  year: 'numeric',
  month: 'short',
  day: 'numeric'
})
  })

  // @ts-ignore
  const data = valid.map(m => m.weight as number)

  if (chartInstance) {
    chartInstance.destroy()
  }

  chartInstance = new Chart(chartRef.value, {
    type: 'line',
    data: {
      labels: labels,
      datasets: [{
        label: 'Weight (kg)',
        data: data,
        borderColor: '#4a9eff',
        backgroundColor: 'rgba(74, 158, 255, 0.1)',
        fill: true,
        tension: 0.3,
        pointBackgroundColor: '#4a9eff',
        pointBorderColor: '#ffffff',
        pointBorderWidth: 2,
        pointRadius: 5
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          labels: { color: '#a0a0a0', font: { size: 14, weight: '600' as any } }
        }
        //as any je TypeScript sintaksa koja kaže prevodiocu:
        // "Ovu vrednost tretiraj kao bilo koji tip (any)
        // i preskoči sve provere tipova za ovu liniju.
      },
      scales: {
        x: {
          grid: { color: '#2a2a2a' },
          ticks: { color: '#888', maxTicksLimit: 10 }
        },
        y: {
          grid: { color: '#2a2a2a' },
          ticks: { color: '#888', callback: (v) => v + ' kg' as any }
        }
      }
    }
  })
}

async function fetchMeasurements() {
  try {
    const response = await api.get('/api/measurements')
    measurements.value = response.data
  } catch (err) {
    console.error('Error fetching measurements:', err)
  }
}

function clearForm() {
  measureDate.value = null
  weight.value = null
  bodyFat.value = null
  muscleMass.value = null
  waistCm.value = null
  chestCm.value = null
  armCm.value = null
  legCm.value = null
  note.value = ''
  editingId.value = null
}

function editMeasurement(measurement: any) {
  measureDate.value = measurement.measureDate
  weight.value = measurement.weight
  bodyFat.value = measurement.bodyFat
  muscleMass.value = measurement.muscleMass
  waistCm.value = measurement.waistCm
  chestCm.value = measurement.chestCm
  armCm.value = measurement.armCm
  legCm.value = measurement.legCm
  note.value = measurement.note || ''
  editingId.value = measurement.measurementId
  document.querySelector('.card:first-of-type')?.scrollIntoView({ behavior: 'smooth' })
}

async function deleteMeasurement(id: number) {
  if (!confirm('Delete this measurement?')) return
  try {
    await api.delete(`/api/measurements/${id}`)
    await fetchMeasurements()
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Error deleting measurement'
  }
}

async function handleSubmit() {
  if (!weight.value && !bodyFat.value && !muscleMass.value && 
      !waistCm.value && !chestCm.value && !armCm.value && !legCm.value) {
    error.value = 'Please enter at least one measurement value'
    return
  }

  isLoading.value = true
  error.value = ''

  const payload = {
    measureDate: measureDate.value || null,
    weight: weight.value,
    bodyFat: bodyFat.value,
    muscleMass: muscleMass.value,
    waistCm: waistCm.value,
    chestCm: chestCm.value,
    armCm: armCm.value,
    legCm: legCm.value,
    note: note.value || ''
  }

  try {
    if (editingId.value) {
      await api.put(`/api/measurements/${editingId.value}`, payload)
    } else {
      await api.post('/api/measurements', payload)
    }

    clearForm()
    await fetchMeasurements()
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Error saving measurement'
  } finally {
    isLoading.value = false
  }
}

function formatDate(dateString: string): string {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-GB', {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit'
})
}

// ===== DODATO =====
watch(measurements, () => {
  setTimeout(createChart, 100)
}, { deep: true })

// ============================================================
// LIFECYCLE
// ============================================================
onMounted(() => {
  fetchMeasurements()
  setTimeout(createChart, 200)  
})
</script>

<template>
  <div class="measurements-container">
    <div class="page-header">
      <h1>Body Measurements</h1>
      <RouterLink to="/dashboard" class="btn-secondary">Back to Dashboard</RouterLink>
    </div>

    <!-- ===== chart ===== -->
    <div class="card">
      <h2>Weight Progress</h2>
      <div v-if="measurements.filter(m => m.weight).length === 0" class="empty-state">
        No weight data yet. Log your first measurement!
      </div>
      <div v-else class="chart-container">
        <canvas ref="chartRef" class="chart-canvas"></canvas>
      </div>
    </div>

    <!-- Forma za unos / izmenu -->
    <div class="card">
      <h2>{{ editingId ? 'Edit Measurement' : 'Log New Measurement' }}</h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-grid">

          <div class="form-group full-width">
            <label for="measureDate">Date (optional)</label>
            <input id="measureDate" v-model="measureDate" type="date" />
            <small style="color: #888; font-size: 0.8rem; display: block; margin-top: 0.2rem;">
              Leave empty for today
            </small>
            <small style="color: #D46D59; font-size: 0.8rem; display: block; margin-top: 0.2rem;">
              Only one measurement per day is allowed.
            </small>
          </div>

          <div class="form-group">
            <label for="weight">Weight (kg)</label>
            <input id="weight" v-model="weight" type="number" step="0.1" placeholder="e.g. 75.5" />
          </div>

          <div class="form-group">
            <label for="bodyFat">Body Fat (%)</label>
            <input id="bodyFat" v-model="bodyFat" type="number" step="0.1" placeholder="e.g. 15.5" />
          </div>

          <div class="form-group">
            <label for="muscleMass">Muscle Mass (kg)</label>
            <input id="muscleMass" v-model="muscleMass" type="number" step="0.1" placeholder="e.g. 35.0" />
          </div>

          <div class="form-group">
            <label for="waistCm">Waist (cm)</label>
            <input id="waistCm" v-model="waistCm" type="number" step="0.1" placeholder="e.g. 80.0" />
          </div>

          <div class="form-group">
            <label for="chestCm">Chest (cm)</label>
            <input id="chestCm" v-model="chestCm" type="number" step="0.1" placeholder="e.g. 105.0" />
          </div>

          <div class="form-group">
            <label for="armCm">Arm (cm)</label>
            <input id="armCm" v-model="armCm" type="number" step="0.1" placeholder="e.g. 38.0" />
          </div>

          <div class="form-group">
            <label for="legCm">Leg (cm)</label>
            <input id="legCm" v-model="legCm" type="number" step="0.1" placeholder="e.g. 58.0" />
          </div>

          <div class="form-group full-width">
            <label for="note">Note (optional)</label>
            <textarea id="note" v-model="note" rows="2" placeholder="e.g. Morning measurement, fasted" />
          </div>
        </div>

        <div v-if="error" class="error">{{ error }}</div>

        <div style="display: flex; gap: 0.75rem; margin-top: 0.5rem;">
          <button type="submit" class="btn-primary" :disabled="isLoading">
            {{ isLoading ? 'Saving...' : editingId ? 'Update Measurement' : 'Save Measurement' }}
          </button>
          <button v-if="editingId" type="button" class="btn-secondary" @click="clearForm">
            Cancel
          </button>
        </div>
      </form>
    </div>

    <!-- Lista merenja -->
    <div class="card">
      <h2>Measurement History</h2>
      <div v-if="measurements.length === 0" class="empty-state">
        No measurements yet. Log your first one above!
      </div>
      <div v-else>
        <div class="measurement-list">
          <div
            v-for="m in measurements"
            :key="m.measurementId"
            class="measurement-item"
          >
            <div class="measurement-header">
              <span class="measurement-date">{{ formatDate(m.measureDate) }}</span>
              <span v-if="m.weight" class="badge badge-blue">
                {{ m.weight }} kg
              </span>
              <span v-if="m.bodyFat" class="badge badge-green">
                {{ m.bodyFat }}% BF
              </span>
              <span v-if="m.muscleMass" class="badge badge-purple">
                {{ m.muscleMass }} kg muscle
              </span>
              <div style="margin-left: auto; display: flex; gap: 0.4rem;">
                <button @click="editMeasurement(m)" class="btn-edit">Edit</button>
                <button @click="deleteMeasurement(m.measurementId)" class="btn-delete">Delete</button>
              </div>
            </div>
            <div class="measurement-details">
              <span v-if="m.waistCm">Waist: {{ m.waistCm }} cm</span>
              <span v-if="m.chestCm">Chest: {{ m.chestCm }} cm</span>
              <span v-if="m.armCm">Arm: {{ m.armCm }} cm</span>
              <span v-if="m.legCm">Leg: {{ m.legCm }} cm</span>
              <span v-if="m.note" class="measurement-note">{{ m.note }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.page-logo {
  width: 60px;
  height: 60px;
  display: block;
  margin: 0 auto 1.5rem auto;
}

.chart-container {
  width: 100%;
  min-height: 300px;
}
.chart-canvas {
  width: 100%;
  height: 300px;
}

.measurements-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem 1.5rem;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #2a2a2a;
}

.page-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: #ffffff;
  margin: 0;
}

.card {
  background-color: #1a1a1a;
  border: 1px solid #2a2a2a;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}

.card h2 {
  font-size: 1.3rem;
  font-weight: 600;
  color: #a0a0a0;
  margin-top: 0;
  margin-bottom: 1.2rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #2a2a2a;
}

/* ----- Forma ----- */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.form-group label {
  color: #888;
  font-size: 0.85rem;
  margin-bottom: 0.3rem;
}

.form-group input,
.form-group textarea {
  padding: 0.6rem 0.8rem;
  background-color: #111111;
  border: 1px solid #333;
  border-radius: 8px;
  color: #e0e0e0;
  font-size: 0.95rem;
  transition: border-color 0.2s;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #4a9eff;
}

.form-group input::placeholder,
.form-group textarea::placeholder {
  color: #555;
}

.form-group textarea {
  resize: vertical;
}

.error {
  grid-column: 1 / -1;
  color: #ff6b6b;
  padding: 0.6rem 1rem;
  background-color: #2a1a1a;
  border-radius: 6px;
  border-left: 3px solid #ff6b6b;
  margin: 0.5rem 0;
}

.btn-primary {
  padding: 0.8rem 1.5rem;
  background-color: #4a9eff;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
  flex: 1;
}

.btn-primary:hover:not(:disabled) {
  background-color: #2d7dd2;
}

.btn-primary:disabled {
  background-color: #2a2a2a;
  color: #555;
  cursor: not-allowed;
}

.btn-secondary {
  padding: 0.8rem 1.5rem;
  background-color: transparent;
  color: #aaa;
  border: 1px solid #555;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}

.btn-secondary:hover {
  background-color: #2a2a2a;
  color: #fff;
}

/* ----- Lista merenja ----- */
.measurement-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.measurement-item {
  background-color: #141414;
  border: 1px solid #2a2a2a;
  border-radius: 8px;
  padding: 0.8rem 1rem;
}

.measurement-header {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  flex-wrap: wrap;
}

.measurement-date {
  font-weight: 600;
  color: #e0e0e0;
}

.badge {
  display: inline-block;
  padding: 0.15rem 0.6rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.badge-blue {
  background-color: #1a2a3a;
  color: #4a9eff;
}

.badge-green {
  background-color: #1a3a1a;
  color: #4caf50;
}

.badge-purple {
  background-color: #2a1a3a;
  color: #b47aff;
}

.btn-edit,
.btn-delete {
  background: none;
  border: none;
  color: #888;
  cursor: pointer;
  font-size: 1rem;
  padding: 0.2rem 0.4rem;
  border-radius: 4px;
  transition: background 0.2s, color 0.2s;
}

.btn-edit:hover {
  background-color: #1a2a3a;
  color: #4a9eff;
}

.btn-delete:hover {
  background-color: #2a1a1a;
  color: #ff6b6b;
}

.measurement-details {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
  margin-top: 0.3rem;
  color: #888;
  font-size: 0.85rem;
}

.measurement-note {
  color: #666;
  font-style: italic;
  flex-basis: 100%;
}

.empty-state {
  text-align: center;
  color: #444;
  padding: 2rem 1rem;
  font-size: 0.95rem;
}

/* ----- Responsive ----- */
@media (max-width: 640px) {
  .form-grid {
    grid-template-columns: 1fr;
  }

  .measurements-container {
    padding: 1rem;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .page-header h1 {
    font-size: 1.5rem;
  }

  .measurement-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .measurement-header div:last-child {
    margin-left: 0 !important;
    align-self: flex-end;
  }
}

/* ----- Dark Modern Datepicker ----- */
input[type="date"] {
  background-color: #1a1a1a;
  color: #e0e0e0;
  border: 1px solid #444;
  border-radius: 8px;
  padding: 0.7rem 1rem;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: border-color 0.2s, box-shadow 0.2s;
}

input[type="date"]:hover {
  border-color: #7ab8ff;
}

input[type="date"]:focus {
  outline: none;
  border-color: #4a9eff;
  box-shadow: 0 0 0 3px rgba(74, 158, 255, 0.2);
}

/* Veća ikonica - tamna */
input[type="date"]::-webkit-calendar-picker-indicator {
  filter: invert(1);
  transform: scale(1.4);
  cursor: pointer;
}

input[type="date"]::-moz-calendar-picker-indicator {
  filter: invert(1);
  transform: scale(1.4);
  cursor: pointer;
}
</style>