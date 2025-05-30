package com.esgi.notfound.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public ApplicationRunner dataLoader() {
        return args -> {
            try {
                // Vérifier si les données existent déjà
                Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM parking_spots", Integer.class);
                if (count != null && count > 0) {
                    System.out.println("🔄 Données déjà présentes (" + count + " places)");
                    return;
                }
                
                // Configuration des rangées
                String[] rows = {"A", "B", "C", "D", "E", "F"};
                
                // Double boucle pour créer les 60 places (6 rangées x 10 places)
                for (String row : rows) {
                    for (int position = 1; position <= 10; position++) {
                        
                        String spotNumber = row + position;
                        String spotType;
                        boolean hasElectricCharging;
                        
                        // Définir le type selon la rangée
                        if (row.equals("A") || row.equals("F")) {
                            spotType = "ELECTRIC_CHARGING";
                            hasElectricCharging = true;
                        } else if (row.equals("C") && position == 1) {
                            // C1 est standard mais avec recharge
                            spotType = "STANDARD";
                            hasElectricCharging = true;
                        } else {
                            spotType = "STANDARD";
                            hasElectricCharging = false;
                        }
                        
                        // Insérer la place
                        String sql = "INSERT INTO parking_spots (spot_number, spot_type, row_name, position_in_row, is_available, has_electric_charging) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";
                        
                        jdbcTemplate.update(sql, spotNumber, spotType, row, position, true, hasElectricCharging);
                    }
                }
                
                System.out.println("✅ 60 places de parking insérées avec succès !");
                
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'insertion : " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}