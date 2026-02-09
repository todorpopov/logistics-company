import React from 'react';
import Table from '../../components/table/Table';
import { shipmentColumns } from '../shipmentCommon';
import {
  useGetShipmentsReceivedByCurrentUser,
  useGetShipmentsSentByCurrentUser
} from '../request';
import './Shipments.css';

const readOnlyConfig = {
  enableCreation: false,
  enableEdition: false,
  enableDeletion: false,
};

const ShipmentsTable: React.FC = () => {
  const { data: shipmentsSentByCurrentUser } = useGetShipmentsSentByCurrentUser();
  const { data: shipmentsReceivedByCurrentUser } = useGetShipmentsReceivedByCurrentUser();

  return (
    <div className="shipments-container">
      <div className="shipments-content">
        <h2>Shipments sent by me</h2>
        <Table
          config={readOnlyConfig}
          columns={shipmentColumns}
          data={shipmentsSentByCurrentUser ?? []}
        />
      </div>
      <div className="shipments-content">
        <h2>Shipments received by me</h2>
        <Table
          config={readOnlyConfig}
          columns={shipmentColumns}
          data={shipmentsReceivedByCurrentUser ?? []}
        />
      </div>
    </div>
  );
};

export default ShipmentsTable;
