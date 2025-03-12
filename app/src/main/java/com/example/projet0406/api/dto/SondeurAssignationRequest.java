package com.example.projet0406.api.dto;

public class SondeurAssignationRequest {
    private int id_sondeur_assigne;
    private String etat_sondage;

    public SondeurAssignationRequest(int id_sondeur_assigne, String etat_sondage) {
        this.id_sondeur_assigne = id_sondeur_assigne;
        this.etat_sondage = etat_sondage;
    }

    public int getIdSondeurAssigne() {
        return id_sondeur_assigne;
    }

    public String getEtatSondage() {
        return etat_sondage;
    }
}