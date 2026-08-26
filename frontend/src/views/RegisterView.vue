<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import api from '../api/axios'

const fullName = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const isLoading = ref(false)
const error = ref('')

const router = useRouter()
const authStore = useAuthStore()

async function handleRegister() {
  // Validacija
  if (fullName.value.trim().length < 2) {
    error.value = 'Full name must be at least 2 characters'
    return
  }

  if (!email.value.includes('@')) {
    error.value = 'Please enter a valid email address'
    return
  }

  if (password.value.length < 6) {
    error.value = 'Password must be at least 6 characters'
    return
  }

  if (password.value !== confirmPassword.value) {
    error.value = 'Passwords do not match'
    return
  }

  isLoading.value = true
  error.value = ''

  try {
    await api.post('/api/auth/register', {
      fullName: fullName.value.trim(),
      email: email.value.trim(),
      password: password.value
    })

    // Preusmeri na login
    router.push('/login')
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Registration failed'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="register-container">

    <img src="/favicon.svg" alt="Logo" class="auth-logo" />

    <h2>Create Account</h2>

    <form @submit.prevent="handleRegister">
      <div v-if="error" class="error">{{ error }}</div>

      <input
        v-model="fullName"
        type="text"
        placeholder="Full Name"
        required
      />

      <input
        v-model="email"
        type="email"
        placeholder="Email"
        required
      />

      <input
        v-model="password"
        type="password"
        placeholder="Password"
        required
      />

      <input
        v-model="confirmPassword"
        type="password"
        placeholder="Confirm Password"
        required
      />

      <button type="submit" :disabled="isLoading">
        {{ isLoading ? 'Creating account...' : 'Sign Up' }}
      </button>

      <p>
        Already have an account?
        <RouterLink to="/login">Sign In</RouterLink>
      </p>
    </form>
  </div>
</template>

<style scoped>

.auth-logo {
  width: 80px;
  height: 80px;
  display: block;
  margin: 0 auto 1.5rem auto;
}

.register-container {
  max-width: 480px;
  margin: 4rem auto;
  padding: 3rem;
  background-color: #1a1a1a;
  border: 1px solid #2a2a2a;
  border-radius: 16px;
}

.register-container h2 {
  font-size: 2.4rem;
  font-weight: 700;
  color: #ffffff;
  text-align: center;
  margin-bottom: 2.5rem;
  border: none;
  text-transform: none;
  letter-spacing: 0;
}

.register-container input {
  width: 100%;
  padding: 1rem 1.2rem;
  margin-bottom: 1.2rem;
  background-color: #111111;
  border: 1px solid #333;
  border-radius: 10px;
  color: #f0f0f0;
  font-size: 1.15rem;
  transition: border-color 0.2s, box-shadow 0.2s;
  box-sizing: border-box;
}

.register-container input::placeholder {
  color: #555;
  font-size: 1.05rem;
}

.register-container input:focus {
  outline: none;
  border-color: #4a9eff;
  box-shadow: 0 0 0 3px rgba(74, 158, 255, 0.2);
}

.error {
  color: #ff6b6b;
  font-size: 1rem;
  margin-bottom: 1rem;
  padding: 0.8rem 1.2rem;
  background-color: #2a1a1a;
  border-radius: 8px;
  border-left: 3px solid #ff6b6b;
}

.register-container button[type="submit"] {
  width: 100%;
  padding: 1rem;
  margin-top: 0.8rem;
  background-color: #4a9eff;
  color: #fff;
  border: none;
  border-radius: 10px;
  font-size: 1.2rem;
  font-weight: 700;
  cursor: pointer;
  transition: background 0.2s, transform 0.1s;
}

.register-container button[type="submit"]:hover:not(:disabled) {
  background-color: #2d7dd2;
}

.register-container button[type="submit"]:active {
  transform: scale(0.98);
}

.register-container button[type="submit"]:disabled {
  background-color: #2a2a2a;
  color: #555;
  cursor: not-allowed;
}

.register-container p {
  text-align: center;
  margin-top: 1.8rem;
  color: #a0a0a0;
  font-size: 1.05rem;
}

.register-container a {
  color: #4a9eff;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
  font-size: 1.05rem;
}

.register-container a:hover {
  color: #7ab8ff;
  text-decoration: underline;
}
</style>