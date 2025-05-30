package com.esgi.notfound.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.esgi.notfound.infrastructure.repositories")
@EntityScan(basePackages = "com.esgi.notfound.infrastructure.entities")
@EnableTransactionManagement
public class DatabaseConfig {
    
    // Configuration automatique via Spring Boot
    // Les propriétés sont dans application.properties
}
