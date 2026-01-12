import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import { useGetOfficeEmployees } from './request';
import { useQueryClient } from '@tanstack/react-query';
import {API_URL} from '../../App';
import axios from 'axios';

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

  const handleCreate = (employee: OfficeEmployee) => {
    axios.post(`${API_URL}/api/user/office-employee`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, officeId: employee.officeId, password: employee.lastName })
      .then(() => {
        console.log('Employee added successfully');
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error adding employee');
        // todo add error toast
      });
  };

  const handleEdit = (employee: OfficeEmployee) => {
    axios.put(`${API_URL}/api/user/office-employee/${employee.officeEmployeeId}`, { email: employee.email, firstName: employee.firstName, lastName: employee.lastName, officeId: employee.officeId })
      .then(() => {
        console.log('Employee updated successfully');
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error updating employee');
        // todo add error toast
      });
  };

  const handleDelete = (employee: OfficeEmployee) => {
    axios.delete(`${API_URL}/api/user/office-employee/${employee.officeEmployeeId}`)
      .then(() => {
        queryClient.invalidateQueries({ queryKey: ['officeEmployees'] });
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
