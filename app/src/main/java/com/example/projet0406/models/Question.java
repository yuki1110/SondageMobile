package com.example.projet0406.models;

public class Question {
    private int id;
    private String libelle;
    private String typeQuestion;

    public Question(int id, String libelle, String typeQuestion) {
        this.id = id;
        this.libelle = libelle;
        this.typeQuestion = typeQuestion;
    }

    public int getId() {
        return id;
    }
    public String getLibelle() {
        return libelle;
    }
    public String getTypeQuestion() {
        return typeQuestion;
    }
}