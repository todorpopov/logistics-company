import React from 'react';
import Table, { Config } from '../../components/table/Table';
import './CreateShipment.css';
import Toast from '../../components/toast/Toast';
import { useGetShipments } from '../request';
import { shipmentColumns } from '../shipmentCommon';
import { useShipmentHandlers } from '../useShipmentHandlers';

const config: Config = {
  enableCreation: true,
  enableEdition: false,
  enableDeletion: false,
};

const CreateShipment: React.FC = () => {
  const { data: shipments } = useGetShipments();
  const { toast, setToast, handleCreate } = useShipmentHandlers({ enableCreation: true });

  return (
    <div className="create-shipments-container">
      {toast && (
        <Toast type={toast.type} text={toast.text} onClose={() => setToast(null)} />
      )}
      <div className="create-shipments-content">
        <Table
          config={config}
          columns={shipmentColumns}
          data={shipments ?? []}
          onCreate={handleCreate}
        />
      </div>
    </div>
  );
};

export default CreateShipment;
