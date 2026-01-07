import React from 'react';
import './Manage.css';

function Manage<T extends object>() {
  return (
    <div data-testid="home-page" className="home-container">
      <h3 className="home-title">Manage A*</h3>
      <div className="icon-row">
        <div className="icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/offices'}>
          <i className="bi bi-pin-map"></i>
          <hr className="icon-divider" />
          <div>Manage offices</div>
        </div>
        <div className="icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/workers'}>
          <i className="bi bi-person"></i>
          <hr className="icon-divider" />
          <div>Manage office employees</div>
        </div>
        <div className="icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/couriers'}>
          <i className="bi bi-truck"></i>
          <hr className="icon-divider" />
          <div>Manage couriers</div>
        </div>
        <div className="icon-feature-card clickable" tabIndex={0} role="button" onClick={() => window.location.href='/manage/packages'}>
          <i className="bi bi-box-seam"></i>
          <hr className="icon-divider" />
          <div>Manage packages</div>
        </div>
      </div>
    </div>
  );

}

export default Manage;
