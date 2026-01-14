import React, { useEffect } from 'react';

export interface ToastProps {
    type: 'success' | 'error';
    text: string;
    autoClose?: boolean;
    onClose?: () => void;
}

const Toast: React.FC<ToastProps> = ({ type, text, autoClose = true, onClose }) => {
  useEffect(() => {
    if (autoClose && onClose) {
      const timer = setTimeout(() => {
        onClose();
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [autoClose, onClose]);

  return (
    <div className="login-toast">
      {type === 'success' && (
        <div className="alert alert-success mb-0" role="alert">
          {text}
        </div>
      )}
      {type === 'error' && (
        <div className="alert alert-danger mb-0" role="alert">
          {text}
        </div>
      )}
    </div>
  );
};

export default Toast;
