import axios from 'axios'
import { useAuthStore } from '../stores/authStore'

const api = axios.create({
  baseURL: 'http://localhost:8080'  // adresa našeg Spring Boot backenda
})
//Umesto da pišeš http://localhost:8080/api/auth/login svaki put,
//pišeš samo /api/auth/login


// Interceptor — izvršava se pre svakog zahteva
api.interceptors.request.use((config) => {

    //funkcija koja se izvršava pre svakog zahteva
    //kao i JwtFilter na backendu, samo na frontendu

  const authStore = useAuthStore()
  
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  
  return config
})

export default api