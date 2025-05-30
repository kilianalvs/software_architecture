-- Initialisation des 60 places de parking (6 rangées x 10 places)
-- ne sert plus a rien en l'etat, bdd gérée par config/DatabaseConfig.java
-- Rangées A et B : places standards
INSERT INTO parking_spots (spot_number, spot_type, row_name, position_in_row, is_available, has_electric_charging) VALUES

-- Rangées A : places murales avec recharge électrique
('A1', 'ELECTRIC_CHARGING', 'A', 1, true, true),
('A2', 'ELECTRIC_CHARGING', 'A', 2, true, true),
('A3', 'ELECTRIC_CHARGING', 'A', 3, true, true),
('A4', 'ELECTRIC_CHARGING', 'A', 4, true, true),
('A5', 'ELECTRIC_CHARGING', 'A', 5, true, true),
('A6', 'ELECTRIC_CHARGING', 'A', 6, true, true),
('A7', 'ELECTRIC_CHARGING', 'A', 7, true, true),
('A8', 'ELECTRIC_CHARGING', 'A', 8, true, true),
('A9', 'ELECTRIC_CHARGING', 'A', 9, true, true),
('A10', 'ELECTRIC_CHARGING', 'A', 10, true, true),

-- Rangée B (1-10) - Standard
('B1', 'STANDARD', 'B', 1, true, false),
('B2', 'STANDARD', 'B', 2, true, false),
('B3', 'STANDARD', 'B', 3, true, false),
('B4', 'STANDARD', 'B', 4, true, false),
('B5', 'STANDARD', 'B', 5, true, false),
('B6', 'STANDARD', 'B', 6, true, false),
('B7', 'STANDARD', 'B', 7, true, false),
('B8', 'STANDARD', 'B', 8, true, false),
('B9', 'STANDARD', 'B', 9, true, false),
('B10', 'STANDARD', 'B', 10, true, false),

-- Rangée C (1-10) - Standard
('C1', 'STANDARD', 'C', 1, true, true),
('C2', 'STANDARD', 'C', 2, true, false),
('C3', 'STANDARD', 'C', 3, true, false),
('C4', 'STANDARD', 'C', 4, true, false),
('C5', 'STANDARD', 'C', 5, true, false),
('C6', 'STANDARD', 'C', 6, true, false),
('C7', 'STANDARD', 'C', 7, true, false),
('C8', 'STANDARD', 'C', 8, true, false),
('C9', 'STANDARD', 'C', 9, true, false),
('C10', 'STANDARD', 'C', 10, true, false),

('D1', 'STANDARD', 'D', 1, true, false),
('D2', 'STANDARD', 'D', 2, true, false),
('D3', 'STANDARD', 'D', 3, true, false),
('D4', 'STANDARD', 'D', 4, true, false),
('D5', 'STANDARD', 'D', 5, true, false),
('D6', 'STANDARD', 'D', 6, true, false),
('D7', 'STANDARD', 'D', 7, true, false),
('D8', 'STANDARD', 'D', 8, true, false),
('D9', 'STANDARD', 'D', 9, true, false),
('D10', 'STANDARD', 'D', 10, true, false),

('E1', 'STANDARD', 'E', 1, true, false),
('E2', 'STANDARD', 'E', 2, true, false),
('E3', 'STANDARD', 'E', 3, true, false),
('E4', 'STANDARD', 'E', 4, true, false),
('E5', 'STANDARD', 'E', 5, true, false),
('E6', 'STANDARD', 'E', 6, true, false),
('E7', 'STANDARD', 'E', 7, true, false),
('E8', 'STANDARD', 'E', 8, true, false),
('E9', 'STANDARD', 'E', 9, true, false),
('E10', 'STANDARD', 'E', 10, true, false),

-- Rangées F : places murales avec recharge électrique
('F1', 'ELECTRIC_CHARGING', 'F', 1, true, true),
('F2', 'ELECTRIC_CHARGING', 'F', 2, true, true),
('F3', 'ELECTRIC_CHARGING', 'F', 3, true, true),
('F4', 'ELECTRIC_CHARGING', 'F', 4, true, true),
('F5', 'ELECTRIC_CHARGING', 'F', 5, true, true),
('F6', 'ELECTRIC_CHARGING', 'F', 6, true, true),
('F7', 'ELECTRIC_CHARGING', 'F', 7, true, true),
('F8', 'ELECTRIC_CHARGING', 'F', 8, true, true),
('F9', 'ELECTRIC_CHARGING', 'F', 9, true, true),
('F10', 'ELECTRIC_CHARGING', 'F', 10, true, true);