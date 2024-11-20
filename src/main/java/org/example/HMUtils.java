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
}
