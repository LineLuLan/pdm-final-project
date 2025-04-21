// src/pages/RequestTimes.jsx
import React, { useEffect, useState } from 'react';
import Table from '../components/Table';
import Modal from '../components/Modal';
import Form from '../components/Form';
import Spinner from '../components/Spinner';
import SearchBar from '../components/SearchBar';

const dummyRequestTimes = [
  { requestId: 1, bid: 1, did: 1, patientId: 1, requestDate: '2025-04-10', bloodType: 'O+', quantity: 250, urgency: 'Khẩn cấp', status: 'Đã duyệt' },
  { requestId: 2, bid: 2, did: 2, patientId: 2, requestDate: '2025-04-14', bloodType: 'A-', quantity: 300, urgency: 'Bình thường', status: 'Chờ xử lý' },
];

const columns = [
  { key: 'requestId', label: 'ID' },
  { key: 'bid', label: 'Ngân hàng máu' },
  { key: 'did', label: 'Bác sĩ' },
  { key: 'patientId', label: 'Bệnh nhân' },
  { key: 'requestDate', label: 'Ngày yêu cầu' },
  { key: 'bloodType', label: 'Nhóm máu' },
  { key: 'quantity', label: 'Số lượng (ml)' },
  { key: 'urgency', label: 'Mức độ' },
  { key: 'status', label: 'Trạng thái' },
];

const formFields = [
  { key: 'bid', label: 'Ngân hàng máu (ID)', required: true, type: 'number', min: 1 },
  { key: 'did', label: 'Bác sĩ (ID)', required: true, type: 'number', min: 1 },
  { key: 'patientId', label: 'Bệnh nhân (ID)', required: true, type: 'number', min: 1 },
  { key: 'requestDate', label: 'Ngày yêu cầu', required: true, type: 'date' },
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
  { key: 'quantity', label: 'Số lượng (ml)', required: true, type: 'number', min: 100 },
  { key: 'urgency', label: 'Mức độ', required: true, type: 'select', options: [
    { value: 'Khẩn cấp', label: 'Khẩn cấp' },
    { value: 'Bình thường', label: 'Bình thường' },
  ] },
  { key: 'status', label: 'Trạng thái', type: 'select', options: [
    { value: 'Chờ xử lý', label: 'Chờ xử lý' },
    { value: 'Đã duyệt', label: 'Đã duyệt' },
    { value: 'Từ chối', label: 'Từ chối' },
  ] },
];

export default function RequestTimes() {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formValues, setFormValues] = useState({});
  const [errors, setErrors] = useState({});
  const [search, setSearch] = useState('');

  useEffect(() => {
    setRequests(dummyRequestTimes);
    setLoading(false);
    // getAllRequestTimes().then(res => { setRequests(res.data); setLoading(false); })
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
    if (window.confirm('Bạn chắc chắn muốn xoá yêu cầu này?')) {
      setRequests(requests.filter((d) => d.requestId !== row.requestId));
      // deleteRequestTime(row.requestId)
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
      setRequests(requests.map((d) => (d.requestId === editing.requestId ? { ...formValues, requestId: editing.requestId } : d)));
      // updateRequestTime(editing.requestId, formValues)
    } else {
      const newId = Math.max(...requests.map((d) => d.requestId), 0) + 1;
      setRequests([...requests, { ...formValues, requestId: newId }]);
      // createRequestTime(formValues)
    }
    setModalOpen(false);
  };

  const filtered = requests.filter((d) =>
    Object.values(d).join(' ').toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-red-600">Quản lý yêu cầu nhận máu</h2>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded shadow"
          onClick={handleAdd}
        >
          Thêm mới
        </button>
      </div>
      <SearchBar value={search} onChange={setSearch} placeholder="Tìm kiếm yêu cầu..." />
      {loading ? (
        <Spinner />
      ) : (
        <Table columns={columns} data={filtered} onEdit={handleEdit} onDelete={handleDelete} />
      )}
      <Modal open={modalOpen} title={editing ? 'Sửa yêu cầu' : 'Thêm yêu cầu'} onClose={() => setModalOpen(false)}>
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
