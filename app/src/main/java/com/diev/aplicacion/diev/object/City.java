package com.diev.aplicacion.diev.object;


public class City {
    private int ciudadId;
    private String name;
    private String country;
    private String temp;

    public City() {
    }

    public City(int ciudadId, String name, String country, String temp) {
        this.ciudadId = ciudadId;
        this.name = name;
        this.country = country;
        this.temp = temp;
    }

    public int getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(int ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
