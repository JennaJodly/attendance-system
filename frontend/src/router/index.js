import { createRouter, createWebHistory } from 'vue-router'
import WelcomeView from '../views/WelcomeView.vue'
import AttendanceView from '../views/AttendanceView.vue'
import Employee from '../components/employee.vue'


const routes = [
  { path: '/', component: WelcomeView },
  { path: '/attendance', component: AttendanceView },
  {path: '/employee', component: Employee}
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
