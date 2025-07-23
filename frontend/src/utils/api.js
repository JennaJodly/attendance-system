import axios from 'axios';

// const API_BASE_URL = import.meta.env.VUE_APP_API_URL || '';
const API_BASE_URL = process.env.VUE_APP_API_URL || '';

console.log('API BASE URL:', process.env.VUE_APP_API_URL);


const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000, // 30 seconds for Render cold starts
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export default apiClient;
export { API_BASE_URL };