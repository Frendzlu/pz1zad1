package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Room {
    private final String roomName;
    private String roomDescription;
    private double roomPrice;
    private final int floor;
    private final int roomNumber;
    private final int capacity;
    private final ArrayList<Guest> guests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getFloor() {
        return floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public Room(String roomName, String roomDescription, double roomPrice, int floor, int roomNumber, int capacity, ArrayList<Guest> guests, LocalDate checkInDate, LocalDate checkOutDate) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.guests = guests;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.capacity = capacity;
    }

    public Room(String roomName, String roomDescription, double roomPrice, int floor, int roomNumber, int capacity) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        if (this.roomDescription.isEmpty()) {
            this.roomDescription = "No description was provided";
        }
        this.roomPrice = roomPrice;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.guests = new ArrayList<>();
        this.checkInDate = null;
        this.checkOutDate = null;
        this.capacity = capacity;
    }

    public Room(String roomName, int roomNumber, int floor) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.guests = new ArrayList<>();
        this.roomPrice = 0.0;
        this.roomDescription = "No description was provided";
        this.checkInDate = null;
        this.checkOutDate = null;
        this.capacity = 0;
    }

    public void removeGuests() {
        this.guests.clear();
    }

    public void addGuest(Guest guest) {
        if (guests.size() < this.capacity) {
            guests.add(guest);
        }
    }

    public static boolean isValidRoomName(int floor, String roomName) {
        return roomName.startsWith(String.valueOf(floor));
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public String[] toCSVFileRecord(String dateformat) {
        StringBuilder gueststring = new StringBuilder();
        for (Guest guest : guests) {
            if (gueststring.isEmpty()) {
                gueststring = new StringBuilder(guest.name + "," + guest.surname);
            } else {
                gueststring.append(";").append(guest.name).append(",").append(guest.surname);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);
        String cin = "null";
        if (this.checkInDate != null) {
            cin = this.checkInDate.format(formatter);
        }
        String cout = "null";
        if (this.checkOutDate != null) {
            cout = this.checkOutDate.format(formatter);
        }

        return new String[]{
            this.roomName,
            String.valueOf(this.floor),
            String.valueOf(this.roomNumber),
            String.valueOf(this.roomPrice),
            String.valueOf(this.capacity),
            this.roomDescription,
            cin,
            cout,
            gueststring.toString()
        };
    }
}
