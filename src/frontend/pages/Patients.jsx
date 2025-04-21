// src/pages/Patients.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

// Dummy data demo
const dummyPatients = [
  { patientId: 1, name: 'Phạm Văn X', gender: 'Nam', age: 45, bloodType: 'B+', phone: '0911222333' },
  { patientId: 2, name: 'Lê Thị Y', gender: 'Nữ', age: 30, bloodType: 'A-', phone: '0988777666' },
];

const columns = [
  { key: 'patientId', label: 'ID' },
  { key: 'name', label: 'Tên' },
  { key: 'gender', label: 'Giới tính' },
  { key: 'age', label: 'Tuổi' },
  { key: 'bloodType', label: 'Nhóm máu' },
  { key: 'phone', label: 'SĐT' },
];

const formFields = [
  { key: 'name', label: 'Tên', required: true },
  { key: 'gender', label: 'Giới tính', required: true, type: 'select', options: [
    { value: 'Nam', label: 'Nam' },
    { value: 'Nữ', label: 'Nữ' },
    { value: 'Khác', label: 'Khác' },
  ] },
  { key: 'age', label: 'Tuổi', required: true, type: 'number', min: 0, max: 120 },
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
  { key: 'phone', label: 'SĐT', required: true },
];

export default function Patients() {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setPatients(dummyPatients);
    setLoading(false);
    // getAllPatients().then(res => { setPatients(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá bệnh nhân này?')) {
      setPatients(patients.filter((d) => d.patientId !== row.patientId));
      // deletePatient(row.patientId)
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
      setPatients(patients.map((d) => (d.patientId === editing.patientId ? { ...formValues, patientId: editing.patientId } : d)));
      // updatePatient(editing.patientId, formValues)
    } else {
      const newId = Math.max(...patients.map((d) => d.patientId), 0) + 1;
      setPatients([...patients, { ...formValues, patientId: newId }]);
      // createPatient(formValues)
    }
    setModalOpen(false);
  };

  const filtered = patients.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý bệnh nhân</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm bệnh nhân..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa bệnh nhân' : 'Thêm bệnh nhân'} onClose={() => setModalOpen(false)}>
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
