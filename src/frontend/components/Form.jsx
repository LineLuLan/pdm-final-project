// src/components/Form.jsx
import React from 'react';
import React from 'react';

export default function Form({ fields, values, onChange, onSubmit, submitLabel, errors }) {
  return (
    <form className="space-y-4" onSubmit={onSubmit}>
      {fields.map((field) => (
        <div key={field.key}>
          <label className="block mb-1 font-medium text-gray-700">
            {field.label}
            {field.required && <span className="text-red-500">*</span>}
          </label>
          {field.type === 'select' ? (
            <select
              className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-red-400"
              value={values[field.key] || ''}
              onChange={(e) => onChange(field.key, e.target.value)}
              required={field.required}
            >
              <option value="">Chọn...</option>
              {field.options.map((opt) => (
                <option key={opt.value} value={opt.value}>{opt.label}</option>
              ))}
            </select>
          ) : (
            <input
              className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-red-400"
              type={field.type || 'text'}
              value={values[field.key] || ''}
              onChange={(e) => onChange(field.key, e.target.value)}
              required={field.required}
              min={field.min}
              max={field.max}
            />
          )}
          {errors && errors[field.key] && (
            <div className="text-sm text-red-500 mt-1">{errors[field.key]}</div>
          )}
        </div>
      ))}
      <button
        type="submit"
        className="w-full bg-red-500 hover:bg-red-600 text-white font-semibold py-2 rounded shadow"
      >
        {submitLabel || 'Lưu'}
      </button>
    </form>
  );
}
