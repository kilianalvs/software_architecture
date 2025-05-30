import React, { useEffect, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import { Button } from 'primereact/button';
import { submitReservation } from '../domain/usecases/submitReservation';
import ParkingGrid from './ParkingGrid';
import { getAvailableSpots } from '../service/parkingService';

export const ReservationForm = () => {
  const [matricule, setMatricule] = useState('');
  const [dates, setDates] = useState<Date[] | null>(null);
  const [selectedSpot, setSelectedSpot] = useState<string | null>(null);
  const [availableSpots, setAvailableSpots] = useState<string[]>([]);

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

      if (dates && dates.length === 2 && dates[0] && dates[1]) {
        const start = dates[0].toISOString().split('T')[0];
        const end = dates[1].toISOString().split('T')[0];
        try {
          const spots = await getAvailableSpots(start, end, false); 
          setAvailableSpots(spots.filter((s: any) => s.available).map((s: any) => s.number));

        } catch (err) {
          console.error('Erreur lors du chargement des places disponibles', err);
        }
      }
    };

    fetchAvailable();
  }, [dates]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!dates || dates.length !== 2 || !dates[0] || !dates[1] || !matricule || !selectedSpot) {
      alert("Veuillez remplir tous les champs et sélectionner une place");
      return;
    }

    const start = dates[0].toISOString().split('T')[0];
    const end = dates[1].toISOString().split('T')[0];
    const parkingSpotId = selectedSpot; // string, sera géré côté back

    try {
      const result = await submitReservation(matricule, parkingSpotId, start, end);
      alert('Réservation confirmée');
      console.log(result);
    } catch (error) {
      alert("Erreur lors de la réservation");
      console.error(error);
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
            <label htmlFor="dates" className="block mb-2 text-xl">
              <b>La date</b>
            </label>
            <Calendar
              id="dates"
              value={dates}
              onChange={(e) => {
                const value = e.value as Date[];
                if (value?.length > 5) return;
                setDates(value);
              }}
              selectionMode="range"
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
                selected={selectedSpot}
                onSelect={setSelectedSpot}
                availableSpots={availableSpots}
              />
            </div>
          )}
        </div>
        <Button
          type="submit"
          label="Envoyer"
          className="w-full p-button-lg"
          disabled={!matricule || !dates || dates.length !== 2 || !selectedSpot}
        />
      </form>
    </div>
  );
};
