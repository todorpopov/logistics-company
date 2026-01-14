import React from 'react';
import './ReportsHome.css';
import FormModal, { ModalInputField } from '../../components/FormModal';
import { modalFieldsConfigMap, ModalFieldConfig } from './FormConfig';
import {API_URL} from '../../App';
import axios from 'axios';
import Toast from '../../components/toast/Toast';

const cards = [
  { icon: 'bi bi-person-badge', label: 'Employees', path: '/employees' },
  { icon: 'bi bi-people', label: 'Clients', path: '/clients' },
  { icon: 'bi bi-cash-stack', label: 'Income for Period', path: '/total-gross-income-for-period' },
  { icon: 'bi bi-clipboard-check', label: 'Registered Shipments', path: '/registered-shipments' },
  { icon: 'bi bi-truck', label: 'Shipments Sent for Delivery', path: '/shipments-sent-for-delivery' },
  { icon: 'bi bi-pencil-square', label: 'Shipments Registered By', path: '/shipments-registered-by' },
  { icon: 'bi bi-person-up', label: 'Shipments Sent By', path: '/shipments-sent-by' },
  { icon: 'bi bi-person-down', label: 'Shipments Received By', path: '/shipments-received-by' },
];

const ReportsHome: React.FC = () => {
  const [modalOpen, setModalOpen] = React.useState(false);
  const [modalTitle, setModalTitle] = React.useState('');
  const [modalFields, setModalFields] = React.useState<ModalInputField[]>([]);
  const [modalFieldValues, setModalFieldValues] = React.useState<Record<string, string>>({});
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleFieldChange = (name: string, value: string) => {
    setModalFieldValues((prev) => ({ ...prev, [name]: value }));
    setModalFields((fields) =>
      fields.map((f) => (f.name === name ? { ...f, value } : f))
    );
  };

  const handleCardClick = (card: typeof cards[0]) => {
    setModalTitle(card.label);
    const configFields: ModalFieldConfig[] = modalFieldsConfigMap[card.label] || [];
    const fields: ModalInputField[] = configFields.map((field) => ({
      ...field,
      value: '',
      onChange: (e) => handleFieldChange(field.name, e.target.value),
    }));
    setModalFields(fields);
    setModalFieldValues({});
    setModalOpen(true);
  };

  const handleModalSubmit = async () => {
    const queryParams = Object.values(modalFieldValues);
    let url = API_URL + '/api/report';
    queryParams.forEach(param => { url = url + `/${param}`; });

    axios.get(url)
      .then(() => setToast({ type: 'success', text: 'Report generated successfully' }))
      .catch(() => setToast({ type: 'error', text: 'Error generating report' }))
      .finally(() => setModalOpen(false));
  };

  return (
    <div data-testid="reports-home-page" className="home-container">
      {toast && (
        <Toast type={toast.type} text={toast.text} onClose={() => setToast(null)} />
      )}
      <h3 className="home-title">Reports</h3>
      <div className="icon-row reports-grid">
        {cards.map((card) => (
          <div
            key={card.label}
            className="icon-feature-card clickable"
            tabIndex={0}
            role="button"
            onClick={() => handleCardClick(card)}
            style={{ minWidth: 180 }}
          >
            <i className={card.icon}></i>
            <hr className="icon-divider" />
            <div>{card.label}</div>
          </div>
        ))}
      </div>
      <FormModal
        isOpen={modalOpen}
        title={modalTitle}
        inputFields={modalFields}
        onClose={() => setModalOpen(false)}
        onSubmit={handleModalSubmit}
      />
    </div>
  );
};

export default ReportsHome;
