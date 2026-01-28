import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import '../styles/Auth.css';

const Unauthorized = () => {
  const { logout } = useAuth();

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>Access Denied</h2>
        <p>You do not have permission to access this page.</p>
        <div className="button-group">
          <Link to="/" className="btn-primary">Go to Home</Link>
          <button onClick={logout} className="btn-secondary">Logout</button>
        </div>
      </div>
    </div>
  );
};

export default Unauthorized;
