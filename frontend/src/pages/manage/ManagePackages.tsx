import React, { useState } from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';

interface Package {
  id: number;
  sender: string;
  registeredBy: string;
  deliveryType: string;
  officeId: string;
  deliveredBy: string;
  weight: string;
  price: string;
  shipmentStatus: string;
  sentDate: string;
  deliveredDate: string;
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const packageColumns: Column<Package>[] = [
  { header: 'ID', accessor: 'id' },
  { header: 'Sender', accessor: 'sender', mandatoryForCreation: true },
  { header: 'Registered By', accessor: 'registeredBy', mandatoryForCreation: true },
  { header: 'Delivery Type', accessor: 'deliveryType', mandatoryForCreation: true },
  { header: 'Office ID', accessor: 'officeId', mandatoryForCreation: true },
  { header: 'Delivered By', accessor: 'deliveredBy', mandatoryForCreation: true },
  { header: 'Weight', accessor: 'weight', mandatoryForCreation: true },
  { header: 'Price', accessor: 'price', mandatoryForCreation: true },
  { header: 'Shipment Status', accessor: 'shipmentStatus', mandatoryForCreation: true },
  { header: 'Sent Date', accessor: 'sentDate', mandatoryForCreation: true },
  { header: 'Delivered Date', accessor: 'deliveredDate' },
];

const initialPackages: Package[] = [
  {
    id: 1,
    sender: 'Ivan Ivanov',
    registeredBy: 'Maria Petrova',
    deliveryType: 'office',
    officeId: '1',
    deliveredBy: 'Petar Petrov',
    weight: '2.5',
    price: '10',
    shipmentStatus: 'In transit',
    sentDate: '2024-01-01',
    deliveredDate: '',
  },
  {
    id: 2,
    sender: 'Georgi Georgiev',
    registeredBy: 'Anna Ivanova',
    deliveryType: 'address',
    officeId: '2',
    deliveredBy: 'Ivan Ivanov',
    weight: '1.2',
    price: '7',
    shipmentStatus: 'Delivered',
    sentDate: '2024-01-02',
    deliveredDate: '2024-01-05',
  },
];

const ManagePackages: React.FC = () => {
  const [packages, setPackages] = useState<Package[]>(initialPackages);

  const handleCreate = (pkg: Package) => {
    setPackages(prev => [{ ...pkg }, ...prev]);
  };

  const handleEdit = (pkg: Package, rowIndex: number) => {
    setPackages(prev => prev.map((item, idx) => idx === rowIndex ? { ...item, ...pkg } : item));
  };

  const handleDelete = (_pkg: Package, rowIndex: number) => {
    setPackages(prev => prev.filter((_item, idx) => idx !== rowIndex));
  };

  return (
    <div className="manage-offices-container">
      <div className="manage-offices-content">
        <Table
          config={config}
          columns={packageColumns}
          data={packages}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManagePackages;
