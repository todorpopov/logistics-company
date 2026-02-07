import { Column } from '../components/table/Table';

export interface Shipment {
  shipmentId: number;
  senderId: number;
  receiverId: number;
  registeredById: string;
  deliveryType: string;
  deliveryOfficeId: string;
  courierEmployeeId: string;
  weightGram: string;
  price: string;
  status: string;
  sentDate: string;
  deliveredDate: string;
  clientPhoneNumber: string;
}

export const shipmentColumns: Column<Shipment>[] = [
  { header: 'ID', accessor: 'shipmentId', mandatoryForCreation: false, editable: false },
  { header: 'Sender', accessor: 'senderId', mandatoryForCreation: true, editable: false },
  { header: 'Receiver', accessor: 'receiverId', mandatoryForCreation: true, editable: false },
  { header: 'Registered By', accessor: 'registeredById', mandatoryForCreation: true, editable: false },
  { header: 'Delivery Type', accessor: 'deliveryType', mandatoryForCreation: true, editable: false },
  { header: 'Office ID', accessor: 'deliveryOfficeId', mandatoryForCreation: true, editable: false },
  { header: 'Delivered By', accessor: 'courierEmployeeId', mandatoryForCreation: false, editable: false },
  { header: 'Weight', accessor: 'weightGram', mandatoryForCreation: true, editable: false },
  { header: 'Price', accessor: 'price', mandatoryForCreation: true, editable: false },
  { header: 'Shipment Status', accessor: 'status', mandatoryForCreation: true },
  { header: 'Sent Date', accessor: 'sentDate', mandatoryForCreation: true, editable: false },
  { header: 'Client Phone Number', accessor: 'clientPhoneNumber', mandatoryForCreation: true, editable: false },
  { header: 'Delivered Date', accessor: 'deliveredDate', mandatoryForCreation: false },
];
