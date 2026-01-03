import React from 'react';
import './Home.css';

const Home: React.FunctionComponent = () => {
  return (
    <div data-testid="home-page" className="home-container">
      <h1 className="home-title">Welcome to Logistics Company</h1>
      <div className="icon-row">
        <div className="icon-feature-card">
          <i className="bi bi-truck"></i>
          <hr className="icon-divider" />
          <div>Fast Deliveries</div>
        </div>
        <div className="icon-feature-card">
          <i className="bi bi-box-seam"></i>
          <hr className="icon-divider" />
          <div>Send and Receive Packages</div>
        </div>
        <div className="icon-feature-card">
          <i className="bi bi-currency-dollar"></i>
          <hr className="icon-divider" />
          <div>Best prices</div>
        </div>
      </div>
    </div>
  );
};

export default Home;
