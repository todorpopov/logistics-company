import React from 'react';
import Table, { Column } from './Table';

interface User {
  id: number;
  name: string;
  email: string;
  number: number;
}

const columns: Column<User>[] = [
  { header: 'ID', accessor: 'id' },
  { header: 'Name', accessor: 'name' },
  { header: 'Email', accessor: 'email' },
  { header: 'Number', accessor: 'number' },
];

const data: User[] = [
  { id: 1, name: 'Alice', email: 'alice@example.com', number: 13456 },
  { id: 2, name: 'Bob', email: 'bob@example.com', number: 16237 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 4, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 5, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 6, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 7, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', number: 18373 },
];

const TableDemo: React.FC = () => (
  <div style={{ padding: 24 }}>
    <h2>Users Table</h2>
    <Table columns={columns} data={data} />
  </div>
);

export default TableDemo;
