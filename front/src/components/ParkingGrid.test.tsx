import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import ParkingGrid from './ParkingGrid';

describe('ParkingGrid', () => {
    const mockOnSelect = jest.fn();

    beforeEach(() => {
        mockOnSelect.mockClear();
    });

    // 🧪 Test 1: Rendu de base
    it('should render all parking spots (60 spots: 6 rows × 10 cols)', () => {
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={['A01', 'B05']} 
        />
        );

        // Vérifier que tous les spots sont rendus
        const spots = screen.getAllByRole('button');
        expect(spots).toHaveLength(60); // 6 rows × 10 cols = 60 spots
    });

    // 🧪 Test 2: Format des IDs de spots
    it('should generate correct spot IDs', () => {
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={['A01', 'F10']} 
        />
        );

        // Vérifier quelques spots spécifiques
        expect(screen.getByText('A01')).toBeInTheDocument();
        expect(screen.getByText('A10')).toBeInTheDocument();
        expect(screen.getByText('F01')).toBeInTheDocument();
        expect(screen.getByText('F10')).toBeInTheDocument();
        expect(screen.getByText('C05')).toBeInTheDocument();
    });

    // 🧪 Test 3: État des spots disponibles
    it('should enable only available spots', () => {
        const availableSpots = ['A01', 'B05', 'C10'];
        
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        // Spots disponibles sont activés
        expect(screen.getByText('A01')).toBeEnabled();
        expect(screen.getByText('B05')).toBeEnabled();
        expect(screen.getByText('C10')).toBeEnabled();

        // Spots non disponibles sont désactivés
        expect(screen.getByText('A02')).toBeDisabled();
        expect(screen.getByText('F09')).toBeDisabled();
    });

    // 🧪 Test 4: Styles des spots disponibles
    it('should apply correct styles for available spots', () => {
        const availableSpots = ['A01', 'B05'];
        
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        const availableSpot = screen.getByText('A01');
        const unavailableSpot = screen.getByText('A02');

        // Spot disponible: fond vert
        expect(availableSpot).toHaveStyle({
        backgroundColor: '#d1e7dd',
        color: 'black',
        cursor: 'pointer'
        });

        // Spot non disponible: fond rouge
        expect(unavailableSpot).toHaveStyle({
        backgroundColor: '#f8d7da',
        color: 'black',
        cursor: 'not-allowed'
        });
    });

    // 🧪 Test 5: Spot sélectionné
    it('should highlight selected spot with blue background', () => {
        const availableSpots = ['A01', 'B05'];
        
        render(
        <ParkingGrid 
            selected="A01" 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        const selectedSpot = screen.getByText('A01');
        const unselectedSpot = screen.getByText('B05');

        // Spot sélectionné: fond bleu, texte blanc
        expect(selectedSpot).toHaveStyle({
        backgroundColor: '#0d6efd',
        color: 'white'
        });

        // Spot non sélectionné: fond vert normal
        expect(unselectedSpot).toHaveStyle({
        backgroundColor: '#d1e7dd',
        color: 'black'
        });
    });

    // 🧪 Test 6: Callback onSelect
    it('should call onSelect when clicking available spot', () => {
        const availableSpots = ['A01', 'B05'];
        
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        const availableSpot = screen.getByText('A01');
        fireEvent.click(availableSpot);

        expect(mockOnSelect).toHaveBeenCalledTimes(1);
        expect(mockOnSelect).toHaveBeenCalledWith('A01');
    });

    // 🧪 Test 7: Pas de callback sur spot désactivé
    it('should not call onSelect when clicking disabled spot', () => {
        render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={['A01']} 
        />
        );

        const disabledSpot = screen.getByText('A02');
        fireEvent.click(disabledSpot);

        expect(mockOnSelect).not.toHaveBeenCalled();
    });

    // 🧪 Test 8: Changement de sélection
    it('should handle selection changes correctly', () => {
        const availableSpots = ['A01', 'B05', 'C10'];
        
        const { rerender } = render(
        <ParkingGrid 
            selected="A01" 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        // Initialement A01 sélectionné
        expect(screen.getByText('A01')).toHaveStyle({
        backgroundColor: '#0d6efd',
        color: 'white'
        });

        // Changer la sélection vers B05
        rerender(
        <ParkingGrid 
            selected="B05" 
            onSelect={mockOnSelect} 
            availableSpots={availableSpots} 
        />
        );

        // A01 plus sélectionné
        expect(screen.getByText('A01')).toHaveStyle({
        backgroundColor: '#d1e7dd',
        color: 'black'
        });

        // B05 maintenant sélectionné
        expect(screen.getByText('B05')).toHaveStyle({
        backgroundColor: '#0d6efd',
        color: 'white'
        });
    });

  // 🧪 Test 9: Grid layout CSS
    it('should apply correct grid layout styles', () => {
        const { container } = render(
        <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={[]} 
        />
        );

        const gridContainer = container.firstChild as HTMLElement;
        
        expect(gridContainer).toHaveStyle({
        display: 'grid',
        gridTemplateColumns: 'repeat(10, 1fr)',
        gap: '8px',
        marginTop: '1rem'
        });
    });

    // 🧪 Test 10: Tous les spots disponibles
    it('should handle when all spots are available', () => {
        const rows = ['A', 'B', 'C', 'D', 'E', 'F'];
        const cols = Array.from({ length: 10 }, (_, i) => String(i + 1).padStart(2, '0'));
        
        const allSpots: string[] = [];
        
        rows.forEach(row => {
            cols.forEach(col => {
            allSpots.push(`${row}${col}`);
            });
        });

        render(
            <ParkingGrid 
            selected={null} 
            onSelect={mockOnSelect} 
            availableSpots={allSpots} 
            />
        );

        const spots = screen.getAllByRole('button');
        spots.forEach(spot => {
            expect(spot).toBeEnabled();
            expect(spot).toHaveStyle({
            backgroundColor: '#d1e7dd',
            cursor: 'pointer'
            });
        });
    });



  // 🧪 Test 11: Aucun spot disponible
  it('should handle when no spots are available', () => {
    render(
      <ParkingGrid 
        selected={null} 
        onSelect={mockOnSelect} 
        availableSpots={[]} 
      />
    );

    const spots = screen.getAllByRole('button');
    spots.forEach(spot => {
      expect(spot).toBeDisabled();
      expect(spot).toHaveStyle({
        backgroundColor: '#f8d7da',
        cursor: 'not-allowed'
      });
    });
  });
});