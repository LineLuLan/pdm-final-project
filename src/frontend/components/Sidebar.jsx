// src/components/Sidebar.jsx
import React from 'react';
import { NAV_ITEMS } from '../constants';
import { NavLink } from 'react-router-dom';

const icons = {
  dashboard: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8v-10h-8v10zm0-18v6h8V3h-8z" /></svg>
  ),
  users: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M17 20h5v-2a4 4 0 0 0-3-3.87M9 20H4v-2a4 4 0 0 1 3-3.87M16 3.13a4 4 0 1 1-8 0 4 4 0 0 1 8 0z" /></svg>
  ),
  user: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" /></svg>
  ),
  stethoscope: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M6 18v-7a6 6 0 1 1 12 0v7" /><circle cx="12" cy="18" r="2" /></svg>
  ),
  bank: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M3 10v10h18V10M4 10V7a8 8 0 0 1 16 0v3" /></svg>
  ),
  drop: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M12 2C12 2 7 7.5 7 12a5 5 0 0 0 10 0c0-4.5-5-10-5-10z" /></svg>
  ),
  gift: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M20 12v7a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2v-7" /><rect width="20" height="5" x="2" y="7" rx="2" /><path d="M12 22V7" /></svg>
  ),
  clipboard: (
    <svg className="w-6 h-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M9 2h6a2 2 0 0 1 2 2v2H7V4a2 2 0 0 1 2-2zm0 4v2h6V6" /><rect width="16" height="16" x="4" y="6" rx="2" /></svg>
  ),
};

export default function Sidebar() {
  return (
    <aside className="bg-white shadow-md w-36 min-h-screen flex flex-col border-r">
      <div className="h-16 flex items-center justify-center border-b">
        <span className="font-bold text-lg tracking-wide text-red-600">PDM Admin</span>
      </div>
      <nav className="flex-1 px-2 py-4">
        <ul className="space-y-1">
          {NAV_ITEMS.map((item) => (
            <li key={item.path}>
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  `flex items-center gap-2 px-2.5 py-2 rounded-md hover:bg-red-50 transition-colors text-sm ${isActive ? 'bg-red-100 text-red-600 font-semibold' : 'text-gray-700'}`
                }
              >
                <span className="flex items-center justify-center w-4 h-4">{React.cloneElement(icons[item.icon], { className: 'w-4 h-4' })}</span>
                <span className="truncate">{item.name}</span>
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
}
