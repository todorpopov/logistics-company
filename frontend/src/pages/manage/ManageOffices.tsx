import React, { useState } from 'react';
import TableDemo from '../../components/table/TableDemo';
import './ManageOffices.css';
import { Action, ACTIONS } from './Actions';

const CreateOfficeForm: React.FC = () => {
  const [officeName, setOfficeName] = useState('');
  const [officeAddress, setOfficeAddress] = useState('');
  const [errors, setErrors] = useState<{ officeName?: string; officeAddress?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { officeName?: string; officeAddress?: string } = {};
    if (!officeName.trim()) {
      newErrors.officeName = 'Office name is required';
    }
    if (!officeAddress.trim()) {
      newErrors.officeAddress = 'Address is required';
    }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };

  return (
    <form className="p-4 rounded shadow bg-white" onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Create Office</h5>
      <div className="mb-3 text-start">
        <label htmlFor="officeName" className="form-label">Office Name</label>
        <input
          type="text"
          className={`form-control${errors.officeName ? ' is-invalid' : ''}`}
          id="officeName"
          placeholder="Enter office name"
          value={officeName}
          onChange={e => setOfficeName(e.target.value)}
        />
        {errors.officeName && <div className="invalid-feedback text-start">{errors.officeName}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="officeAddress" className="form-label">Address</label>
        <input
          type="text"
          className={`form-control${errors.officeAddress ? ' is-invalid' : ''}`}
          id="officeAddress"
          placeholder="Enter location"
          value={officeAddress}
          onChange={e => setOfficeAddress(e.target.value)}
        />
        {errors.officeAddress && <div className="invalid-feedback text-start">{errors.officeAddress}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Create</button>
    </form>
  );
};

const UpdateOfficeForm: React.FC = () => {
  const [officeId, setOfficeId] = useState('');
  const [officeName, setOfficeName] = useState('');
  const [officeAddress, setOfficeAddress] = useState('');
  const [errors, setErrors] = useState<{ officeId?: string; officeName?: string; officeAddress?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { officeId?: string; officeName?: string; officeAddress?: string } = {};
    if (!officeId.trim()) {
      newErrors.officeId = 'Office ID is required';
    }
    if (!officeName.trim()) {
      newErrors.officeName = 'Office name is required';
    }
    if (!officeAddress.trim()) {
      newErrors.officeAddress = 'Address is required';
    }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };

  return (
    <form className="p-4 rounded shadow bg-white" onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Update Office</h5>
      <div className="mb-3 text-start">
        <label htmlFor="officeId" className="form-label">Office ID</label>
        <input
          type="text"
          className={`form-control${errors.officeId ? ' is-invalid' : ''}`}
          id="officeId"
          placeholder="Enter office ID"
          value={officeId}
          onChange={e => setOfficeId(e.target.value)}
        />
        {errors.officeId && <div className="invalid-feedback text-start">{errors.officeId}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="officeName" className="form-label">Office Name</label>
        <input
          type="text"
          className={`form-control${errors.officeName ? ' is-invalid' : ''}`}
          id="officeName"
          placeholder="Enter office name"
          value={officeName}
          onChange={e => setOfficeName(e.target.value)}
        />
        {errors.officeName && <div className="invalid-feedback text-start">{errors.officeName}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="officeAddress" className="form-label">Address</label>
        <input
          type="text"
          className={`form-control${errors.officeAddress ? ' is-invalid' : ''}`}
          id="officeAddress"
          placeholder="Enter location"
          value={officeAddress}
          onChange={e => setOfficeAddress(e.target.value)}
        />
        {errors.officeAddress && <div className="invalid-feedback text-start">{errors.officeAddress}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Update</button>
    </form>
  );
};

const DeleteOfficeForm: React.FC = () => {
  const [officeId, setOfficeId] = useState('');
  const [errors, setErrors] = useState<{ officeId?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { officeId?: string } = {};
    if (!officeId.trim()) {
      newErrors.officeId = 'Office ID is required';
    }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };

  return (
    <form className="p-4 rounded shadow bg-white" onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Delete Office</h5>
      <div className="mb-3 text-start">
        <label htmlFor="officeId" className="form-label">Office ID</label>
        <input
          type="text"
          className={`form-control${errors.officeId ? ' is-invalid' : ''}`}
          id="officeId"
          placeholder="Enter office ID"
          value={officeId}
          onChange={e => setOfficeId(e.target.value)}
        />
        {errors.officeId && <div className="invalid-feedback text-start">{errors.officeId}</div>}
      </div>
      <button type="submit" className="btn btn-danger w-100">Delete</button>
    </form>
  );
};

const ManageOffices: React.FC = () => {
  const [selectedAction, setSelectedAction] = useState<Action>(Action.Read);

  let content: React.ReactNode;
  switch (selectedAction) {
  case Action.Read:
    content = <TableDemo />;
    break;
  case Action.Create:
    content = <CreateOfficeForm />;
    break;
  case Action.Update:
    content = <UpdateOfficeForm />;
    break;
  case Action.Delete:
    content = <DeleteOfficeForm />;
    break;
  default:
    content = null;
  }

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-actions">
        {ACTIONS.map(action => (
          <button
            key={action}
            className={`btn btn-${selectedAction === action ? 'primary' : 'outline-primary'} w-100`}
            onClick={() => setSelectedAction(action)}
          >
            {action.charAt(0).toUpperCase() + action.slice(1)}
          </button>
        ))}
      </div>
      <div className="manage-offices-content">
        {content}
      </div>
    </div>
  );
};

export default ManageOffices;
