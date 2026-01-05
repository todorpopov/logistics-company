import { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar: React.FC = () => {
  const [expanded, setExpanded] = useState(false);

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">A*</Link>
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
            <li className="nav-item">
              <Link className="nav-link" to="/" onClick={() => setExpanded(false)}>Home</Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#features" onClick={() => setExpanded(false)}>Features</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#pricing" onClick={() => setExpanded(false)}>Pricing</a>
            </li>
            <li className="nav-item">
              <a className="nav-link disabled" aria-disabled="true">Disabled</a>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/manage" onClick={() => setExpanded(false)}>Manage</Link>
            </li>
          </ul>
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/login" onClick={() => setExpanded(false)}>Login</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/signup" onClick={() => setExpanded(false)}>Signup</Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
