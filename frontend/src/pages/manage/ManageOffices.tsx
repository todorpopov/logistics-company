import React, { useState } from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';

interface Office {
  id: number;
  name: string;
  address: string;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const officeColumns: Column<Office>[] = [
  { header: 'ID', accessor: 'id' },
  { header: 'Name', accessor: 'name', mandatoryForCreation: true },
  { header: 'Address', accessor: 'address', mandatoryForCreation: true },
];

const initialOffices: Office[] = [
  { id: 1, name: 'Central Office', address: '123 Main St' },
  { id: 2, name: 'Branch Office', address: '456 Side St' },
];

const ManageOffices: React.FC = () => {
  const [offices, setOffices] = useState<Office[]>(initialOffices);

  const handleCreate = (office: Office) => {
    setOffices(prev => [{ ...office }, ...prev]);
  };

  const handleEdit = (office: Office, rowIndex: number) => {
    setOffices(prev => prev.map((item, idx) => idx === rowIndex ? { ...item, ...office } : item));
  };

  const handleDelete = (_office: Office, rowIndex: number) => {
    setOffices(prev => prev.filter((_item, idx) => idx !== rowIndex));
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
        <Table
          config={config}
          columns={officeColumns}
          data={offices}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageOffices;
