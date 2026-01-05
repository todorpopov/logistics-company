import React, { useState } from 'react';
import './ManageOffices.css';

enum Action {
  Read = 'read',
  Create = 'create',
  Update = 'update',
  Delete = 'delete',
}

const ACTIONS = [Action.Read, Action.Create, Action.Update, Action.Delete];

const CreateCourierForm: React.FC = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [errors, setErrors] = useState<{ firstName?: string; lastName?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { firstName?: string; lastName?: string } = {};
    if (!firstName.trim()) { newErrors.firstName = 'First name is required'; }
    if (!lastName.trim()) { newErrors.lastName = 'Last name is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Create Courier</h5>
      <div className="mb-3 text-start">
        <label htmlFor="firstName" className="form-label">First Name</label>
        <input type="text" className={`form-control${errors.firstName ? ' is-invalid' : ''}`} id="firstName" value={firstName} onChange={e => setFirstName(e.target.value)} />
        {errors.firstName && <div className="invalid-feedback text-start">{errors.firstName}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="lastName" className="form-label">Last Name</label>
        <input type="text" className={`form-control${errors.lastName ? ' is-invalid' : ''}`} id="lastName" value={lastName} onChange={e => setLastName(e.target.value)} />
        {errors.lastName && <div className="invalid-feedback text-start">{errors.lastName}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Create</button>
    </form>
  );
};

const UpdateCourierForm: React.FC = () => {
  const [courierId, setCourierId] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [errors, setErrors] = useState<{ courierId?: string; firstName?: string; lastName?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { courierId?: string; firstName?: string; lastName?: string } = {};
    if (!courierId.trim()) { newErrors.courierId = 'Courier ID is required'; }
    if (!firstName.trim()) { newErrors.firstName = 'First name is required'; }
    if (!lastName.trim()) { newErrors.lastName = 'Last name is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Update Courier</h5>
      <div className="mb-3 text-start">
        <label htmlFor="courierId" className="form-label">Courier ID</label>
        <input type="text" className={`form-control${errors.courierId ? ' is-invalid' : ''}`} id="courierId" value={courierId} onChange={e => setCourierId(e.target.value)} />
        {errors.courierId && <div className="invalid-feedback text-start">{errors.courierId}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="firstName" className="form-label">First Name</label>
        <input type="text" className={`form-control${errors.firstName ? ' is-invalid' : ''}`} id="firstName" value={firstName} onChange={e => setFirstName(e.target.value)} />
        {errors.firstName && <div className="invalid-feedback text-start">{errors.firstName}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="lastName" className="form-label">Last Name</label>
        <input type="text" className={`form-control${errors.lastName ? ' is-invalid' : ''}`} id="lastName" value={lastName} onChange={e => setLastName(e.target.value)} />
        {errors.lastName && <div className="invalid-feedback text-start">{errors.lastName}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Update</button>
    </form>
  );
};

const DeleteCourierForm: React.FC = () => {
  const [courierId, setCourierId] = useState('');
  const [errors, setErrors] = useState<{ courierId?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { courierId?: string } = {};
    if (!courierId.trim()) { newErrors.courierId = 'Courier ID is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Delete Courier</h5>
      <div className="mb-3 text-start">
        <label htmlFor="courierId" className="form-label">Courier ID</label>
        <input type="text" className={`form-control${errors.courierId ? ' is-invalid' : ''}`} id="courierId" value={courierId} onChange={e => setCourierId(e.target.value)} />
        {errors.courierId && <div className="invalid-feedback text-start">{errors.courierId}</div>}
      </div>
      <button type="submit" className="btn btn-danger w-100">Delete</button>
    </form>
  );
};

const ManageCouriers: React.FC = () => {
  const [selectedAction, setSelectedAction] = useState<Action>(Action.Read);

  let content: React.ReactNode;
  switch (selectedAction) {
  case Action.Read:
    content = <div>Table for couriers goes here</div>;
    break;
  case Action.Create:
    content = <CreateCourierForm />;
    break;
  case Action.Update:
    content = <UpdateCourierForm />;
    break;
  case Action.Delete:
    content = <DeleteCourierForm />;
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

export default ManageCouriers;

