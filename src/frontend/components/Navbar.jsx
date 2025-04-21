// src/components/Navbar.jsx
import React from 'react';

import { NavLink } from "react-router-dom";
import { NAV_ITEMS } from "../constants";

export default function Navbar({ user }) {
  return (
    <header className="h-[60px] px-4 flex items-center justify-between bg-gradient-to-r from-white via-blue-50 to-white border-b shadow-sm" style={{ minHeight: 60 }}>
      <div className="flex items-center w-full max-w-5xl mx-auto justify-between">
        <div className="flex items-center gap-4">
          <img
            src="/assets/logo.png"
            alt="Logo"
            className="w-10 h-auto block object-contain mr-2"
            style={{ maxWidth: '40px', objectFit: 'contain' }}
          />
          <h1 className="text-xl font-extrabold text-blue-700 tracking-wide whitespace-nowrap drop-shadow">PDM</h1>
        </div>
        <nav className="flex items-center gap-4">
          {NAV_ITEMS.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `px-3 py-1 rounded font-semibold text-base transition ${isActive ? 'bg-blue-200 text-blue-800 shadow' : 'text-gray-700 hover:bg-blue-50'}`
              }
              end={item.path === "/"}
            >
              {item.name}
            </NavLink>
          ))}
        </nav>
        <div className="flex items-center space-x-2">
          {user && <span className="text-gray-600 font-medium hidden sm:inline">Xin chào, {user.name}</span>}
          <img
            src="https://ui-avatars.com/api/?name=Admin&background=ef4444&color=fff&size=32"
            alt="User Avatar"
            className="w-8 h-8 rounded-full border"
          />
        </div>
      </div>
    </header>
  );
}

