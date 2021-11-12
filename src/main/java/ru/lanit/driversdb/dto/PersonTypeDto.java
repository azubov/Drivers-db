package ru.lanit.driversdb.dto;

import lombok.Data;

@Data
public class PersonTypeDto {
    private String id;
    private String country;
    private String name;
    private String birthdate;

    public PersonTypeDto() {
    }

    public PersonTypeDto(String id, String country, String name, String birthdate) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.birthdate = birthdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
