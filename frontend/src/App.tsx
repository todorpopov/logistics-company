import React from 'react';
import { Routes, Route } from 'react-router-dom';
import './App.css';
import Navbar from './components/navbar/Navbar';
import LogIn from './pages/auth/LogIn';
import Home from './pages/home/Home';
import SignUp from './pages/auth/SignUp';
import Manage from './pages/manage/Manage';
import ManageOffices from './pages/manage/ManageOffices';
import ManageOfficeEmployees from './pages/manage/ManageOfficeEmployees';
import ManageCouriers from './pages/manage/ManageCouriers';
import ManageShipments from './pages/manage/ManageShipments';
import { AuthProvider } from './context/AuthContext';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import ReportsHome from './pages/reports/ReportsHome';
import Report from './pages/reports/Report';
import CreateShipment from './pages/officeEmployee/CreateShipment';
import ShipmentsTable from './pages/client/ShipmentsTable';

export const API_URL = process.env.REACT_APP_API_URL;

const queryClient = new QueryClient();

const App: React.FunctionComponent = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <div className="App">
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LogIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/manage" element={<Manage />} />
            <Route path="/manage/offices" element={<ManageOffices />} />
            <Route path="/manage/workers" element={<ManageOfficeEmployees />} />
            <Route path="/manage/couriers" element={<ManageCouriers />} />
            <Route path="/manage/shipments" element={<ManageShipments />} />
            <Route path="/reports" element={<ReportsHome />} />
            <Route path="/report" element={<Report />} />
            <Route path="/shipment" element={<CreateShipment />} />
            <Route path="/shipments" element={<ShipmentsTable />} />
          </Routes>
        </div>
      </AuthProvider>
    </QueryClientProvider>
  );
};

export default App;
