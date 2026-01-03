import React, { useState, useEffect } from 'react';
import { validateAuthFields } from '../../utils/validateAuthFields';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './AuthForm.css';

const SignUp: React.FunctionComponent = () => {
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
      axios.post('/signup', { email, password })
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
        <div className="auth-toast">
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
      <div className="container d-flex justify-content-center auth-container" data-testid="login-page">
        <div className="card auth-card">
          <div className="card-body">
            <h2 className="card-title mb-4 text-center">Sign Up</h2>
            <form noValidate onSubmit={handleSubmit}>
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
              <button type="submit" className="btn btn-primary w-100" disabled={loading}>Sign Up</button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default SignUp;
