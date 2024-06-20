package org.example.praktika;

public class Masina {
    private String producator;
    private String model;
    private String fabricatie;
    private String motor;
    private String combustibil;
    private String pasageri;
    private String inmatriculare;
    private String pret;

    // Constructor
    public Masina(String producator, String model, String fabricatie, String motor,
               String combustibil, String pasageri, String inmatriculare, String pret) {
        this.producator = producator;
        this.model = model;
        this.fabricatie = fabricatie;
        this.motor = motor;
        this.combustibil = combustibil;
        this.pasageri = pasageri;
        this.inmatriculare = inmatriculare;
        this.pret = pret;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFabricatie(String fabricatie) {
        this.fabricatie = fabricatie;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public void setCombustibil(String combustibil) {
        this.combustibil = combustibil;
    }

    public void setPasageri(String pasageri) {
        this.pasageri = pasageri;
    }

    public void setInmatriculare(String inmatriculare) {
        this.inmatriculare = inmatriculare;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    // Getters
    public String getProducator() {
        return producator;
    }

    public String getModel() {
        return model;
    }

    public String getFabricatie() {
        return fabricatie;
    }

    public String getMotor() {
        return motor;
    }

    public String getCombustibil() {
        return combustibil;
    }

    public String getPasageri() {
        return pasageri;
    }

    public String getInmatriculare() {
        return inmatriculare;
    }

    public String getPret() {
        return pret;
    }
}
