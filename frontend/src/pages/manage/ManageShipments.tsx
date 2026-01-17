import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import './ManageOffices.css';
import { API_URL } from '../../App';
import { useQueryClient } from '@tanstack/react-query';
import { useGetShipments } from './request';
import Toast from '../../components/toast/Toast';
import axiosInstance from  '../../utils/axiosConfig';

export interface Shipment {
  shipmentId: number;
  senderId: number;
  registeredById: string;
  deliveryType: string;
  deliveryOfficeId: string;
  courierEmployeeId: string;
  weightGram: string;
  price: string;
  status: string;
  sentDate: string;
  deliveredDate: string;
  clientPhoneNumber: string
}

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const shipmentColumns: Column<Shipment>[] = [
  { header: 'ID', accessor: 'shipmentId', editable: false },
  { header: 'Sender', accessor: 'senderId', mandatoryForCreation: true },
  { header: 'Registered By', accessor: 'registeredById', mandatoryForCreation: true },
  { header: 'Delivery Type', accessor: 'deliveryType', mandatoryForCreation: true },
  { header: 'Office ID', accessor: 'deliveryOfficeId', mandatoryForCreation: true },
  { header: 'Delivered By', accessor: 'courierEmployeeId', mandatoryForCreation: true },
  { header: 'Weight', accessor: 'weightGram', mandatoryForCreation: true },
  { header: 'Price', accessor: 'price', mandatoryForCreation: true },
  { header: 'Shipment Status', accessor: 'status', mandatoryForCreation: true },
  { header: 'Sent Date', accessor: 'sentDate', mandatoryForCreation: true },
  { header: 'Client Phone Number', accessor: 'clientPhoneNumber', mandatoryForCreation: true },
  { header: 'Delivered Date', accessor: 'deliveredDate' },
];

const ManageShipments: React.FC = () => {
  const queryClient = useQueryClient();
  const { data: shipments } = useGetShipments();
  const [toast, setToast] = React.useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleCreate = (shipment: Shipment) => {
    axiosInstance.post(`${API_URL}/api/shipment`, {
      senderId: shipment.senderId,
      registeredById: shipment.registeredById,
      deliveryType: shipment.deliveryType,
      deliveryOfficeId: shipment.deliveryOfficeId,
      courierEmployeeId: shipment.courierEmployeeId,
      weightGram: shipment.weightGram,
      price: shipment.price,
      clientPhoneNumber: shipment.clientPhoneNumber
    })
      .then(() => {
        setToast({ type: 'success', text: 'Shipment created successfully' });
        queryClient.invalidateQueries({ queryKey: ['shipments'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error creating shipment' });
      });
  };

  const handleEdit = (shipment: Shipment) => {
    axiosInstance.put(`${API_URL}/api/shipment/${shipment.shipmentId}`, {
      deliveryType: shipment.deliveryType,
      deliveryOfficeId: shipment.deliveryOfficeId,
      courierEmployeeId: shipment.courierEmployeeId,
      price: shipment.price,
      clientPhoneNumber: shipment.clientPhoneNumber,
      shipmentStatus: shipment.status,
      deliveredDate: shipment.deliveredDate
    })
      .then(() => {
        setToast({ type: 'success', text: 'Shipment updated successfully' });
        queryClient.invalidateQueries({ queryKey: ['shipments'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error updating shipment' });
      });
  };

  const handleDelete = (shipment: Shipment) => {
    axiosInstance.delete(`${API_URL}/api/shipment/${shipment.shipmentId}`)
      .then(() => {
        setToast({ type: 'success', text: 'Shipment deleted successfully' });
        queryClient.invalidateQueries({ queryKey: ['shipments'] });
      })
      .catch(() => {
        setToast({ type: 'error', text: 'Error deleting shipment' });
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
          columns={shipmentColumns}
          data={shipments ?? []}
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageShipments;
