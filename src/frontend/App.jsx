// src/App.jsx
import React, { useState } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Navbar from './components/Navbar';
import AppRoutes from './routes';
import Toast from './components/Toast';

export default function App() {
  const [toast, setToast] = useState(null);
  const [user] = useState({ name: 'Admin' }); // Giả lập user

  const showToast = (type, message) => {
    setToast({ type, message });
  };

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Navbar user={user} />
        <main className="max-w-5xl mx-auto p-4 md:p-8">
          {/* Truyền showToast cho các page nếu cần */}
          <AppRoutes showToast={showToast} />
        </main>
      </div>
      {toast && (
        <Toast
          type={toast.type}
          message={toast.message}
          onClose={() => setToast(null)}
        />
      )}
    </Router>
  );
}
