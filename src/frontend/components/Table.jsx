// src/components/Table.jsx
import React from 'react';
import React from 'react';

export default function Table({ columns, data, onEdit, onDelete }) {
  return (
    <div className="overflow-x-auto rounded shadow">
      <table className="min-w-full bg-white">
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.key} className="px-4 py-2 text-left bg-gray-100 text-gray-700 font-semibold border-b">
                {col.label}
              </th>
            ))}
            {(onEdit || onDelete) && <th className="px-4 py-2 bg-gray-100" />}
          </tr>
        </thead>
        <tbody>
          {data.length === 0 ? (
            <tr>
              <td colSpan={columns.length + 1} className="px-4 py-6 text-center text-gray-400">Không có dữ liệu</td>
            </tr>
          ) : (
            data.map((row, idx) => (
              <tr key={idx} className="hover:bg-red-50 transition-colors">
                {columns.map((col) => (
                  <td key={col.key} className="px-4 py-2 border-b text-sm">
                    {row[col.key]}
                  </td>
                ))}
                {(onEdit || onDelete) && (
                  <td className="px-4 py-2 border-b text-right space-x-2">
                    {onEdit && (
                      <button onClick={() => onEdit(row)} className="text-blue-600 hover:underline">Sửa</button>
                    )}
                    {onDelete && (
                      <button onClick={() => onDelete(row)} className="text-red-600 hover:underline">Xoá</button>
                    )}
                  </td>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
