import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'

// Components
import GlobalLoader from './components/GearLoader.vue'

// Toast
import Toast, { POSITION } from 'vue-toastification'
import 'vue-toastification/dist/index.css'

// Global functions
import global_push_toast from './utils/global_push_toast'
import globalUtils from './utils/globalUtils'

const app = createApp(App)

// Register plugins
app.use(router)
app.use(vuetify)
app.use(Toast, {
  position: POSITION.TOP_RIGHT,
  timeout: 3000,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
})

// Register global component
app.component('GlobalLoader', GlobalLoader)

// ✅ Register global properties (functions)
app.config.globalProperties.$globalToast = app.config.globalProperties.$toast
app.config.globalProperties.$globalUtils = {
  ...globalUtils,
  global_push_toast
}

app.mount('#app')
