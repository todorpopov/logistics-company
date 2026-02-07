import { useQuery, UseQueryResult } from '@tanstack/react-query';
import { API_URL } from '../App';
import type { Office } from './manage/ManageOffices';
import {OfficeEmployee} from './manage/ManageOfficeEmployees';
import { CourierEmployee } from './manage/ManageCouriers';
import { Shipment } from './shipmentCommon';
import axiosInstance from '../utils/axiosConfig';
import { mapOfficeEmployees, mapShipments } from './reports/reportMappers';

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
  receiver: { clientId: number };
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
        return mapOfficeEmployees(data);
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
    return mapShipments(data);
  },
  refetchInterval: 5000
});

export const useGetShipmentsSentBy = (): UseQueryResult<Shipment[], Error> => useQuery<Shipment[], Error>({
    queryKey: ['shipments'],
    queryFn: async () => {
        const { data } = await axiosInstance.get<ShipmentRaw[]>(`${API_URL}/api/shipments-sent-by/${localStorage.getItem('userId')}`);
        return mapShipments(data);
    },
    refetchInterval: 5000
});
