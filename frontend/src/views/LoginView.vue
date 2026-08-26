<script setup lang="ts">

import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import api from '../api/axios'

const email = ref('')
const password = ref('')
const router = useRouter()
const authStore = useAuthStore()

async function handleLogin() { // async - da se izvrsava u pozadini asinhrono
  // POST na backend sa email i password
  // await - čeka da se zahtev završi pa nastavi!!
  // await pauzira funkciju dok Axios ne dobije odgovor sa servera
  const response = await api.post('/api/auth/login', {
    email: email.value,
    password: password.value
  })
  
  //čuva token u store i localStorage
  authStore.login(response.data.token, email.value)
  //backend vraća {"token":"eyJ..."}
  
  //preusmeri korisnika na dashboard
  router.push('/dashboard')
}

</script>
  
  <template>
  <div class="login-container">

    <img src="/favicon.svg" alt="Logo" class="auth-logo" />

    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="email" type="email" placeholder="Email" />
      <input v-model="password" type="password" placeholder="Password" />
      <button type="submit">Sign in</button>
      <p>No account? <RouterLink to="/register">Register</RouterLink></p>
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


.login-container {
  max-width: 480px;
  margin: 4rem auto;
  padding: 3rem;
  background-color: #1a1a1a;
  border: 1px solid #2a2a2a;
  border-radius: 16px;
}

.login-container h2 {
  font-size: 2.4rem;
  font-weight: 700;
  color: #ffffff;
  text-align: center;
  margin-bottom: 2.5rem;
  border: none;
  text-transform: none;
  letter-spacing: 0;
}

.login-container input {
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

.login-container input::placeholder {
  color: #555;
  font-size: 1.05rem;
}

.login-container input:focus {
  outline: none;
  border-color: #4a9eff;
  box-shadow: 0 0 0 3px rgba(74, 158, 255, 0.2);
}

.login-container button[type="submit"] {
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

.login-container button[type="submit"]:hover {
  background-color: #2d7dd2;
}

.login-container button[type="submit"]:active {
  transform: scale(0.98);
}

.login-container p {
  text-align: center;
  margin-top: 1.8rem;
  color: #a0a0a0;
  font-size: 1.05rem;
}

.login-container a {
  color: #4a9eff;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
  font-size: 1.05rem;
}

.login-container a:hover {
  color: #7ab8ff;
  text-decoration: underline;
}
</style>