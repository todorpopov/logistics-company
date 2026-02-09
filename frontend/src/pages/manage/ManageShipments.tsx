import React from 'react';
import Table, { Config } from '../../components/table/Table';
import './ManageCommon.css';
import Toast from '../../components/toast/Toast';
import { useGetShipments } from '../request';
import { shipmentColumns } from '../shipmentCommon';
import { useShipmentHandlers } from '../useShipmentHandlers';

const config: Config = {
  enableCreation: true,
  enableEdition: true,
  enableDeletion: true,
};

const ManageShipments: React.FC = () => {
  const { data: shipments } = useGetShipments();
  const { toast, setToast, handleCreate, handleEdit, handleDelete } = useShipmentHandlers({ enableCreation: true, enableEdition: true, enableDeletion: true });

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
          onCreate={handleCreate}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
};

export default ManageShipments;
