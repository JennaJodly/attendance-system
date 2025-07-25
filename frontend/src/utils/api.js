import axios from 'axios';

// Environment-based API URL configuration
const getApiBaseUrl = () => {
  // For Vite/Vue 3
  if (import.meta.env) {
    return import.meta.env.VITE_API_URL || import.meta.env.VUE_APP_API_URL || 'http://localhost:9090';
  }
  
  // For Vue CLI/webpack
  if (process.env.VUE_APP_API_URL) {
    return process.env.VUE_APP_API_URL;
  }
  
  // Fallback for local development
  return 'http://localhost:9090';
};

const API_BASE_URL = getApiBaseUrl();

console.log('Environment:', process.env.NODE_ENV);
console.log('API BASE URL:', API_BASE_URL);
console.log('All env vars:', {
  VUE_APP_API_URL: process.env.VUE_APP_API_URL,
  VITE_API_URL: import.meta.env?.VITE_API_URL,
  NODE_ENV: process.env.NODE_ENV
});

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000, // 30 seconds for Render cold starts
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: false, // Set to true only if you need cookies
});

// Request interceptor
apiClient.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method?.toUpperCase()} request to: ${config.baseURL}${config.url}`);
    
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error('Request interceptor error:', error);
    return Promise.reject(error);
  }
);

// Response interceptor
apiClient.interceptors.response.use(
  (response) => {
    console.log('API Response:', response.status, response.config.url);
    return response;
  },
  (error) => {
    console.error('API Error Details:', {
      url: error.config?.url,
      method: error.config?.method,
      status: error.response?.status,
      statusText: error.response?.statusText,
      data: error.response?.data,
      message: error.message
    });
    
    // Handle specific error cases
    if (error.response?.status === 403) {
      console.error('CORS or Authentication error - check your backend CORS configuration');
    }
    
    return Promise.reject(error);
  }
);

export default apiClient;
export { API_BASE_URL };