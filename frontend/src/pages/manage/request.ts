import { useQuery, UseQueryResult } from '@tanstack/react-query';
import { API_URL } from '../../App';
import type { Office } from './ManageOffices';
import {OfficeEmployee} from './ManageOfficeEmployees';
import { CourierEmployee } from './ManageCouriers';
import { Shipment } from './ManageShipments';
import axiosInstance from '../../utils/axiosConfig';

interface OfficeEmployeeRaw {
  officeEmployeeId: number;
  email: string;
  firstName: string;
  lastName: string;
  office: Office;
}

interface ShipmentRaw {
  shipmentId: number;
  sender: { clientId: number };
  registeredBy: { officeEmployeeId: string };
  deliveryType: string;
  deliveryOffice: { officeId: string };
  courierEmployee: { courierEmployeeId: string };
  weightGram: string;
  price: string;
  status: string;
  sentDate: string;
  deliveredDate: string;
  clientPhoneNumber: string;
}

export const useGetOffices = ():
    UseQueryResult<Office[], Error> => useQuery<Office[], Error>({
      queryKey: ['offices'],
      queryFn: async () => {
        const { data } = await axiosInstance.get<Office[]>(`${API_URL}/api/office`);
        return data;
      },
      refetchInterval: 5000
    });

export const useGetOfficeEmployees = ():
    UseQueryResult<OfficeEmployee[], Error> => useQuery<OfficeEmployee[], Error>({
      queryKey: ['officeEmployees'],
      queryFn: async () => {
        const { data } = await axiosInstance.get<OfficeEmployeeRaw[]>(`${API_URL}/api/user/office-employee`);
        return data.map(employee => ({
          officeEmployeeId: employee.officeEmployeeId,
          email: employee.email,
          firstName: employee.firstName,
          lastName: employee.lastName,
          officeId: employee.office?.officeId ?? 0
        }));
      },
      refetchInterval: 5000
    });

export const useGetCourierEmployees = ():
    UseQueryResult<CourierEmployee[], Error> => useQuery<CourierEmployee[], Error>({
      queryKey: ['courierEmployees'],
      queryFn: async () => {
        const { data } = await axiosInstance.get<CourierEmployee[]>(`${API_URL}/api/user/courier-employee`);
        return data;
      },
      refetchInterval: 5000
    });

export const useGetShipments = (): UseQueryResult<Shipment[], Error> => useQuery<Shipment[], Error>({
  queryKey: ['shipments'],
  queryFn: async () => {
    const { data } = await axiosInstance.get<ShipmentRaw[]>(`${API_URL}/api/shipment`);
    return data.map(shipment => ({
      shipmentId: shipment.shipmentId,
      senderId: shipment.sender?.clientId ?? 0,
      registeredById: shipment.registeredBy?.officeEmployeeId ?? '',
      deliveryType: shipment.deliveryType,
      deliveryOfficeId: shipment.deliveryOffice?.officeId ?? '',
      courierEmployeeId: shipment.courierEmployee?.courierEmployeeId ?? '',
      weightGram: shipment.weightGram,
      price: shipment.price,
      status: shipment.status,
      sentDate: shipment.sentDate,
      deliveredDate: shipment.deliveredDate,
      clientPhoneNumber: shipment.clientPhoneNumber
    }));
  },
  refetchInterval: 5000
});
