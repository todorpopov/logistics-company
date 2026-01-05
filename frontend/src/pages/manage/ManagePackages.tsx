import React, { useState } from 'react';
import './ManageOffices.css';

enum Action {
  Read = 'read',
  Create = 'create',
  Update = 'update',
  Delete = 'delete',
}

const ACTIONS = [Action.Read, Action.Create, Action.Update, Action.Delete];

const CreatePackageForm: React.FC = () => {
  const [sender, setSender] = useState('');
  const [registeredBy, setRegisteredBy] = useState('');
  const [deliveryType, setDeliveryType] = useState('office');
  const [officeId, setOfficeId] = useState('');
  const [deliveredBy, setDeliveredBy] = useState('');
  const [weight, setWeight] = useState('');
  const [price, setPrice] = useState('');
  const [shipmentStatus, setShipmentStatus] = useState('');
  const [sentDate, setSentDate] = useState('');
  const [deliveredDate, setDeliveredDate] = useState('');
  const [errors, setErrors] = useState<{ sender?: string; registeredBy?: string; deliveryType?: string; officeId?: string; deliveredBy?: string; weight?: string; price?: string; shipmentStatus?: string; sentDate?: string; deliveredDate?: string }>({});
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: typeof errors = {};
    if (!sender.trim()) { newErrors.sender = 'Sender is required'; }
    if (!registeredBy.trim()) { newErrors.registeredBy = 'Registered by is required'; }
    if (!deliveryType) { newErrors.deliveryType = 'Delivery type is required'; }
    if (!officeId.trim()) { newErrors.officeId = 'Office ID is required'; }
    if (!deliveredBy.trim()) { newErrors.deliveredBy = 'Delivered by is required'; }
    if (!weight.trim()) { newErrors.weight = 'Weight is required'; }
    if (!price.trim()) { newErrors.price = 'Price is required'; }
    if (!shipmentStatus.trim()) { newErrors.shipmentStatus = 'Shipment status is required'; }
    if (!sentDate.trim()) { newErrors.sentDate = 'Sent date is required'; }
    // deliveredDate can be optional if not delivered yet
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      // Submit logic here
    }
  };
  return (
    <form onSubmit={handleSubmit} noValidate>
      <h5 className="mb-4 text-capitalize">Create Package</h5>
      <div className="mb-3 text-start">
        <label htmlFor="sender" className="form-label">Sender</label>
        <input type="text" className={`form-control${errors.sender ? ' is-invalid' : ''}`} id="sender" value={sender} onChange={e => setSender(e.target.value)} />
        {errors.sender && <div className="invalid-feedback text-start">{errors.sender}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="registeredBy" className="form-label">Registered By (Employee)</label>
        <input type="text" className={`form-control${errors.registeredBy ? ' is-invalid' : ''}`} id="registeredBy" value={registeredBy} onChange={e => setRegisteredBy(e.target.value)} />
        {errors.registeredBy && <div className="invalid-feedback text-start">{errors.registeredBy}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="deliveryType" className="form-label">Delivery Type</label>
        <select className={`form-select${errors.deliveryType ? ' is-invalid' : ''}`} id="deliveryType" value={deliveryType} onChange={e => setDeliveryType(e.target.value)}>
          <option value="office">Office</option>
          <option value="address">Address</option>
        </select>
        {errors.deliveryType && <div className="invalid-feedback text-start">{errors.deliveryType}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="officeId" className="form-label">Office ID</label>
        <input type="text" className={`form-control${errors.officeId ? ' is-invalid' : ''}`} id="officeId" value={officeId} onChange={e => setOfficeId(e.target.value)} />
        {errors.officeId && <div className="invalid-feedback text-start">{errors.officeId}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="deliveredBy" className="form-label">Delivered By</label>
        <input type="text" className={`form-control${errors.deliveredBy ? ' is-invalid' : ''}`} id="deliveredBy" value={deliveredBy} onChange={e => setDeliveredBy(e.target.value)} />
        {errors.deliveredBy && <div className="invalid-feedback text-start">{errors.deliveredBy}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="weight" className="form-label">Weight</label>
        <input type="text" className={`form-control${errors.weight ? ' is-invalid' : ''}`} id="weight" value={weight} onChange={e => setWeight(e.target.value)} />
        {errors.weight && <div className="invalid-feedback text-start">{errors.weight}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="price" className="form-label">Price</label>
        <input type="text" className={`form-control${errors.price ? ' is-invalid' : ''}`} id="price" value={price} onChange={e => setPrice(e.target.value)} />
        {errors.price && <div className="invalid-feedback text-start">{errors.price}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="shipmentStatus" className="form-label">Shipment Status</label>
        <input type="text" className={`form-control${errors.shipmentStatus ? ' is-invalid' : ''}`} id="shipmentStatus" value={shipmentStatus} onChange={e => setShipmentStatus(e.target.value)} />
        {errors.shipmentStatus && <div className="invalid-feedback text-start">{errors.shipmentStatus}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="sentDate" className="form-label">Sent Date</label>
        <input type="date" className={`form-control${errors.sentDate ? ' is-invalid' : ''}`} id="sentDate" value={sentDate} onChange={e => setSentDate(e.target.value)} />
        {errors.sentDate && <div className="invalid-feedback text-start">{errors.sentDate}</div>}
      </div>
      <div className="mb-3 text-start">
        <label htmlFor="deliveredDate" className="form-label">Delivered Date</label>
        <input type="date" className="form-control" id="deliveredDate" value={deliveredDate} onChange={e => setDeliveredDate(e.target.value)} />
      </div>
      <button type="submit" className="btn btn-primary w-100">Create</button>
    </form>
  );
};

const UpdatePackageForm: React.FC = () => {
  return <div>Update form for package goes here</div>;
};

const DeletePackageForm: React.FC = () => {
  return <div>Delete form for package goes here</div>;
};

const ManagePackages: React.FC = () => {
  const [selectedAction, setSelectedAction] = useState<Action>(Action.Read);

  let content: React.ReactNode;
  switch (selectedAction) {
  case Action.Read:
    content = <div>Table for packages goes here</div>;
    break;
  case Action.Create:
    content = <CreatePackageForm />;
    break;
  case Action.Update:
    content = <UpdatePackageForm />;
    break;
  case Action.Delete:
    content = <DeletePackageForm />;
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

export default ManagePackages;

