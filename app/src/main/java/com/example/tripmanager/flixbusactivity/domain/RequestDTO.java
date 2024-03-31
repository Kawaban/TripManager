package com.example.tripmanager.flixbusactivity.domain;

public class RequestDTO {
    private String origin;
    private String destination;
    private String date;

    private int numberOfPassengers;
    public RequestDTO(String origin, String destination, String date, int numberOfPassengers) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

}
