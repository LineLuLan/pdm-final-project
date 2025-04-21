// src/pages/Dashboard.jsx
import React from 'react';

export default function Dashboard() {
  // Dummy data
  const stats = [
    { label: 'Tổng người hiến máu', value: 320 },
    { label: 'Tổng bệnh nhân', value: 38 },
    { label: 'Tổng bác sĩ', value: 8 },
    { label: 'Tổng ngân hàng máu', value: 24 },
    { label: 'Tổng kho máu', value: 540 },
    { label: 'Lượt hiến máu', value: 410 },
    { label: 'Lượt nhận máu', value: 320 },
  ];
  const activities = [
    { type: 'Hiến máu', name: 'Nguyễn Văn A', date: '2025-04-20' },
    { type: 'Nhận máu', name: 'Trần Thị B', date: '2025-04-19' },
    { type: 'Hiến máu', name: 'Lê Văn C', date: '2025-04-18' },
    { type: 'Nhận máu', name: 'Phạm Thị D', date: '2025-04-17' },
  ];

  // Icon cho từng loại số liệu (demo, có thể thay bằng icon đẹp hơn)
  const statIcons = [
    <svg className="w-8 h-8 text-red-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M17 20h5v-2a4 4 0 0 0-3-3.87M9 20H4v-2a4 4 0 0 1 3-3.87M16 3.13a4 4 0 1 1-8 0 4 4 0 0 1 8 0z" /></svg>, // Người hiến máu
    <svg className="w-8 h-8 text-blue-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" /></svg>, // Bệnh nhân
    <svg className="w-8 h-8 text-emerald-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M6 18v-7a6 6 0 1 1 12 0v7" /><circle cx="12" cy="18" r="2" /></svg>, // Bác sĩ
    <svg className="w-8 h-8 text-yellow-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M3 10v10h18V10M4 10V7a8 8 0 0 1 16 0v3" /></svg>, // Ngân hàng máu
    <svg className="w-8 h-8 text-pink-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M12 2C12 2 7 7.5 7 12a5 5 0 0 0 10 0c0-4.5-5-10-5-10z" /></svg>, // Kho máu
    <svg className="w-8 h-8 text-indigo-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M20 12v7a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2v-7" /><rect width="20" height="5" x="2" y="7" rx="2" /><path d="M12 22V7" /></svg>, // Lượt hiến máu
    <svg className="w-8 h-8 text-cyan-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path d="M9 2h6a2 2 0 0 1 2 2v2H7V4a2 2 0 0 1 2-2zm0 4v2h6V6" /><rect width="16" height="16" x="4" y="6" rx="2" /></svg>, // Lượt nhận máu
  ];

  return (
    <div className="max-w-4xl mx-auto px-4">
      <h2 className="text-3xl font-bold text-center text-blue-700 mb-8 tracking-wide">TỔNG QUAN HỆ THỐNG</h2>
      {/* Taskbar: các card số liệu trên 1 hàng ngang, scroll ngang trên mobile */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-3 pb-2 mb-8 justify-center">
        {stats.map((stat, idx) => {
          return (
            <div
              key={idx}
              className="w-16 h-16 bg-white rounded-lg shadow flex flex-col items-center justify-center border hover:shadow-md transition gap-0.5 cursor-pointer"
            >
              {statIcons[idx] && (
                <div className="flex items-center justify-center w-5 h-5 mb-0.5">{React.cloneElement(statIcons[idx], { className: 'w-4 h-4' })}</div>
              )}
              <div className="text-xs font-bold text-gray-700 mb-0.5">{stat.value}</div>
              <div className="text-[10px] text-gray-500 text-center font-medium whitespace-nowrap">{stat.label}</div>
            </div>
          );
        })}
      </div>
      {/* Thông tin quan trọng */}
      <div className="flex flex-row gap-4 mb-6 overflow-x-auto">
        <div className="bg-blue-50 border border-blue-200 rounded-lg px-4 py-2 text-sm flex items-center min-w-[180px]">
          <span className="font-semibold text-blue-700 mr-2">Hiến máu hôm nay:</span>
          <span className="font-bold text-blue-900">5 lượt</span>
        </div>
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg px-4 py-2 text-sm flex items-center min-w-[200px]">
          <span className="font-semibold text-yellow-700 mr-2">Cảnh báo:</span>
          <span className="font-bold text-yellow-900">Kho máu O+ thấp!</span>
        </div>
        <div className="bg-green-50 border border-green-200 rounded-lg px-4 py-2 text-sm flex items-center min-w-[220px]">
          <span className="font-semibold text-green-700 mr-2">Thông báo:</span>
          <span className="font-bold text-green-900">Hệ thống hoạt động ổn định.</span>
        </div>
      </div>
      {/* Thông tin chi tiết: hoạt động gần đây */}
      <div className="bg-white rounded-xl shadow p-4">
        <h3 className="text-lg font-bold text-gray-800 mb-3">Hoạt động gần đây</h3>
        <ul className="divide-y divide-gray-100">
          {activities.map((act, idx) => (
            <li key={idx} className="flex items-center justify-between py-2 px-2">
              <span className={`px-3 py-1 rounded-full text-xs font-semibold ${act.type === 'Hiến máu' ? 'bg-green-100 text-green-700' : 'bg-blue-100 text-blue-700'}`}>{act.type}</span>
              <span className="font-medium text-gray-700 truncate max-w-[160px] md:max-w-xs">{act.name}</span>
              <span className="text-gray-400 text-xs font-mono">{act.date}</span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
