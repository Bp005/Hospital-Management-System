// src/services/appointmentService.js
import axiosInstance from '../api/axios';

const appointmentService = {
  // ============================================
  // PATIENT ENDPOINTS
  // ============================================
  
  // Get my appointments (as patient)
  getMyAppointments: async () => {
    const response = await axiosInstance.get('/api/v1/appointments/my');
    return response.data;
  },
  
  // Book new appointment (as patient)
  bookAppointment: async (appointmentData) => {
    const response = await axiosInstance.post('/api/v1/appointments/book', appointmentData);
    return response.data;
  },
  
  // Cancel my appointment
  cancelAppointment: async (id) => {
    const response = await axiosInstance.put(`/api/v1/appointments/${id}/cancel`);
    return response.data;
  },
  
  // ============================================
  // DOCTOR ENDPOINTS
  // ============================================
  
  // Get my appointments (as doctor)
  getMyDoctorAppointments: async () => {
    const response = await axiosInstance.get('/api/v1/appointments/doctor/my');
    return response.data;
  },
  
  // Mark appointment as completed
  completeAppointment: async (id) => {
    const response = await axiosInstance.put(`/api/v1/appointments/${id}/complete`);
    return response.data;
  },
  
  // ============================================
  // ADMIN ENDPOINTS
  // ============================================
  
  // Get all appointments (admin)
  getAllAppointments: async () => {
    const response = await axiosInstance.get('/api/v1/appointments');
    return response.data;
  },
  
  // Create appointment (admin/doctor)
  createAppointment: async (appointmentData) => {
    const response = await axiosInstance.post('/api/v1/appointments', appointmentData);
    return response.data;
  },
  
  // Update appointment
  updateAppointment: async (id, appointmentData) => {
    const response = await axiosInstance.put(`/api/v1/appointments/${id}`, appointmentData);
    return response.data;
  },
  
  // Delete appointment
  deleteAppointment: async (id) => {
    const response = await axiosInstance.delete(`/api/v1/appointments/${id}`);
    return response.data;
  },
};

export default appointmentService;