import React from 'react';
import Table, { Column, Config } from '../../components/table/Table';
import { useLocation } from 'react-router-dom';
import './Report.css';

const config: Config = {
  enableCreation: false,
  enableEdition: false,
  enableDeletion: false,
};

const Report: React.FC = () => {
  const location = useLocation();
  const reportData = location.state?.reportData ?? [];
  const reportTitle = location.state?.reportTitle ?? '';

  const isNumberReport = typeof reportData === 'number';

  const columns: Column<any>[] = React.useMemo(() => {
    if (isNumberReport || reportData.length === 0) {return [];}
    return Object.keys(reportData[0]).map((key) => ({
      header: key.charAt(0).toUpperCase() + key.slice(1),
      accessor: key,
    }));
  }, [reportData, isNumberReport]);

  return (
    <div className="manage-container">
      <div className="manage-content">
        <h4>Report: {reportTitle}</h4>
        {isNumberReport ? (
          <div className="report-number-card">
            <span className="report-number-value">{reportData}</span>
          </div>
        ) : (
          <Table
            config={config}
            columns={columns}
            data={reportData}
          />
        )}
      </div>
    </div>
  );
};

export default Report;
