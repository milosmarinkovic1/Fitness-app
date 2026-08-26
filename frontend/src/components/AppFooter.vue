<template>
  <footer class="footer">
    <div class="footer-content">
      <span class="footer-brand">fitness app</span>
      <span class="footer-text">Track your progress, beat your records.</span>
      <span class="footer-copy">© 2026 Fitness app. All rights reserved.</span>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #141414;
  border-top: 1px solid #2a2a2a;
  padding: 2.5rem 2rem;
  margin-top: 4rem;
}

.footer-content {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.footer-brand {
  font-weight: 700;
  color: #4a9eff;
  font-size: 1.1rem;
}

.footer-text {
  color: #888;
  font-size: 1rem;
}

.footer-copy {
  color: #555;
  font-size: 0.9rem;
}
</style>