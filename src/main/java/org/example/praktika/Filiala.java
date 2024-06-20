package org.example.praktika;

public class Filiala {
    private String nume;
    private String adresa;
    private String telefon;
    private String persoanaContact;

    public Filiala(String nume, String adresa, String telefon, String persoanaContact) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.persoanaContact = persoanaContact;
    }

    // Getters and setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPersoanaContact() {
        return persoanaContact;
    }

    public void setPersoanaContact(String persoanaContact) {
        this.persoanaContact = persoanaContact;
    }
}
