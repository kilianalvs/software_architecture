import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from 'primereact/button';
import { useAuth } from '../hooks/useAuth';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const { isAuthenticated, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const handleMesReservation = () => {
    navigate('/mes-reservations');
  }

  const handleLogin = () => {
    navigate('/login');
  };

  return (
    <header className="flex justify-content-end p-3 shadow-2">

      {isAuthenticated ? (
        <Button
          label="Se déconnecter"
          icon="pi pi-sign-out"
          onClick={handleLogout}
          className="p-button-sm p-button-danger"
        />
      ) : (
        <Button
          label="Se connecter"
          icon="pi pi-sign-in"
          onClick={handleLogin}
          className="p-button-sm"
        />
      )}
    </header>
  );
};

export default Header;