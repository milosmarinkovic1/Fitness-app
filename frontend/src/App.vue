<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
  <div class="app-wrapper">
    <div class="app-content">
      <RouterView />
    </div>
    <AppFooter />
  </div>
</template>

<style>

html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

#app {
  height: 100%;
}

.app-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.app-content {
  flex: 1 0 auto;  /* gura footer na dno */
}

footer {
  flex-shrink: 0;  /* sprečava da se footer skupi */
}
</style>