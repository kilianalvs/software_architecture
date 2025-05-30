import React from 'react';

type ParkingGridProps = {
  selected: string | null;
  onSelect: (spotId: string) => void;
  availableSpots: string[];
};

const rows = ['A', 'B', 'C', 'D', 'E', 'F'];
const cols = Array.from({ length: 10 }, (_, i) => String(i + 1).padStart(2, '0'));

const ParkingGrid: React.FC<ParkingGridProps> = ({ selected, onSelect, availableSpots }) => {

  return (
    <div
      style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(10, 1fr)',
        gap: '8px',
        marginTop: '1rem',
      }}
    >
      {rows.map((row) =>
        cols.map((col) => {
          const spotId = `${row}${col}`;
          const isAvailable = availableSpots.includes(spotId);
          const isSelected = selected === spotId;

          return (
            <button
              type="button"
              key={spotId}
              disabled={!isAvailable}
              onClick={() => onSelect(spotId)}
              style={{
                padding: '0.5rem',
                fontWeight: 'bold',
                backgroundColor: isSelected ? '#0d6efd' : isAvailable ? '#d1e7dd' : '#f8d7da',
                color: isSelected ? 'white' : 'black',
                border: '1px solid #ccc',
                borderRadius: '4px',
                cursor: isAvailable ? 'pointer' : 'not-allowed',
              }}
            >
              {spotId}
            </button>
          );
        })
      )}
    </div>
  );
};

export default ParkingGrid;
