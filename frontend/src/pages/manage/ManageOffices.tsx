import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageCommon.css';
import {API_URL} from '../../App';
import {useGetOffices} from '../request';
import { useQueryClient } from '@tanstack/react-query';
import Toast from '../../components/toast/Toast';
import axiosInstance from '../../utils/axiosConfig';

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
  { header: 'ID', accessor: 'officeId', mandatoryForCreation: false, editable: false },
  { header: 'Name', accessor: 'name', mandatoryForCreation: true },
  { header: 'Address', accessor: 'address', mandatoryForCreation: true },
  { header: 'Phone Number', accessor: 'phoneNumber', mandatoryForCreation: true }
];

const ManageOffices: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: offices } = useGetOffices();
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleCreate = (office: Office) => {
    axiosInstance.post(`${API_URL}/api/office`, { name: office.name, address: office.address, phoneNumber: office.phoneNumber })
      .then(() => {
        setToast({ type: 'success', text: 'Office created successfully' });
        queryClient.invalidateQueries({ queryKey: ['offices'] });
      })
      .catch((e) => {
        setToast({ type: 'error', text: 'Error creating office' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
      });
  };

  const handleEdit = (office: Office) => {
    axiosInstance.put(`${API_URL}/api/office/${office.officeId}`, { name: office.name, address: office.address, phoneNumber: office.phoneNumber })
      .then(() => {
        setToast({ type: 'success', text: 'Office updated successfully' });
        queryClient.invalidateQueries({ queryKey: ['offices'] });
      })
      .catch((e) => {
        setToast({ type: 'error', text: 'Error updating office' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
      });
  };

  const handleDelete = (office: Office) => {
    axiosInstance.delete(`${API_URL}/api/office/${office.officeId}`)
      .then(() => {
        setToast({ type: 'success', text: 'Office deleted successfully' });
        queryClient.invalidateQueries({ queryKey: ['offices'] });
      })
      .catch((e) => {
        setToast({ type: 'error', text: 'Error deleting office' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
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
