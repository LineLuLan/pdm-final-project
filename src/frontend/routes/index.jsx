// src/routes/index.jsx
import React, { Suspense, lazy } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Spinner from '../components/Spinner';

const LoginPage = lazy(() => import('../pages/Login'));
const Dashboard = lazy(() => import('../pages/Dashboard'));
const Donors = lazy(() => import('../pages/Donors'));
const Patients = lazy(() => import('../pages/Patients'));
const Doctors = lazy(() => import('../pages/Doctors'));
const BloodBanks = lazy(() => import('../pages/BloodBanks'));
const BloodStocks = lazy(() => import('../pages/BloodStocks'));
const DonateTimes = lazy(() => import('../pages/DonateTimes'));
const RequestTimes = lazy(() => import('../pages/RequestTimes'));

export default function AppRoutes() {
  return (
    <Suspense fallback={<Spinner />}>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<Dashboard />} />
        <Route path="/donors" element={<Donors />} />
        <Route path="/patients" element={<Patients />} />
        <Route path="/doctors" element={<Doctors />} />
        <Route path="/bloodbanks" element={<BloodBanks />} />
        <Route path="/bloodstocks" element={<BloodStocks />} />
        <Route path="/donatetimes" element={<DonateTimes />} />
        <Route path="/requesttimes" element={<RequestTimes />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Suspense>
  );
}
