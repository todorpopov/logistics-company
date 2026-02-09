import React from 'react';
import './Home.css';

const Home: React.FunctionComponent = () => {
  return (
    <div className="home-container">
      <h1 className="home-title">Welcome to A*</h1>
      <div className="home-icon-row">
        <div className="home-icon-feature-card">
          <i className="bi bi-truck"></i>
          <hr className="home-icon-divider" />
          <div>Fast Deliveries</div>
        </div>
        <div className="home-icon-feature-card">
          <i className="bi bi-box-seam"></i>
          <hr className="home-icon-divider" />
          <div>Send and Receive Packages</div>
        </div>
        <div className="home-icon-feature-card">
          <i className="bi bi-currency-dollar"></i>
          <hr className="home-icon-divider" />
          <div>Best prices</div>
        </div>
      </div>
    </div>
  );
};

export default Home;
