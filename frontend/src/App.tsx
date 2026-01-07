import React from 'react';
import { Routes, Route } from 'react-router-dom';
import './App.css';
import Navbar from './components/navbar/Navbar';
import LogIn from './pages/auth/LogIn';
import Home from './pages/home/Home';
import SignUp from './pages/auth/SignUp';
import Manage from './pages/manage/Manage';
import ManageOffices from './pages/manage/ManageOffices';
import ManageOfficeWorkers from './pages/manage/ManageOfficeWorkers';
import ManageCouriers from './pages/manage/ManageCouriers';
import ManagePackages from './pages/manage/ManagePackages';
import TableDemo from './components/table/TableDemo';

const App: React.FunctionComponent = () => {
  return (
    <div className="App">
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/manage" element={<Manage />} />
        <Route path="/manage/offices" element={<ManageOffices />} />
        <Route path="/manage/workers" element={<ManageOfficeWorkers />} />
        <Route path="/manage/couriers" element={<ManageCouriers />} />
        <Route path="/manage/packages" element={<ManagePackages />} />
        <Route path="/table-demo" element={<TableDemo />} />
        <Route path="/table" element={<TableDemo />} />
        {/* Add more routes here as needed */}
      </Routes>
      {/*
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      */}
    </div>
  );
};

export default App;
