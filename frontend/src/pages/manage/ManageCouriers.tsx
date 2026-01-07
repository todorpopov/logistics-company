import React, { useState } from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';

interface Courier {
  id: number;
  firstName: string;
  lastName: string;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const courierColumns: Column<Courier>[] = [
  { header: 'ID', accessor: 'id', mandatoryForCreation: true },
  { header: 'First Name', accessor: 'firstName', mandatoryForCreation: true },
  { header: 'Last Name', accessor: 'lastName', mandatoryForCreation: true },
];

const initialCouriers: Courier[] = [
  { id: 1, firstName: 'Ivan', lastName: 'Ivanov' },
  { id: 2, firstName: 'Petar', lastName: 'Petrov' },
];

const ManageCouriers: React.FC = () => {
  const [couriers, setCouriers] = useState<Courier[]>(initialCouriers);

  const handleCreate = (courier: Courier) => {
    setCouriers(prev => [{ ...courier }, ...prev]);
  };

  const handleEdit = (courier: Courier, rowIndex: number) => {
    setCouriers(prev => prev.map((item, idx) => idx === rowIndex ? { ...item, ...courier } : item));
  };

  const handleDelete = (_courier: Courier, rowIndex: number) => {
    setCouriers(prev => prev.filter((_item, idx) => idx !== rowIndex));
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
        <Table
          config={config}
          columns={courierColumns}
          data={couriers}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageCouriers;
