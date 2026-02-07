import React from 'react';
import './ReportsHome.css';
import FormModal, { ModalInputField } from '../../components/FormModal';
import { modalFieldsConfigMap, ModalFieldConfig } from './FormConfig';
import { API_URL } from '../../App';
import axiosInstance from '../../utils/axiosConfig';
import Toast from '../../components/toast/Toast';
import { useNavigate } from 'react-router-dom';
import { mapOfficeEmployees, mapShipments } from './reportMappers';

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

const USE_MOCK = true;

const ReportsHome: React.FC = () => {
  const [modalOpen, setModalOpen] = React.useState(false);
  const [modalTitle, setModalTitle] = React.useState('');
  const [modalFields, setModalFields] = React.useState<ModalInputField[]>([]);
  const [modalFieldValues, setModalFieldValues] = React.useState<Record<string, string>>({});
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);
  const navigate = useNavigate();

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
    let url = API_URL + '/api/report' + cards.find((card) => card.label === modalTitle)?.path;
    queryParams.forEach(param => { url = url + `/${param}`; });

    if (USE_MOCK) {
      const mockData = [
        {
          shipmentId: 1,
          senderId: 101,
          registeredById: 'E001',
          deliveryType: 'Express',
          deliveryOfficeId: 'O100',
          courierEmployeeId: 'C200',
          weightGram: '1500',
          price: '25.00',
          status: 'Delivered',
          sentDate: '2026-01-10',
          deliveredDate: '2026-01-12',
          clientPhoneNumber: '+1234567890',
        },
        {
          shipmentId: 2,
          senderId: 102,
          registeredById: 'E002',
          deliveryType: 'Standard',
          deliveryOfficeId: 'O101',
          courierEmployeeId: 'C201',
          weightGram: '2000',
          price: '18.50',
          status: 'In Transit',
          sentDate: '2026-01-13',
          deliveredDate: '',
          clientPhoneNumber: '+1987654321',
        },
        {
          shipmentId: 3,
          senderId: 103,
          registeredById: 'E003',
          deliveryType: 'Economy',
          deliveryOfficeId: 'O102',
          courierEmployeeId: 'C202',
          weightGram: '500',
          price: '10.00',
          status: 'Pending',
          sentDate: '2026-01-15',
          deliveredDate: '',
          clientPhoneNumber: '+1122334455',
        },
      ];
      setToast({ type: 'success', text: 'MOCKED Report generated successfully' });
      setModalOpen(false);
      setTimeout(() => {
        navigate('/report', { state: { reportData: mockData, reportTitle: modalTitle } });
      }, 1000);
      return;
    }

    axiosInstance.get(url)
      .then((response) => {
        setToast({ type: 'success', text: 'Report generated successfully' });
        let mappedData = response.data;
        if (modalTitle === 'Employees') {
          mappedData = mapOfficeEmployees(response.data);
        } else if (modalTitle === 'Registered Shipments' || modalTitle === 'Shipments Sent for Delivery' || modalTitle === 'Shipments Registered By' || modalTitle === 'Shipments Sent By' || modalTitle === 'Shipments Received By') {
          mappedData = mapShipments(response.data);
        }
        navigate('/report', { state: { reportData: mappedData, reportTitle: modalTitle } });
      })
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
