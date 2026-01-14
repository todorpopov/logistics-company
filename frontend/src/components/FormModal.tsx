import React from 'react';

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
    <div className="modal-backdrop" style={{ position: 'fixed', top: 0, left: 0, width: '100vw', height: '100vh', background: 'rgba(0,0,0,0.3)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <div className="modal-content d-flex gap-4" style={{ background: '#fff', borderRadius: 8, padding: 32, minWidth: 320, maxWidth: 400, boxShadow: '0 2px 16px rgba(0,0,0,0.2)' }}>
        <span>Generate Report for {title}</span>
        <form onSubmit={e => { e.preventDefault(); onSubmit(); }}>
          {inputFields.map((field) => (
            <div key={field.name} style={{ marginBottom: 16 }}>
              <input
                id={field.name}
                name={field.name}
                type={field.type}
                value={field.value}
                onChange={field.onChange}
                placeholder={field.label}
                style={{ width: '100%', padding: 8, borderRadius: 4, border: '1px solid #ccc' }}
                required
              />
            </div>
          ))}
          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: 12, marginTop: 24 }}>
            <button type="button" onClick={onClose} style={{ padding: '8px 16px', borderRadius: 4, border: 'none', background: '#ccc' }}>{closeLabel}</button>
            <button type="submit" style={{ padding: '8px 16px', borderRadius: 4, border: 'none', background: '#0d6efd', color: '#fff' }}>{submitLabel}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FormModal;
