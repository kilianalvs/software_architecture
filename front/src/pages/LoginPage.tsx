import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { submitConnexion } from '../domain/usecases/submitConnexion';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth'; 

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();
  const { login } = useAuth();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Login with:', { username, password });
    
    try {
      // Utiliser soit submitConnexion soit login du contexte
      await submitConnexion(username, password);
      
      // Mettre à jour l'état d'authentification
      await login(username, password);
      
      navigate('/secretaire');
    } catch (error) {
      alert("Erreur lors de la connexion");
      console.error(error);
    }
  };

  return (
    <div className="flex flex-column align-items-center justify-content-center" style={{ height: '100vh', padding: '2rem' }}>
      <h2 style={{ marginBottom: '2rem' }}>Connexion Secrétaire</h2>

      <form onSubmit={handleLogin} style={{ width: '100%', maxWidth: '400px' }}>
        <div className="p-fluid">
          <div className="field mb-4">
            <label htmlFor="username">Nom d’utilisateur</label>
            <InputText
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Entrez votre identifiant"
              required
            />
          </div>

          <div className="field mb-4">
            <label htmlFor="password">Mot de passe</label>
            <Password
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              feedback={false}
              toggleMask
              placeholder="Mot de passe"
              required
            />
          </div>

          <Button type="submit" label="Se connecter" className="w-full p-button-lg" />
        </div>
      </form>
    </div>
  );
};

export default LoginPage;
