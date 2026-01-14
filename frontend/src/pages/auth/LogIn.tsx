import React, { useState, useEffect } from 'react';
import { validateAuthFields } from '../../utils/validateAuthFields';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './LogIn.css';
import { API_URL } from '../../App';
import { useAuth, UserRole } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Toast from '../../components/toast/Toast';

const LogIn: React.FunctionComponent = () => {
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');
  const [password, setPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [showToast, setShowToast] = useState(false);
  const [toastType, setToastType] = useState<'success' | 'error' | null>(null);
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
    if (emailError) {
      setEmailError('');
    }
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
    if (passwordError) {
      setPasswordError('');
    }
  };

  const performLogin = (isAdmin: boolean) => {
    const { emailError, passwordError, valid } = validateAuthFields(email, password);
    setEmailError(emailError);
    setPasswordError(passwordError);

    if (valid) {
      setLoading(true);
      setToastType(null);
      setShowToast(false);

      const endpoint = isAdmin ? `${API_URL}/api/auth/log-admin-in` : `${API_URL}/api/auth/log-in`;

      axios.post(endpoint, { email, password })
        .then((response) => {
          const {role} = response.data;
          if (role) {
            let roleEnum;
            switch (role) {
            case 'ADMIN':
              roleEnum = UserRole.ADMIN;
              break;
            case 'CLIENT':
              roleEnum = UserRole.CLIENT;
              break;
            case 'OFFICE_EMPLOYEE':
              roleEnum = UserRole.OFFICE_EMPLOYEE;
              break;
            case 'COURIER_EMPLOYEE':
              roleEnum = UserRole.COURIER_EMPLOYEE;
              break;
            default:
              roleEnum = role;
            }
            login({role: roleEnum});
            setToastType('success');
            setShowToast(true);
            setTimeout(() => {
              navigate('/');
            }, 1500);
          } else {
            setToastType('error');
            setShowToast(true);
          }
        })
        .catch(() => {
          setToastType('error');
          setShowToast(true);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    performLogin(false);
  };

  const handleAdminSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    performLogin(true);
  };

  useEffect(() => {
    if (showToast) {
      const timer = setTimeout(() => {
        setShowToast(false);
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [showToast]);

  return (
    <>
      {showToast && toastType && (
        <Toast
          type={toastType}
          text={toastType === 'success' ? 'You successfully logged in!' : 'Invalid credentials!'}
        />
      )}
      <div className="container login-container" data-testid="login-page">
        <div className="card login-card">
          <div className="card-body">
            <h2 className="card-title mb-4 text-center">Log In</h2>
            <form className="login-form" noValidate onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="email" className="form-label text-start w-100">Email address</label>
                <input
                  type="email"
                  className={`form-control${emailError ? ' is-invalid' : ''}`}
                  id="email"
                  placeholder="Enter email"
                  value={email}
                  onChange={handleEmailChange}
                />
                {emailError && (
                  <div className="invalid-feedback text-start w-100">{emailError}</div>
                )}
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label text-start w-100">Password</label>
                <input
                  type="password"
                  className={`form-control${passwordError ? ' is-invalid' : ''}`}
                  id="password"
                  placeholder="Password"
                  value={password}
                  onChange={handlePasswordChange}
                />
                {passwordError && (
                  <div className="invalid-feedback text-start w-100">{passwordError}</div>
                )}
              </div>
              <button type="submit" className="btn btn-primary w-100" disabled={loading}>Log In</button>
              <button
                type="button"
                className="btn btn-secondary w-100 mt-2"
                disabled={loading}
                onClick={handleAdminSubmit}
              >
                Log In as Admin
              </button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default LogIn;
