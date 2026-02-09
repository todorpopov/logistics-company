import React from 'react';
import Table, { Config } from '../../components/table/Table';
import './UpdateShipment.css';
import Toast from '../../components/toast/Toast';
import { useGetShipmentsByCourierUserId } from '../request';
import { shipmentColumns } from '../shipmentCommon';
import { useShipmentHandlers } from '../useShipmentHandlers';

const config: Config = {
  enableCreation: false,
  enableEdition: true,
  enableDeletion: false,
};

const UpdateShipment: React.FC = () => {
  const { data: shipments } = useGetShipmentsByCourierUserId(JSON.parse(localStorage.getItem('user') || '{}').userId);
  const { toast, setToast, handleEdit } = useShipmentHandlers({ enableEdition: true });

  return (
    <div className="update-shipments-container">
      {toast && (
        <Toast type={toast.type} text={toast.text} onClose={() => setToast(null)} />
      )}
      <div className="update-shipments-content">
        <Table
          config={config}
          columns={shipmentColumns}
          data={shipments ?? []}
          onEdit={handleEdit}
        />
      </div>
    </div>
  );
};

export default UpdateShipment;
