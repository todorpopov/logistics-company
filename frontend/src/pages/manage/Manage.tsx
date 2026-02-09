import React from 'react';
import './Manage.css';

function Manage() {
  return (
    <div className="manage-container">
      <h3 className="manage-title">Manage A*</h3>
      <div className="manage-icon-row">
        <div className="manage-icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/offices'}>
          <i className="bi bi-pin-map"></i>
          <hr className="manage-icon-divider" />
          <div>Manage offices</div>
        </div>
        <div className="manage-icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/workers'}>
          <i className="bi bi-person"></i>
          <hr className="manage-icon-divider" />
          <div>Manage office employees</div>
        </div>
        <div className="manage-icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/couriers'}>
          <i className="bi bi-truck"></i>
          <hr className="manage-icon-divider" />
          <div>Manage couriers</div>
        </div>
        <div className="manage-icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/shipments'}>
          <i className="bi bi-box-seam"></i>
          <hr className="manage-icon-divider" />
          <div>Manage shipments</div>
        </div>
      </div>
    </div>
  );

}

export default Manage;
