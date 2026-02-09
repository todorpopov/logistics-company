import React from 'react';
import './FormModal.css';

export interface ModalInputField {
  name: string;
  label: string;
  type: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export interface ConfigurableModalProps {
  isOpen: boolean;
  title?: string;
  inputFields: ModalInputField[];
  onClose: () => void;
  onSubmit: () => void;
  submitLabel?: string;
  closeLabel?: string;
}

const FormModal: React.FC<ConfigurableModalProps> = ({
  isOpen,
  title,
  inputFields,
  onClose,
  onSubmit,
  submitLabel = 'Submit',
  closeLabel = 'Close',
}) => {
  if (!isOpen) {
    return null ;
  }

  return (
    <div className="modal-backdrop">
      <div className="modal-content d-flex gap-4">
        <span>Generate Report for {title}</span>
        <form onSubmit={e => { e.preventDefault(); onSubmit(); }}>
          {inputFields.map((field) => (
            <div key={field.name} className="form-modal-input-wrapper">
              <input
                id={field.name}
                name={field.name}
                type={field.type}
                value={field.value}
                onChange={field.onChange}
                placeholder={field.label}
                className="form-modal-input"
                required
              />
            </div>
          ))}
          <div className="form-modal-actions">
            <button type="button" onClick={onClose} className="form-modal-btn-close">{closeLabel}</button>
            <button type="submit" className="form-modal-btn-submit">{submitLabel}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FormModal;
