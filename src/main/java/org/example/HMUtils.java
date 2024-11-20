package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class HMUtils {
    public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> files = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                continue;
            }
            files.add(fileEntry);
        }
        return files;
    }
    public static void displayRoomInfo(Room room, ConsoleWrapper csl, boolean displayPrice, boolean displayGuests) {
        if (room == null) {
            return;
        }
        csl.print(1, "Room %s", room.getRoomName());
        if (displayPrice) {
            csl.print(2, "Price: %.02f", room.getRoomPrice());
        }
        csl.print(2, "%s", room.getRoomDescription());
        if (!displayGuests) {
            return;
        }
        if (!room.getGuests().isEmpty()) {
            csl.print(2, "Check in date:  %s", room.getCheckInDate());
            csl.print(2, "Check out date: %s", room.getCheckOutDate());
        } else {
            csl.print(2, "The room is free");
        }
        for (Guest guest : room.getGuests()) {
            csl.print(2, "%s", guest.toString());
        }
    }

    public static String getValidRoomName(ConsoleWrapper csl, int floor) {
        String roomName = "";
        boolean isRoomNumberValid = false;
        while (!isRoomNumberValid) {
            roomName = csl.getString(1, "Room number (with the floor at the beginning): ").trim();
            isRoomNumberValid = Room.isValidRoomName(floor, roomName);
            if (!isRoomNumberValid) {
                csl.print(1, "This room number does not belong to this floor, please try again.");
            }
        }
        return roomName;
    }

    public static boolean shouldMoreRoomsBeCreated(ConsoleWrapper csl) {
        boolean isNotValid = true;
        while (isNotValid) {
            isNotValid = false;
            String y = csl.getString(1, "Do you want to add more rooms? (Y/n): ").toLowerCase();
            if (y.equals("n")) {
                return true;
            } else if (!y.equals("y")) {
                isNotValid = true;
            }
        }
        return false;
    }
}
