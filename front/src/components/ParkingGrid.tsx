import React from 'react';

type ParkingGridProps = {
  selected: string | null;
  onSelect: (spotId: string) => void;
  availableSpots: string[];
};

const rows = ['A', 'B', 'C', 'D', 'E', 'F'];

const cols = Array.from({ length: 10 }, (_, i) => String(i + 1));

const ParkingGrid: React.FC<ParkingGridProps> = ({ selected, onSelect, availableSpots }) => {
  
  const eclat = "/eclat.png";

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
          const isElectricRow = row === 'A' || row === 'F';

          return (
            <button
              type="button"
              key={spotId}
              disabled={!isAvailable}
              onClick={() => onSelect(spotId)}
              style={{
                position: 'relative',
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
              {isElectricRow && (
                <img
                  src={eclat}
                  alt="Voiture électrique"
                  style={{
                    position: 'absolute',
                    bottom: '4px',
                    right: '4px',
                    width: '16px',
                    height: '16px',
                  }}
                />
              )}
            </button>
          );
        })
      )}
    </div>
  );
};

export default ParkingGrid;