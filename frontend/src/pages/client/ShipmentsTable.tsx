import React from 'react';
import Table from '../../components/table/Table';
import { shipmentColumns } from '../shipmentCommon';
import {useGetShipments, useGetShipmentsSentBy} from '../request';

const readOnlyConfig = {
  enableCreation: false,
  enableEdition: false,
  enableDeletion: false,
};

const ShipmentsTable: React.FC = () => {
  const { data: shipments } = useGetShipmentsSentBy();
  console.log(localStorage.getItem('userId'));
  return (
    <div className="manage-container">
      <div className="manage-content">
        <h2>My Shipments</h2>
        <Table
          config={readOnlyConfig}
          columns={shipmentColumns}
          data={shipments ?? []}
        />
      </div>
    </div>
  );
};

export default ShipmentsTable;

