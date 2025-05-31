import React, { useEffect, useState } from 'react';
import { Calendar } from 'primereact/calendar';
import ParkingGrid from '../components/ParkingGrid';
import { getAvailableSpots } from '../service/parkingService';

const SecretaryParkingPage: React.FC = () => {
  const [selectedSpot, setSelectedSpot] = useState<string | null>(null);
  const [availableSpots, setAvailableSpots] = useState<string[]>([]);
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date());

  useEffect(() => {
    const fetchAvailable = async () => {
      if (!selectedDate) return;

      const dateStr = selectedDate.toISOString().split('T')[0];

      try {
        const spots = await getAvailableSpots(dateStr, dateStr, false);
        const filtered = spots.filter((s: any) => s.available).map((s: any) => s.number);
        setAvailableSpots(filtered);
      } catch (err) {
        console.error('Erreur lors du chargement des places disponibles', err);
      }
    };

    fetchAvailable();
  }, [selectedDate]);

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Vue Parking - Secrétaire</h2>

      <div className="mb-4" style={{ maxWidth: '300px' }}>
        <label htmlFor="calendar"><b>Sélectionner une date :</b></label>
        <Calendar
          id="calendar"
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.value as Date)}
          dateFormat="yy-mm-dd"
          showIcon
          readOnlyInput
          minDate={new Date()}
          disabledDays={[0, 6]} // désactive samedi/dimanche
        />
      </div>

      <p>Place sélectionnée : <b>{selectedSpot || 'aucune'}</b></p>

      <ParkingGrid
        selected={selectedSpot}
        onSelect={setSelectedSpot}
        availableSpots={availableSpots}
      />
    </div>
  );
};

export default SecretaryParkingPage;
