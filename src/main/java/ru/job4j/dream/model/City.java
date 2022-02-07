package ru.job4j.dream.model;

import java.util.Objects;

public class City {
    int id;
    String city;

    public City(int id, String city) {
        this.id = id;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city1 = (City) o;
        return id == city1.id && Objects.equals(city, city1.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }
}
