// src/pages/Donors.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';
import {
  getAllDonors,
  createDonor,
  updateDonor,
  deleteDonor,
} from '../services/api';

// Dummy data cho demo
const dummyDonors = [
  { donorId: 1, name: 'Nguyễn Văn A', gender: 'Nam', age: 28, bloodType: 'O+', weight: 65, phone: '0912345678' },
  { donorId: 2, name: 'Trần Thị B', gender: 'Nữ', age: 32, bloodType: 'A-', weight: 52, phone: '0987654321' },
];

const columns = [
  { key: 'donorId', label: 'ID' },
  { key: 'name', label: 'Tên' },
  { key: 'gender', label: 'Giới tính' },
  { key: 'age', label: 'Tuổi' },
  { key: 'bloodType', label: 'Nhóm máu' },
  { key: 'weight', label: 'Cân nặng (kg)' },
  { key: 'phone', label: 'SĐT' },
];

const formFields = [
  { key: 'name', label: 'Tên', required: true },
  { key: 'gender', label: 'Giới tính', required: true, type: 'select', options: [
    { value: 'Nam', label: 'Nam' },
    { value: 'Nữ', label: 'Nữ' },
    { value: 'Khác', label: 'Khác' },
  ] },
  { key: 'age', label: 'Tuổi', required: true, type: 'number', min: 18, max: 70 },
  { key: 'bloodType', label: 'Nhóm máu', required: true, type: 'select', options: [
    { value: 'A+', label: 'A+' },
    { value: 'A-', label: 'A-' },
    { value: 'B+', label: 'B+' },
    { value: 'B-', label: 'B-' },
    { value: 'AB+', label: 'AB+' },
    { value: 'AB-', label: 'AB-' },
    { value: 'O+', label: 'O+' },
    { value: 'O-', label: 'O-' },
  ] },
  { key: 'weight', label: 'Cân nặng (kg)', required: true, type: 'number', min: 40 },
  { key: 'phone', label: 'SĐT', required: true },
];

export default function Donors() {
  const [donors, setDonors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    // Sử dụng dummy data, có thể thay bằng API thực tế
    setDonors(dummyDonors);
    setLoading(false);
    // getAllDonors().then(res => {
    //   setDonors(res.data);
    //   setLoading(false);
    // });
  }, []);

  const handleAdd = () => {
    setEditing(null);
    setFormValues({});
    setErrors({});
    setModalOpen(true);
  };

  const handleEdit = (row) => {
    setEditing(row);
    setFormValues(row);
    setErrors({});
    setModalOpen(true);
  };

  const handleDelete = (row) => {
    if (window.confirm('Bạn chắc chắn muốn xoá người hiến máu này?')) {
      setDonors(donors.filter((d) => d.donorId !== row.donorId));
      // deleteDonor(row.donorId)
    }
  };

  const validate = (values) => {
    const errs = {};
    formFields.forEach((f) => {
      if (f.required && !values[f.key]) errs[f.key] = 'Bắt buộc';
      if (f.type === 'number') {
        if (f.min && values[f.key] < f.min) errs[f.key] = `Tối thiểu ${f.min}`;
        if (f.max && values[f.key] > f.max) errs[f.key] = `Tối đa ${f.max}`;
      }
    });
    return errs;
  };

  const handleFormChange = (key, value) => {
    setFormValues((prev) => ({ ...prev, [key]: value }));
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const errs = validate(formValues);
    if (Object.keys(errs).length > 0) {
      setErrors(errs);
      return;
    }
    if (editing) {
      setDonors(donors.map((d) => (d.donorId === editing.donorId ? { ...formValues, donorId: editing.donorId } : d)));
      // updateDonor(editing.donorId, formValues)
    } else {
      const newId = Math.max(...donors.map((d) => d.donorId), 0) + 1;
      setDonors([...donors, { ...formValues, donorId: newId }]);
      // createDonor(formValues)
    }
    setModalOpen(false);
  };

  const filtered = donors.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="bg-white rounded-xl shadow p-6 max-w-6xl mx-auto mt-4">
      <h2 className="text-2xl font-bold text-center text-red-600 mb-6">QUẢN LÝ NGƯỜI HIẾN MÁU</h2>
      <div className="flex flex-col md:flex-row gap-8">
        {/* Bảng danh sách bên trái */}
        <div className="flex-1">
          <div className="flex justify-between items-center mb-4">
            <span className="font-semibold text-gray-700">Danh sách người hiến máu</span>
            <button
              className="bg-gradient-to-r from-red-400 to-red-600 hover:from-red-500 hover:to-red-700 text-white font-semibold px-5 py-2 rounded shadow text-sm"
              onClick={handleAdd}
            >
              + Thêm mới
            </button>
          </div>
          <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm người hiến máu..." />
          <div className="mt-2">
            {loading ? (
              <Spinner />
            ) : (
              <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
            )}
          </div>
        </div>
        {/* Form nhập liệu bên phải */}
        {modalOpen && (
          <div className="w-full md:w-96">
            <Modal open={modalOpen} title={editing ? 'Sửa người hiến máu' : 'Thêm người hiến máu'} onClose={() => setModalOpen(false)}>
              <Form
                fields={formFields}
                values={formValues}
                onChange={handleFormChange}
                onSubmit={handleFormSubmit}
                errors={errors}
                submitLabel={editing ? 'Cập nhật' : 'Thêm mới'}
              />
            </Modal>
          </div>
        )}
      </div>
    </div>
  );
}

