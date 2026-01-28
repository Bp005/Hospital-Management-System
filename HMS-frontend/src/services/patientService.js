// src/services/patientService.js
import axiosInstance from '../api/axios';

const patientService = {
  // Get all patients (ADMIN, DOCTOR)
  getAllPatients: async () => {
    const response = await axiosInstance.get('/api/v1/patients');
    return response.data;
  },

  // Get patient by ID (ADMIN, DOCTOR)
  getPatientById: async (id) => {
    const response = await axiosInstance.get(`/api/v1/patients/${id}`);
    return response.data;
  },

  // Create new patient (ADMIN, DOCTOR)
  createPatient: async (patientData) => {
    const response = await axiosInstance.post('/api/v1/patients', patientData);
    return response.data;
  },

  // Update patient (ADMIN, DOCTOR)
  updatePatient: async (id, patientData) => {
    const response = await axiosInstance.put(`/api/v1/patients/${id}`, patientData);
    return response.data;
  },

  // Delete patient (ADMIN, DOCTOR)
  deletePatient: async (id) => {
    const response = await axiosInstance.delete(`/api/v1/patients/${id}`);
    return response.data;
  },

  // **NEW** - Get current patient's profile (PATIENT role)
  getMyProfile: async () => {
    const response = await axiosInstance.get('/api/v1/patients/me');
    return response.data;
  },

  // **NEW** - Update current patient's profile (PATIENT role)
  updateMyProfile: async (profileData) => {
    const response = await axiosInstance.put('/api/v1/patients/me', profileData);
    return response.data;
  },
};

export default patientService;