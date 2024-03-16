package com.example.tripmanager.flixbusactivity.domain;

public class RequestDTO {
    private String origin;
    private String destination;
    private String date;

    private int numberOfPassengers;
    private boolean isRoundTrip;
    private String returnDate;
    public RequestDTO(String origin, String destination, String date, int numberOfPassengers, boolean isRoundTrip, String returnDate) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.numberOfPassengers = numberOfPassengers;
        this.isRoundTrip = isRoundTrip;
        this.returnDate = returnDate;
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

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
