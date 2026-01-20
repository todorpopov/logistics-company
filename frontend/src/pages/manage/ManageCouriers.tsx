import { useQueryClient } from '@tanstack/react-query';
import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import { useGetCourierEmployees } from '../request';
import {API_URL} from '../../App';
import Toast from '../../components/toast/Toast';
import axiosInstance from '../../utils/axiosConfig';

export interface CourierEmployee {
  courierEmployeeId: number;
  email: string;
  firstName: string;
  lastName: string;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const courierEmployeeColumns: Column<CourierEmployee>[] = [
  { header: 'ID', accessor: 'courierEmployeeId', editable: false },
  { header: 'Email', accessor: 'email', mandatoryForCreation: true },
  { header: 'First Name', accessor: 'firstName', mandatoryForCreation: true },
  { header: 'Last Name', accessor: 'lastName', mandatoryForCreation: true },
];

const ManageCouriers: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: couriers } = useGetCourierEmployees();
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleCreate = (employee: CourierEmployee) => {
    axiosInstance.post(`${API_URL}/api/user/courier-employee`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, password: employee.email })
      .then(() => {
        setToast({ type: 'success', text: 'Employee added successfully' });
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error adding employee' });
      });
  };

  const handleEdit = (employee: CourierEmployee) => {
    axiosInstance.put(`${API_URL}/api/user/courier-employee/${employee.courierEmployeeId}`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName })
      .then(() => {
        setToast({ type: 'success', text: 'Employee updated successfully' });
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error updating employee' });
      });
  };

  const handleDelete = (employee: CourierEmployee) => {
    axiosInstance.delete(`${API_URL}/api/user/courier-employee/${employee.courierEmployeeId}`)
      .then(() => {
        setToast({ type: 'success', text: 'Employee deleted successfully' });
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
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
          columns={courierEmployeeColumns}
          data={couriers ?? []}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageCouriers;
