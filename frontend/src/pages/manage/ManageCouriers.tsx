import { useQueryClient } from '@tanstack/react-query';
import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import { useGetCourierEmployees } from './request';
import axios from 'axios';
import {API_URL} from '../../App';

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
  { header: 'ID', accessor: 'courierEmployeeId', mandatoryForCreation: true },
  { header: 'Email', accessor: 'email', mandatoryForCreation: true },
  { header: 'First Name', accessor: 'firstName', mandatoryForCreation: true },
  { header: 'Last Name', accessor: 'lastName', mandatoryForCreation: true },
];

const ManageCouriers: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: couriers } = useGetCourierEmployees();

  const handleCreate = (employee: CourierEmployee) => {
    axios.post(`${API_URL}/api/user/courier-employee`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, password: employee.lastName })
      .then(() => {
        console.log('Employee added successfully');
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error adding employee');
        // todo add error toast
      });
  };

  const handleEdit = (employee: CourierEmployee) => {
    axios.put(`${API_URL}/api/user/courier-employee/${employee.courierEmployeeId}`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName })
      .then(() => {
        console.log('Employee updated successfully');
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error updating employee');
        // todo add error toast
      });
  };

  const handleDelete = (employee: CourierEmployee) => {
    axios.delete(`${API_URL}/api/user/courier-employee/${employee.courierEmployeeId}`)
      .then(() => {
        queryClient.invalidateQueries({ queryKey: ['courierEmployees'] });
        console.log('Employee deleted successfully');
        // todo add success toast
      })
      .catch(() => {
        console.log('Error deleted employee');
        // todo add error toast
      });
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
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
