<template>
  <div v-if="show" class="loader-overlay">
    <div class="loader"></div>
  </div>
</template>

<script setup>
defineProps({
  show: {
    type: Boolean,
    required: true
  }
});
</script>

<style scoped>
/* Overlay */
.loader-overlay {
  position: fixed;
  z-index: 9999;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(33, 33, 33, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

/* From Uiverse.io by JaydipPrajapati1910 */
.loader {
  width: 48px;
  height: 48px;
  position: relative;
}

.loader:before {
  content: '';
  width: 48px;
  height: 5px;
  background: #6b6b6b;
  position: absolute;
  top: 60px;
  left: 0;
  border-radius: 50%;
  animation: shadow324 0.5s linear infinite;
}

.loader:after {
  content: '';
  width: 100%;
  height: 100%;
  background-color: #0c1a45;
  position: absolute;
  top: 0;
  left: 0;
  border-radius: 4px;
  animation: jump7456 0.5s linear infinite;
}

@keyframes jump7456 {
  15% {
    border-bottom-right-radius: 3px;
  }

  25% {
    transform: translateY(9px) rotate(22.5deg);
  }

  50% {
    transform: translateY(18px) scale(1, 0.9) rotate(45deg);
    border-bottom-right-radius: 40px;
  }

  75% {
    transform: translateY(9px) rotate(67.5deg);
  }

  100% {
    transform: translateY(0) rotate(90deg);
  }
}

@keyframes shadow324 {
  0%, 100% {
    transform: scale(1, 1);
  }

  50% {
    transform: scale(1.2, 1);
  }
}
</style>
