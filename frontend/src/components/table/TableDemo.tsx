import React, { useState } from 'react';
import Table, { Column } from './Table';

interface User {
  id: number;
  name: string;
  email: string;
  number: number;
}

const columns: Column<User>[] = [
  { header: 'ID', accessor: 'id', mandatoryForCreation: true },
  { header: 'Name', accessor: 'name', mandatoryForCreation: true },
  { header: 'Email', accessor: 'email', mandatoryForCreation: true },
  { header: 'Number', accessor: 'number', mandatoryForCreation: false },
];

const initialData: User[] = [
  { id: 1, name: 'Alice', email: 'alice@example.com', number: 13456 },
  { id: 2, name: 'Bob', email: 'bob@example.com', number: 16237 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 4, name: 'Diana', email: 'diana@example.com', number: 19444 },
  { id: 5, name: 'Eve', email: 'eve@example.com', number: 20555 },
];

const TableDemo: React.FC = () => {
  const [data, setData] = useState<User[]>(initialData);

  const handleEdit = (row: User, rowIndex: number) => {
    setData(prev => prev.map((item, idx) => idx === rowIndex ? { ...item, ...row } : item));
  };

  const handleDelete = (_row: User, rowIndex: number) => {
    setData(prev => prev.filter((_item, idx) => idx !== rowIndex));
  };

  const handleCreate = (row: User) => {
    setData(prev => [{ ...row }, ...prev]);
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Editable Users Table (Demo)</h2>
      <Table
        config={{ enableCreation: true, enableEdition: true, enableDeletion: true }}
        columns={columns}
        data={data}
        onEdit={handleEdit}
        onDelete={handleDelete}
        onCreate={handleCreate}
      />
    </div>
  );
};

export default TableDemo;
