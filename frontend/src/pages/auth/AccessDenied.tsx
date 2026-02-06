import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AccessDenied.css';

const AccessDenied: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className="access-denied-container">
      <h1 className="access-denied-title">Access Denied</h1>
      <p className="access-denied-message">You do not have permission to view this page.</p>
      <button className="access-denied-btn" onClick={() => navigate('/')}>Go to Home</button>
    </div>
  );
};

export default AccessDenied;
