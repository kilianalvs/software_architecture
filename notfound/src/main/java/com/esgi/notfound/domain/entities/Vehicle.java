package com.esgi.notfound.domain.entities;

public class Vehicle {
    private String matricule;
    
    public Vehicle(String matricule) {
        if (matricule == null || matricule.trim().isEmpty()) {
            throw new IllegalArgumentException("La plaque d'immatriculation ne peut pas être vide");
        }
        this.matricule = matricule.trim().toUpperCase();
    }
    
    public String getMatricule() {
        return matricule;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return matricule.equals(vehicle.matricule);
    }
    
    @Override
    public int hashCode() {
        return matricule.hashCode();
    }
}