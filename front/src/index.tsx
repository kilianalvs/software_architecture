import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import AppRouter from './routes/router';
import 'primereact/resources/themes/lara-light-blue/theme.css'; // thème
import 'primereact/resources/primereact.min.css';                // core
import 'primeicons/primeicons.css';                              // icônes
import 'primeflex/primeflex.css';                                // utilitaires flex


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <AppRouter />
  </React.StrictMode>
);


