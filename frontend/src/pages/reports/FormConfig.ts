import {ModalInputField} from '../../components/formModal/FormModal';

export interface ModalFieldConfig {
    name: string;
    label: string;
    type: string;
}

export const modalFieldsConfigMap: Record<string, ModalFieldConfig[]> = {
  'Employees': [],
  'Clients': [],
  'Registered Shipments': [],
  'Income for Period': [
    { name: 'startDate', label: 'Start', type: 'date' },
    { name: 'endDate', label: 'End', type: 'date' },
  ],
  'Shipments Sent for Delivery': [],
  'Shipments Registered By': [
    { name: 'officeEmployeeId', label: 'Employee Id', type: 'number' },
  ],
  'Shipments Sent By': [
    { name: 'clientId', label: 'Sender Id', type: 'number' },
  ],
  'Shipments Received By': [
    { name: 'clientId', label: 'Receiver Id', type: 'number' },
  ],
};
