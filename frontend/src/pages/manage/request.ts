import { useQuery, UseQueryResult } from '@tanstack/react-query';
import axios from 'axios';
import { API_URL } from '../../App';
import type { Office } from './ManageOffices';
import {OfficeEmployee} from './ManageOfficeEmployees';
import { CourierEmployee } from './ManageCouriers';
import { Shipment } from './ManagePackages';

interface OfficeEmployeeRaw {
  officeEmployeeId: number;
  email: string;
  firstName: string;
  lastName: string;
  office: Office;
}

export const useGetOffices = ():
    UseQueryResult<Office[], Error> => useQuery<Office[], Error>({
      queryKey: ['offices'],
      queryFn: async () => {
        const { data } = await axios.get<Office[]>(`${API_URL}/api/office`);
        return data;
      },
      refetchInterval: 5000
    });

export const useGetOfficeEmployees = ():
    UseQueryResult<OfficeEmployee[], Error> => useQuery<OfficeEmployee[], Error>({
      queryKey: ['officeEmployees'],
      queryFn: async () => {
        const { data } = await axios.get<OfficeEmployeeRaw[]>(`${API_URL}/api/user/office-employee`);
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
        const { data } = await axios.get<CourierEmployee[]>(`${API_URL}/api/user/courier-employee`);
        return data;
      },
      refetchInterval: 5000
    });

export const useGetShipments = (): UseQueryResult<Shipment[], Error> => useQuery<Shipment[], Error>({
  queryKey: ['shipments'],
  queryFn: async () => {
    const { data } = await axios.get<Shipment[]>(`${API_URL}/api/shipment`);
    return data;
  },
  refetchInterval: 5000
});
