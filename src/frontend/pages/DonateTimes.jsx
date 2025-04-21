// src/pages/DonateTimes.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

const dummyDonateTimes = [
  { donationId: 1, bid: 1, donorId: 1, donationDate: '2025-04-15', quantity: 350, notes: 'Hiến máu toàn phần' },
  { donationId: 2, bid: 2, donorId: 2, donationDate: '2025-04-18', quantity: 450, notes: 'Hiến tiểu cầu' },
];

const columns = [
  { key: 'donationId', label: 'ID' },
  { key: 'bid', label: 'Ngân hàng máu' },
  { key: 'donorId', label: 'Người hiến' },
  { key: 'donationDate', label: 'Ngày hiến' },
  { key: 'quantity', label: 'Số lượng (ml)' },
  { key: 'notes', label: 'Ghi chú' },
];

const formFields = [
  { key: 'bid', label: 'Ngân hàng máu (ID)', required: true, type: 'number', min: 1 },
  { key: 'donorId', label: 'Người hiến (ID)', required: true, type: 'number', min: 1 },
  { key: 'donationDate', label: 'Ngày hiến', required: true, type: 'date' },
  { key: 'quantity', label: 'Số lượng (ml)', required: true, type: 'number', min: 100 },
  { key: 'notes', label: 'Ghi chú' },
];

export default function DonateTimes() {
  const [donateTimes, setDonateTimes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setDonateTimes(dummyDonateTimes);
    setLoading(false);
    // getAllDonateTimes().then(res => { setDonateTimes(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá lượt hiến máu này?')) {
      setDonateTimes(donateTimes.filter((d) => d.donationId !== row.donationId));
      // deleteDonateTime(row.donationId)
    }
  };

  const validate = (values) => {
    const errs = {};
    formFields.forEach((f) => {
      if (f.required && !values[f.key]) errs[f.key] = 'Bắt buộc';
      if (f.type === 'number') {
        if (f.min && values[f.key] < f.min) errs[f.key] = `Tối thiểu ${f.min}`;
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
      setDonateTimes(donateTimes.map((d) => (d.donationId === editing.donationId ? { ...formValues, donationId: editing.donationId } : d)));
      // updateDonateTime(editing.donationId, formValues)
    } else {
      const newId = Math.max(...donateTimes.map((d) => d.donationId), 0) + 1;
      setDonateTimes([...donateTimes, { ...formValues, donationId: newId }]);
      // createDonateTime(formValues)
    }
    setModalOpen(false);
  };

  const filtered = donateTimes.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý lượt hiến máu</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm lượt hiến máu..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa lượt hiến máu' : 'Thêm lượt hiến máu'} onClose={() => setModalOpen(false)}>
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
