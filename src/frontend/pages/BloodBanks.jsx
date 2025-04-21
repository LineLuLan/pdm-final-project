// src/pages/BloodBanks.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

const dummyBloodBanks = [
  { bid: 1, name: 'Ngân hàng máu Trung Ương', address: 'Hà Nội', contactEmail: 'info@nbm.vn', contactPhone: '0241234567' },
  { bid: 2, name: 'Ngân hàng máu Chợ Rẫy', address: 'TP HCM', contactEmail: 'info@choray.vn', contactPhone: '0289876543' },
];

const columns = [
  { key: 'bid', label: 'ID' },
  { key: 'name', label: 'Tên ngân hàng' },
  { key: 'address', label: 'Địa chỉ' },
  { key: 'contactEmail', label: 'Email' },
  { key: 'contactPhone', label: 'SĐT' },
];

const formFields = [
  { key: 'name', label: 'Tên ngân hàng', required: true },
  { key: 'address', label: 'Địa chỉ', required: true },
  { key: 'contactEmail', label: 'Email', type: 'email' },
  { key: 'contactPhone', label: 'SĐT' },
];

export default function BloodBanks() {
  const [banks, setBanks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setBanks(dummyBloodBanks);
    setLoading(false);
    // getAllBloodBanks().then(res => { setBanks(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá ngân hàng máu này?')) {
      setBanks(banks.filter((d) => d.bid !== row.bid));
      // deleteBloodBank(row.bid)
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
      setBanks(banks.map((d) => (d.bid === editing.bid ? { ...formValues, bid: editing.bid } : d)));
      // updateBloodBank(editing.bid, formValues)
    } else {
      const newId = Math.max(...banks.map((d) => d.bid), 0) + 1;
      setBanks([...banks, { ...formValues, bid: newId }]);
      // createBloodBank(formValues)
    }
    setModalOpen(false);
  };

  const filtered = banks.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý ngân hàng máu</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm ngân hàng máu..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa ngân hàng máu' : 'Thêm ngân hàng máu'} onClose={() => setModalOpen(false)}>
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
