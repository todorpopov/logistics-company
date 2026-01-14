import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import { useGetOfficeEmployees } from './request';
import { useQueryClient } from '@tanstack/react-query';
import {API_URL} from '../../App';
import axios from 'axios';
import Toast from '../../components/toast/Toast';

export interface OfficeEmployee {
  officeEmployeeId: number;
  email: string;
  firstName: string;
  lastName: string;
  officeId: number;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const officeEmployeeColumns: Column<OfficeEmployee>[] = [
  { header: 'ID', accessor: 'officeEmployeeId', editable: false },
  { header: 'Email', accessor: 'email' },
  { header: 'First Name', accessor: 'firstName', mandatoryForCreation: true },
  { header: 'Last Name', accessor: 'lastName', mandatoryForCreation: true },
  { header: 'Office ID', accessor: 'officeId', mandatoryForCreation: true },
];

const ManageOfficeEmployees: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: officeEmployees } = useGetOfficeEmployees();
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleCreate = (employee: OfficeEmployee) => {
    axios.post(`${API_URL}/api/user/office-employee`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, officeId: employee.officeId, password: employee.lastName })
      .then(() => {
        setToast({ type: 'success', text: 'Employee added successfully' });
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error adding employee' });
      });
  };

  const handleEdit = (employee: OfficeEmployee) => {
    axios.put(`${API_URL}/api/user/office-employee/${employee.officeEmployeeId}`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, officeId: employee.officeId })
      .then(() => {
        setToast({ type: 'success', text: 'Employee updated successfully' });
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error updating employee' });
      });
  };

  const handleDelete = (employee: OfficeEmployee) => {
    axios.delete(`${API_URL}/api/user/office-employee/${employee.officeEmployeeId}`)
      .then(() => {
        setToast({ type: 'success', text: 'Employee deleted successfully' });
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error deleting employee' });
      });
  };

  return (
    <div className="manage-container">
      {toast && (
        <Toast type={toast.type} text={toast.text} onClose={() => setToast(null)} />
      )}
      <div className="manage-content">
        <Table
          config={config}
          columns={officeEmployeeColumns}
          data={officeEmployees ?? []}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageOfficeEmployees;
