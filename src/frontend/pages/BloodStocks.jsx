// src/pages/BloodStocks.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

const dummyBloodStocks = [
  { stockId: 1, bloodType: 'O+', quantity: 120, status: 'Available', expirationDate: '2025-05-20', storageLocation: 'Tủ A1' },
  { stockId: 2, bloodType: 'A-', quantity: 45, status: 'Reserved', expirationDate: '2025-04-30', storageLocation: 'Tủ B2' },
];

const columns = [
  { key: 'stockId', label: 'ID' },
  { key: 'bloodType', label: 'Nhóm máu' },
  { key: 'quantity', label: 'Số lượng (ml)' },
  { key: 'status', label: 'Trạng thái' },
  { key: 'expirationDate', label: 'Hạn dùng' },
  { key: 'storageLocation', label: 'Vị trí lưu trữ' },
];

const formFields = [
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
  { key: 'quantity', label: 'Số lượng (ml)', required: true, type: 'number', min: 0 },
  { key: 'status', label: 'Trạng thái', required: true, type: 'select', options: [
    { value: 'Available', label: 'Có sẵn' },
    { value: 'Reserved', label: 'Đã đặt' },
    { value: 'Expired', label: 'Hết hạn' },
  ] },
  { key: 'expirationDate', label: 'Hạn dùng', required: true, type: 'date' },
  { key: 'storageLocation', label: 'Vị trí lưu trữ' },
];

export default function BloodStocks() {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setStocks(dummyBloodStocks);
    setLoading(false);
    // getAllBloodStocks().then(res => { setStocks(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá kho máu này?')) {
      setStocks(stocks.filter((d) => d.stockId !== row.stockId));
      // deleteBloodStock(row.stockId)
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
      setStocks(stocks.map((d) => (d.stockId === editing.stockId ? { ...formValues, stockId: editing.stockId } : d)));
      // updateBloodStock(editing.stockId, formValues)
    } else {
      const newId = Math.max(...stocks.map((d) => d.stockId), 0) + 1;
      setStocks([...stocks, { ...formValues, stockId: newId }]);
      // createBloodStock(formValues)
    }
    setModalOpen(false);
  };

  const filtered = stocks.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý kho máu</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm kho máu..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa kho máu' : 'Thêm kho máu'} onClose={() => setModalOpen(false)}>
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
