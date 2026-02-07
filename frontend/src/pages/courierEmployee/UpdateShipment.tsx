import React from 'react';
import Table, { Config } from '../../components/table/Table';
import '../manage/ManageOffices.css';
import Toast from '../../components/toast/Toast';
import { useGetShipments } from '../request';
import { shipmentColumns } from '../shipmentCommon';
import { useShipmentHandlers } from '../useShipmentHandlers';

const config: Config = {
  enableCreation: false,
  enableEdition: true,
  enableDeletion: false,
};

const UpdateShipment: React.FC = () => {
  const { data: shipments } = useGetShipments();
  const { toast, setToast, handleEdit } = useShipmentHandlers({ enableEdition: true });

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
          onEdit={handleEdit}
        />
      </div>
    </div>
  );
};

export default UpdateShipment;
