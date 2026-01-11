import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import axios from 'axios';
import {API_URL} from '../../App';
import {useGetOffices} from './request';
import { useQueryClient } from '@tanstack/react-query';

export interface Office {
  officeId: number;
  name: string;
  address: string;
  phoneNumber?: string;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const officeColumns: Column<Office>[] = [
  { header: 'ID', accessor: 'officeId' },
  { header: 'Name', accessor: 'name', mandatoryForCreation: true },
  { header: 'Address', accessor: 'address', mandatoryForCreation: true },
  { header: 'Phone Number', accessor: 'phoneNumber', mandatoryForCreation: true }
];

const ManageOffices: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: offices } = useGetOffices();

  const handleCreate = (office: Office) => {
    axios.post(`${API_URL}/api/office`, { name: office.name, address: office.address, phoneNumber: office.phoneNumber })
      .then(() => {
        console.log('Office created successfully');
        queryClient.invalidateQueries({ queryKey: ['offices'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error creating office');
        // todo add error toast
      });
  };

  const handleEdit = (office: Office) => {
    axios.put(`${API_URL}/api/office/${office.officeId}`, { name: office.name, address: office.address, phoneNumber: office.phoneNumber })
      .then(() => {
        console.log('Office updated successfully');
        queryClient.invalidateQueries({ queryKey: ['offices'] });
        // todo add success toast
      })
      .catch(() => {
        console.log('Error updating office');
        // todo add error toast
      });
  };

  const handleDelete = (office: Office) => {
    axios.delete(`${API_URL}/api/office/${office.officeId}`)
      .then(() => {
        queryClient.invalidateQueries({ queryKey: ['offices'] });
        console.log('Office deleted successfully');
        // todo add success toast
      })
      .catch(() => {
        console.log('Error deleted office');
        // todo add error toast
      });
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
        <Table
          config={config}
          columns={officeColumns}
          data={offices ?? []}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageOffices;
