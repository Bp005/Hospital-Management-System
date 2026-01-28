// src/services/doctorService.js
import axiosInstance from '../api/axios';

const doctorService = {
  // Get all doctors (ADMIN only)
  getAllDoctors: async () => {
    const response = await axiosInstance.get('/api/v1/doctors');
    return response.data;
  },

  // Get doctor by ID (ADMIN only)
  getDoctorById: async (id) => {
    const response = await axiosInstance.get(`/api/v1/doctors/${id}`);
    return response.data;
  },
  
  // Get current doctor's profile (DOCTOR role)
  getMyProfile: async () => {
    const response = await axiosInstance.get('/api/v1/doctors/me');
    return response.data;
  },
  
  // Update current doctor's profile (DOCTOR role)
  updateMyProfile: async (profileData) => {
    const response = await axiosInstance.put('/api/v1/doctors/me', profileData);
    return response.data;
  },
  
  // NEW: Get available doctors for booking (ANY authenticated user)
  getAvailableDoctors: async () => {
    const response = await axiosInstance.get('/api/v1/doctors/available');
    return response.data;
  },
};

export default doctorService;