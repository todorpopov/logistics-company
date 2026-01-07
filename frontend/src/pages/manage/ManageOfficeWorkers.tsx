import React, { useState } from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';

interface OfficeWorker {
  id: number;
  firstName: string;
  lastName: string;
  officeId: number;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const officeWorkerColumns: Column<OfficeWorker>[] = [
  { header: 'ID', accessor: 'id' },
  { header: 'First Name', accessor: 'firstName', mandatoryForCreation: true },
  { header: 'Last Name', accessor: 'lastName', mandatoryForCreation: true },
  { header: 'Office ID', accessor: 'officeId', mandatoryForCreation: true },
];

const initialOfficeWorkers: OfficeWorker[] = [
  { id: 1, firstName: 'Anna', lastName: 'Ivanova', officeId: 1 },
  { id: 2, firstName: 'Georgi', lastName: 'Petrov', officeId: 2 },
];

const ManageOfficeWorkers: React.FC = () => {
  const [officeWorkers, setOfficeWorkers] = useState<OfficeWorker[]>(initialOfficeWorkers);

  const handleCreate = (worker: OfficeWorker) => {
    setOfficeWorkers(prev => [{ ...worker }, ...prev]);
  };

  const handleEdit = (worker: OfficeWorker, rowIndex: number) => {
    setOfficeWorkers(prev => prev.map((item, idx) => idx === rowIndex ? { ...item, ...worker } : item));
  };

  const handleDelete = (_worker: OfficeWorker, rowIndex: number) => {
    setOfficeWorkers(prev => prev.filter((_item, idx) => idx !== rowIndex));
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
        <Table
          config={config}
          columns={officeWorkerColumns}
          data={officeWorkers}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageOfficeWorkers;
