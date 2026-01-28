import axiosInstance from '../api/axios';

const authService = {
  // Register new user
  register: async (userData) => {
    const response = await axiosInstance.post('/auth/register', userData);
    return response.data;
  },

  // Login user
  login: async (credentials) => {
    const response = await axiosInstance.post('/auth/login', credentials);
    // Backend returns JWT token as a plain string
    return response.data;
  },
};

export default authService;
