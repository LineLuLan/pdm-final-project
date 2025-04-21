// src/components/Modal.jsx
import React from 'react';
import React from 'react';

export default function Modal({ open, title, children, onClose }) {
  if (!open) return null;
  return (
    <div className="fixed inset-0 z-40 flex items-center justify-center bg-black bg-opacity-30">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-lg mx-2">
        <div className="flex justify-between items-center px-6 py-4 border-b">
          <h2 className="font-bold text-lg text-gray-800">{title}</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-red-500 text-xl">&times;</button>
        </div>
        <div className="p-6">{children}</div>
      </div>
    </div>
  );
}
