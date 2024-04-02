package com.example.tripmanager.aiadvisoractivity.domain;

public class RequestDTO {
    private String currentLocation;

    public RequestDTO(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
