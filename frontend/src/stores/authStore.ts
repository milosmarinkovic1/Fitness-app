import { defineStore } from 'pinia' //za kreiranje store-a
import { ref, computed } from 'vue'

//computed prati reaktivne podatke (unutrašnje stanje) i 
// automatski izračunava novu vrednost čim se ti podaci promene.

export const useAuthStore = defineStore('auth', () => {
  //Kreira store sa imenom auth
  //useAuthStore je funkcija koju pozvaš u 
  //bilo kojoj komponenti da dobiješ pristup store-u.
  
  // State podaci koje čuvamo
  const token = ref<string | null>(localStorage.getItem('token')) 
  //proveri da li već postoji token u browseru
  const userEmail = ref<string | null>(localStorage.getItem('userEmail'))

  // Computed — izvedena vrednost , posmatra se promena tokena
  const isAuthenticated = computed(() => token.value !== null)

  // Actions — funkcije koje menjaju STATE podatke
  function login(newToken: string, email: string) {
    token.value = newToken
    userEmail.value = email
    localStorage.setItem('token', newToken)
    localStorage.setItem('userEmail', email)
  }

  function logout() {  // brise local storage
    token.value = null
    userEmail.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userEmail')
  }

  return { token, userEmail, isAuthenticated, login, logout }
})