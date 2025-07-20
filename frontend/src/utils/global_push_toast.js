// src/utils/global_push_toast.js

import { useToast } from "vue-toastification"

const toast = useToast()

export default function global_push_toast(type, message) {
  switch (type) {
    case 'success':
      toast.success(message)
      break
    case 'error':
      toast.error(message)
      break
    case 'info':
      toast.info(message)
      break
    case 'warning':
      toast.warning(message)
      break
    default:
      toast(message)
  }
}
