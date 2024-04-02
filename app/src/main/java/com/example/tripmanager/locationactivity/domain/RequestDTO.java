package com.example.tripmanager.locationactivity.domain;

public class RequestDTO {
    private String city;
    private Boolean attractions;
    private Boolean restaurants;

    public RequestDTO(String city, Boolean attractions, Boolean restaurants) {
        this.city = city;
        this.attractions = attractions;
        this.restaurants = restaurants;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getAttractions() {
        return attractions;
    }

    public void setAttractions(Boolean attractions) {
        this.attractions = attractions;
    }

    public Boolean getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Boolean restaurants) {
        this.restaurants = restaurants;
    }
}
