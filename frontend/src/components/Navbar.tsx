import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import {useAuth, UserRole} from '../context/AuthContext';

const Navbar: React.FC = () => {
  const [expanded, setExpanded] = useState(false);
  const { user, login, logout } = useAuth();

  // todo remove this mock
  // React.useEffect(() => {
  //   login({ userId: 1, role: UserRole.ADMIN });
  // }, []);

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          <img src={process.env.PUBLIC_URL + '/favicon.ico'} alt="Logo" style={{ height: '32px', width: '32px' }} />
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          aria-controls="navbarNav"
          aria-expanded={expanded}
          aria-label="Toggle navigation"
          onClick={() => setExpanded(!expanded)}
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className={`collapse navbar-collapse${expanded ? ' show' : ''}`} id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item align-items-center">
              <Link className="nav-link" to="/" onClick={() => setExpanded(false)}>Home</Link>
            </li>
            {user && (user.role === UserRole.ADMIN) && (
              <>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/manage" onClick={() => setExpanded(false)}>Manage</Link>
                </li>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/reports" onClick={() => setExpanded(false)}>Reports</Link>
                </li>
              </>
            )}
            {user && (user.role === UserRole.OFFICE_EMPLOYEE) && (
              <>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/create-shipment" onClick={() => setExpanded(false)}>Register Shipment</Link>
                </li>
              </>
            )}
            {user && (user.role === UserRole.COURIER_EMPLOYEE) && (
              <>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/update-shipment" onClick={() => setExpanded(false)}>Deliver Shipment</Link>
                </li>
              </>
            )}
            {user && (user.role === UserRole.CLIENT) && (
              <>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/shipments" onClick={() => setExpanded(false)}>My Shipments</Link>
                </li>
              </>
            )}
          </ul>
          <ul className="navbar-nav ms-auto">
            {user ? (
              <li className="nav-item align-items-center">
                <Link className="nav-link" to="/" onClick={() => { logout(); setExpanded(false);}}>Logout</Link>
              </li>
            ) : (
              <>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/login" onClick={() => setExpanded(false)}>Login</Link>
                </li>
                <li className="nav-item align-items-center">
                  <Link className="nav-link" to="/signup" onClick={() => setExpanded(false)}>Signup</Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
