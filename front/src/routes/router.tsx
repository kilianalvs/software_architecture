import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import { ReservationForm } from '../components/ReservationForm';
import Header from '../components/Header';
import SecretaryParkingPage from '../pages/dashboardSecretaire';
import PrivateRoute from './privateRoute';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<ReservationForm />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/secretaire"
          element={
            <PrivateRoute>
              <SecretaryParkingPage />
            </PrivateRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
