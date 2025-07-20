import { getCurrentInstance } from 'vue';

export default function useGlobalUtils() {
  const { appContext } = getCurrentInstance();
  return appContext.config.globalProperties.$globalUtils;
}
