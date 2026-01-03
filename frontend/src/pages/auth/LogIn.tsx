import React, { useState, useEffect } from 'react';
import { validateAuthFields } from '../../utils/validateAuthFields';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './LogIn.css';

const LogIn: React.FunctionComponent = () => {
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');
  const [password, setPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [showToast, setShowToast] = useState(false);
  const [toastType, setToastType] = useState<'success' | 'error' | null>(null);
  const [loading, setLoading] = useState(false);

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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const { emailError, passwordError, valid } = validateAuthFields(email, password);
    setEmailError(emailError);
    setPasswordError(passwordError);
    if (valid) {
      setLoading(true);
      setToastType(null);
      setShowToast(false);
      axios.post('/login', { email, password })
        .then(() => {
          setToastType('success');
          setShowToast(true);
          setTimeout(() => {
            window.location.href = '/';
          }, 1500);
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
        <div className="login-toast">
          {toastType === 'success' && (
            <div className="alert alert-success mb-0" role="alert">
              You successfully logged in!
            </div>
          )}
          {toastType === 'error' && (
            <div className="alert alert-danger mb-0" role="alert">
              Invalid credentials!
            </div>
          )}
        </div>
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
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default LogIn;
