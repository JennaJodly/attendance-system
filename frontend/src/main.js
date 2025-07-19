import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import GlobalLoader from './components/GearLoader.vue'

const app = createApp(App)
app.component('GlobalLoader', GlobalLoader)
app.use(router)
app.use(vuetify)
app.mount('#app')
