// src/composables/useToast.js

import { getCurrentInstance } from 'vue'

export default function useToast() {
  const { appContext } = getCurrentInstance()
  const $globalUtils = appContext.config.globalProperties.$globalUtils
  return $globalUtils.global_push_toast
}
