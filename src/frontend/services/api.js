// src/services/api.js
import axios from "axios";
import { API_BASE_URL } from "../constants";

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Donors
export const getAllDonors = async () => api.get("/donors");
export const createDonor = async (data) => api.post("/donors", data);
export const updateDonor = async (id, data) => api.put(`/donors/${id}`, data);
export const deleteDonor = async (id) => api.delete(`/donors/${id}`);

// Patients
export const getAllPatients = async () => api.get("/patients");
export const createPatient = async (data) => api.post("/patients", data);
export const updatePatient = async (id, data) => api.put(`/patients/${id}`, data);
export const deletePatient = async (id) => api.delete(`/patients/${id}`);

// Doctors
export const getAllDoctors = async () => api.get("/doctors");
export const createDoctor = async (data) => api.post("/doctors", data);
export const updateDoctor = async (id, data) => api.put(`/doctors/${id}`, data);
export const deleteDoctor = async (id) => api.delete(`/doctors/${id}`);

// BloodBanks
export const getAllBloodBanks = async () => api.get("/bloodbanks");
export const createBloodBank = async (data) => api.post("/bloodbanks", data);
export const updateBloodBank = async (id, data) => api.put(`/bloodbanks/${id}`, data);
export const deleteBloodBank = async (id) => api.delete(`/bloodbanks/${id}`);

// BloodStocks
export const getAllBloodStocks = async () => api.get("/bloodstocks");
export const createBloodStock = async (data) => api.post("/bloodstocks", data);
export const updateBloodStock = async (id, data) => api.put(`/bloodstocks/${id}`, data);
export const deleteBloodStock = async (id) => api.delete(`/bloodstocks/${id}`);

// DonateTimes
export const getAllDonateTimes = async () => api.get("/donatetimes");
export const createDonateTime = async (data) => api.post("/donatetimes", data);
export const updateDonateTime = async (id, data) => api.put(`/donatetimes/${id}`, data);
export const deleteDonateTime = async (id) => api.delete(`/donatetimes/${id}`);

// RequestTimes
export const getAllRequestTimes = async () => api.get("/requesttimes");
export const createRequestTime = async (data) => api.post("/requesttimes", data);
export const updateRequestTime = async (id, data) => api.put(`/requesttimes/${id}`, data);
export const deleteRequestTime = async (id) => api.delete(`/requesttimes/${id}`);

export default api;
