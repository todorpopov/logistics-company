import { API_URL } from '../App';
import axiosInstance from '../utils/axiosConfig';
import { useQueryClient } from '@tanstack/react-query';
import { Shipment } from './shipmentCommon';
import { useState } from 'react';

export function useShipmentHandlers(config: { enableCreation?: boolean; enableEdition?: boolean; enableDeletion?: boolean }) {
  const queryClient = useQueryClient();
  const [toast, setToast] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleCreate = config.enableCreation
    ? (shipment: Shipment) => {
      axiosInstance.post(`${API_URL}/api/shipment`, {
        senderId: shipment.senderId,
        receiverId: shipment.receiverId,
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
        .catch((e) => {
          setToast({ type: 'error', text: 'Error creating shipment' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
        });
    }
    : undefined;

  const handleEdit = config.enableEdition
    ? (shipment: Shipment) => {
      axiosInstance.put(`${API_URL}/api/shipment/${shipment.shipmentId}`, {
        deliveryType: shipment.deliveryType,
        deliveryOfficeId: shipment.deliveryOfficeId,
        courierEmployeeId: shipment.courierEmployeeId,
        price: shipment.price,
        clientPhoneNumber: shipment.clientPhoneNumber,
        shipmentStatus: shipment.status,
        phoneNumber: shipment.clientPhoneNumber,
        deliveredDate: shipment.deliveredDate,
        status: shipment.status
      })
        .then(() => {
          setToast({ type: 'success', text: 'Shipment updated successfully' });
          queryClient.invalidateQueries({ queryKey: ['shipments'] });
        })
        .catch((e) => {
          setToast({ type: 'error', text: 'Error updating shipment' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
        });
    }
    : undefined;

  const handleDelete = config.enableDeletion
    ? (shipment: Shipment) => {
      axiosInstance.delete(`${API_URL}/api/shipment/${shipment.shipmentId}`)
        .then(() => {
          setToast({ type: 'success', text: 'Shipment deleted successfully' });
          queryClient.invalidateQueries({ queryKey: ['shipments'] });
        })
        .catch((e) => {
          setToast({ type: 'error', text: 'Error deleting shipment' + (e?.response?.data?.message ? `: ${e.response.data.message}` : '') });
        });
    }
    : undefined;

  return { toast, setToast, handleCreate, handleEdit, handleDelete };
}
