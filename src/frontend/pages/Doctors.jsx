// src/pages/Doctors.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

// Dummy data demo
const dummyDoctors = [
  { doctorId: 1, name: 'Nguyễn Văn Bác', specialization: 'Huyết học', email: 'bacsy1@pdm.com', phone: '0911111111' },
  { doctorId: 2, name: 'Trần Thị Sĩ', specialization: 'Truyền máu', email: 'bacsy2@pdm.com', phone: '0922222222' },
];

const columns = [
  { key: 'doctorId', label: 'ID' },
  { key: 'name', label: 'Tên' },
  { key: 'specialization', label: 'Chuyên môn' },
  { key: 'email', label: 'Email' },
  { key: 'phone', label: 'SĐT' },
];

const formFields = [
  { key: 'name', label: 'Tên', required: true },
  { key: 'specialization', label: 'Chuyên môn', required: true },
  { key: 'email', label: 'Email', required: true, type: 'email' },
  { key: 'phone', label: 'SĐT', required: true },
];

export default function Doctors() {
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setDoctors(dummyDoctors);
    setLoading(false);
    // getAllDoctors().then(res => { setDoctors(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá bác sĩ này?')) {
      setDoctors(doctors.filter((d) => d.doctorId !== row.doctorId));
      // deleteDoctor(row.doctorId)
    }
  };

  const validate = (values) => {
    const errs = {};
    formFields.forEach((f) => {
      if (f.required && !values[f.key]) errs[f.key] = 'Bắt buộc';
      if (f.type === 'email' && values[f.key] && !/^\S+@\S+\.\S+$/.test(values[f.key])) {
        errs[f.key] = 'Email không hợp lệ';
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
      setDoctors(doctors.map((d) => (d.doctorId === editing.doctorId ? { ...formValues, doctorId: editing.doctorId } : d)));
      // updateDoctor(editing.doctorId, formValues)
    } else {
      const newId = Math.max(...doctors.map((d) => d.doctorId), 0) + 1;
      setDoctors([...doctors, { ...formValues, doctorId: newId }]);
      // createDoctor(formValues)
    }
    setModalOpen(false);
  };

  const filtered = doctors.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý bác sĩ</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm bác sĩ..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa bác sĩ' : 'Thêm bác sĩ'} onClose={() => setModalOpen(false)}>
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
  );
}
