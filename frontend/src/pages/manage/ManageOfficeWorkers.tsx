import React, { useState } from 'react';
import './ManageOffices.css';

enum Action {
  Read = 'read',
  Create = 'create',
  Update = 'update',
  Delete = 'delete',
}

const ACTIONS = [Action.Read, Action.Create, Action.Update, Action.Delete];

const CreateOfficeWorkerForm: React.FC = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [officeId, setOfficeId] = useState('');
  const [errors, setErrors] = useState<{ firstName?: string; lastName?: string; officeId?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { firstName?: string; lastName?: string; officeId?: string } = {};
    if (!firstName.trim()) { newErrors.firstName = 'First name is required'; }
    if (!lastName.trim()) { newErrors.lastName = 'Last name is required'; }
    if (!officeId.trim()) { newErrors.officeId = 'Office ID is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Create Office Worker</h5>
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
      <div className="mb-3 text-start">
        <label htmlFor="officeId" className="form-label">Office ID</label>
        <input type="text" className={`form-control${errors.officeId ? ' is-invalid' : ''}`} id="officeId" value={officeId} onChange={e => setOfficeId(e.target.value)} />
        {errors.officeId && <div className="invalid-feedback text-start">{errors.officeId}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Create</button>
    </form>
  );
};

const UpdateOfficeWorkerForm: React.FC = () => {
  const [workerId, setWorkerId] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [officeId, setOfficeId] = useState('');
  const [errors, setErrors] = useState<{ workerId?: string; firstName?: string; lastName?: string; officeId?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { workerId?: string; firstName?: string; lastName?: string; officeId?: string } = {};
    if (!workerId.trim()) { newErrors.workerId = 'Worker ID is required'; }
    if (!firstName.trim()) { newErrors.firstName = 'First name is required'; }
    if (!lastName.trim()) { newErrors.lastName = 'Last name is required'; }
    if (!officeId.trim()) { newErrors.officeId = 'Office ID is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Update Office Worker</h5>
      <div className="mb-3 text-start">
        <label htmlFor="workerId" className="form-label">Worker ID</label>
        <input type="text" className={`form-control${errors.workerId ? ' is-invalid' : ''}`} id="workerId" value={workerId} onChange={e => setWorkerId(e.target.value)} />
        {errors.workerId && <div className="invalid-feedback text-start">{errors.workerId}</div>}
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
      <div className="mb-3 text-start">
        <label htmlFor="officeId" className="form-label">Office ID</label>
        <input type="text" className={`form-control${errors.officeId ? ' is-invalid' : ''}`} id="officeId" value={officeId} onChange={e => setOfficeId(e.target.value)} />
        {errors.officeId && <div className="invalid-feedback text-start">{errors.officeId}</div>}
      </div>
      <button type="submit" className="btn btn-primary w-100">Update</button>
    </form>
  );
};

const DeleteOfficeWorkerForm: React.FC = () => {
  const [workerId, setWorkerId] = useState('');
  const [errors, setErrors] = useState<{ workerId?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { workerId?: string } = {};
    if (!workerId.trim()) { newErrors.workerId = 'Worker ID is required'; }
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Delete Office Worker</h5>
      <div className="mb-3 text-start">
        <label htmlFor="workerId" className="form-label">Worker ID</label>
        <input type="text" className={`form-control${errors.workerId ? ' is-invalid' : ''}`} id="workerId" value={workerId} onChange={e => setWorkerId(e.target.value)} />
        {errors.workerId && <div className="invalid-feedback text-start">{errors.workerId}</div>}
      </div>
      <button type="submit" className="btn btn-danger w-100">Delete</button>
    </form>
  );
};

const ManageOfficeWorkers: React.FC = () => {
  const [selectedAction, setSelectedAction] = useState<Action>(Action.Read);

  let content: React.ReactNode;
  switch (selectedAction) {
  case Action.Read:
    content = <div>Table for office workers goes here</div>;
    break;
  case Action.Create:
    content = <CreateOfficeWorkerForm />;
    break;
  case Action.Update:
    content = <UpdateOfficeWorkerForm />;
    break;
  case Action.Delete:
    content = <DeleteOfficeWorkerForm />;
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

export default ManageOfficeWorkers;

