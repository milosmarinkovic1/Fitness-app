import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import LoginView from '../views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../views/DashboardView.vue'),
      meta: { requiresAuth: true }  // zaštićena ruta
    },
    {
      path: '/',
      redirect: '/login'  // root ruta preusmeri na login
    },
    {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
    },
    {
    path: '/workout/new',
    name: 'workout-new',
    component: () => import('../views/WorkoutView.vue'),
    meta: { requiresAuth: true }
    },
    {
    path: '/measurements',
    name: 'measurements',
    component: () => import('../views/MeasurementsView.vue'),
    meta: { requiresAuth: true }
    },
    
  ]
})

// Route guard — proverava da li je korisnik ulogovan
router.beforeEach((to) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    return '/login'  // ako nije ulogovan, idi na login
  }
})

export default router