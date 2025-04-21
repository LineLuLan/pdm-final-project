// src/components/SearchBar.jsx
import React from 'react';

export default function SearchBar({ value, onChange, placeholder }) {
  return (
    <div className="mb-4 flex items-center">
      <input
        type="text"
        className="border border-gray-300 rounded px-3 py-2 w-full md:w-64 focus:outline-none focus:ring-2 focus:ring-red-400"
        placeholder={placeholder || "Tìm kiếm..."}
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}
