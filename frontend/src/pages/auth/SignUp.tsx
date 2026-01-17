import React, { useState, useEffect } from 'react';
import { validateAuthFields } from '../../utils/validateAuthFields';
import { API_URL } from '../../App';
import { useAuth, UserRole } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './SignUp.css';
import axiosInstance from '../../utils/axiosConfig';

const SignUp: React.FunctionComponent = () => {
  const [firstName, setFirstName] = useState('');
  const [firstNameError, setFirstNameError] = useState('');
  const [lastName, setLastName] = useState('');
  const [lastNameError, setLastNameError] = useState('');
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

  const handleFirstNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFirstName(e.target.value);
    if (firstNameError) {
      setFirstNameError('');
    }
  };
  const handleLastNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLastName(e.target.value);
    if (lastNameError) {
      setLastNameError('');
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const { emailError, passwordError, firstNameError, lastNameError, valid } = validateAuthFields(email, password, firstName, lastName);
    setEmailError(emailError);
    setPasswordError(passwordError);
    setFirstNameError(firstNameError || '');
    setLastNameError(lastNameError || '');
    if (valid) {
      setLoading(true);
      setToastType(null);
      setShowToast(false);
      axiosInstance.post(`${API_URL}/api/auth/sign-up`, { email, password, firstName, lastName })
        .then((response) => {
          // If backend returns a token and role, log the user in automatically
          const { role, token } = response.data;
          if (role && token) {
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
            login({ role: roleEnum, token });
            setToastType('success');
            setShowToast(true);
            setTimeout(() => {
              navigate('/');
            }, 1500);
          } else {
            setToastType('success');
            setShowToast(true);
            setTimeout(() => {
              window.location.href = '/';
            }, 1500);
          }
        })
        .catch(() => {
          setToastType('error');
          setShowToast(true);
          setEmail('');
          setPassword('');
        })
        .finally(() => {
          setLoading(false);
        });
    }
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
      {showToast && (
        <div className="signup-toast">
          {toastType === 'success' && (
            <div className="alert alert-success mb-0" role="alert">
              You have registered successfully!
            </div>
          )}
          {toastType === 'error' && (
            <div className="alert alert-danger mb-0" role="alert">
              Registration failed!
            </div>
          )}
        </div>
      )}
      <div className="container signup-container" data-testid="sign-up-page">
        <div className="card signup-card">
          <div className="card-body">
            <h2 className="card-title mb-4 text-center">Sign Up</h2>
            <form noValidate onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="firstName" className="form-label text-start w-100">First Name</label>
                <input
                  type="text"
                  className={`form-control${firstNameError ? ' is-invalid' : ''}`}
                  id="firstName"
                  placeholder="Enter first name"
                  value={firstName}
                  onChange={handleFirstNameChange}
                />
                {firstNameError && (
                  <div className="invalid-feedback text-start w-100">{firstNameError}</div>
                )}
              </div>
              <div className="mb-3">
                <label htmlFor="lastName" className="form-label text-start w-100">Last Name</label>
                <input
                  type="text"
                  className={`form-control${lastNameError ? ' is-invalid' : ''}`}
                  id="lastName"
                  placeholder="Enter last name"
                  value={lastName}
                  onChange={handleLastNameChange}
                />
                {lastNameError && (
                  <div className="invalid-feedback text-start w-100">{lastNameError}</div>
                )}
              </div>
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
              <button type="submit" className="btn btn-primary w-100 mt-3" disabled={loading}>Sign Up</button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default SignUp;
