import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import 'primereact/resources/themes/lara-light-blue/theme.css'; // thème
import 'primereact/resources/primereact.min.css';                // core
import 'primeicons/primeicons.css';                              // icônes
import 'primeflex/primeflex.css';           
import AppRouter from './routes/router';
import { AuthProvider } from './hooks/useAuth'; // ← Assure-toi que c'est bien importé


const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <AuthProvider>
      <AppRouter />
    </AuthProvider>
  </React.StrictMode>
);


