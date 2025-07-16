import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css' // Add this line to import mdi icons CSS
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

export default createVuetify({
  components,
  directives,
  // Add this icons config
  icons: {
    defaultSet: 'mdi',
  },
})
