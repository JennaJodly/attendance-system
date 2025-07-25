<template>
  <component :is="layoutComponent">
    <router-view />
  </component>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'

// Import both layouts
import AppSidebar from './components/AppSidebar.vue' // your full layout
import WelcomeView from './views/WelcomeView.vue'
const BlankLayout = { template: '<slot />' } 

const route = useRoute()

const layoutComponent = computed(() =>
  route.path === '/' ? WelcomeView : AppSidebar
)
</script>
