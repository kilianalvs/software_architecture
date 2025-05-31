import React, { useEffect, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import { Button } from 'primereact/button';
import { submitReservation } from '../domain/usecases/submitReservation';
import ParkingGrid from './ParkingGrid';
import { getAvailableSpots } from '../service/parkingService';

export const ReservationForm = () => {
  const [matricule, setMatricule] = useState('');
  const [date, setDate] = useState<Date | null>(null);
  const [availableSpots, setAvailableSpots] = useState<any[]>([]);
  const [selectedSpot, setSelectedSpot] = useState<any | null>(null);

  const today = new Date();
  const maxDate = new Date();
  let addedDays = 0;
  while (addedDays < 5) {
    maxDate.setDate(maxDate.getDate() + 1);
    const day = maxDate.getDay();
    if (day !== 0 && day !== 6) addedDays++;
  }

  useEffect(() => {
    const fetchAvailable = async () => {
      if (date) {
        const formatted = date.toISOString().split('T')[0];
        try {
          const spots = await getAvailableSpots(formatted, formatted, false);
          setAvailableSpots(spots.filter((s: any) => s.available));
        } catch (err) {
          console.error('Erreur lors du chargement des places disponibles', err);
        }
      }
    };
    fetchAvailable();
  }, [date]);

  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!date || !matricule || !selectedSpot) {
      alert("Veuillez remplir tous les champs et sélectionner une place");
      return;
    }

    const formatted = date.toISOString().split('T')[0];

    try {
      const result = await submitReservation(matricule, selectedSpot.id, formatted, formatted);
      alert('Réservation confirmée');
      console.log(result);
    } catch (error: any) {
      const errorMessage = error?.response?.data?.message || "Erreur lors de la réservation";
      alert(errorMessage);
      console.error(errorMessage);
    }
  };


  return (
    <div
      className="flex flex-column justify-content-center align-items-center"
      style={{ height: '100%', padding: '2rem' }}
    >
      <h1 className="mb-5" style={{ fontSize: '2.5rem' }}>
        Réservation - Matricule
      </h1>

      <form
        onSubmit={handleSubmit}
        className="p-fluid"
        style={{ width: '100%', maxWidth: '600px' }}
      >
        <div style={{ marginBottom: 100, marginTop: 50 }}>
          <div className="mb-4">
            <label htmlFor="matricule" className="block mb-2 text-xl">
              <b>Matricule</b>
            </label>
            <InputText
              id="matricule"
              value={matricule}
              onChange={(e) => setMatricule(e.target.value)}
              placeholder="Entrez votre matricule"
              className="w-full p-inputtext-lg"
              required
            />
          </div>

          <div className="mb-4">
            <label htmlFor="date" className="block mb-2 text-xl">
              <b>Date</b>
            </label>
            <Calendar
              id="date"
              value={date}
              onChange={(e) => setDate(e.value as Date)}
              readOnlyInput
              minDate={today}
              maxDate={maxDate}
              showIcon
              disabledDays={[0, 6]}
              className="w-full p-inputtext-lg"
              required
            />
          </div>

          {availableSpots.length > 0 && (
            <div className="mb-4 mt-4">
              <label className="block mb-2 text-xl"><b>Choisissez une place</b></label>
              <ParkingGrid
                selected={selectedSpot?.number}
                onSelect={(num) => {
                  const fullSpot = availableSpots.find(s => s.number === num);
                  setSelectedSpot(fullSpot || null);
                }}
                availableSpots={availableSpots.map((s) => s.number)}
              />
            </div>
          )}
        </div>

        <Button
          type="submit"
          label="Envoyer"
          className="w-full p-button-lg"
          disabled={!matricule || !date || !selectedSpot}
        />
      </form>
    </div>
  );
};
