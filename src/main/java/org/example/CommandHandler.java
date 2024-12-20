package org.example;

import jdk.jfr.Description;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;



public class CommandHandler {
    public static String NO_ROOMS_MSG = "Hotel has no rooms";
    public static String CHOOSE_ROOM_MSG = "Choose a room: ";
    public static String NOT_FOUND_MSG = "Room not found";

    private CommandHandler() {
        throw new IllegalStateException("Utility class");
    }

    @Description("Shows all available commands")
    public static void help(ConsoleWrapper csl, Hotel hotel) {
        csl.print(1, "Available commands:");
        csl.print(1, " - 'help'");
        csl.print(2, "Shows the list of commands you are currently seeing");
        csl.print(1, " - 'exit'");
        csl.print(2, "Exits the program without asking if you want to save");
        for (Method c : CommandHandler.class.getDeclaredMethods()) {
            csl.print(1, " - '%s'", c.getName());
            csl.print(2, "%s", c.getDeclaredAnnotation(Description.class).value());
        }
    }

    @Description("Saves hotel data to selected csv file")
    public static void save(ConsoleWrapper csl, Hotel hotel) {
        File file = new File("./hotels/"+hotel.hotelName+".csv");
        try {
            boolean wasFileCreated = file.createNewFile();
            if (wasFileCreated) {
                csl.print(1, "Overwriting existing data...");
            }
        } catch (IOException e) {
            csl.print(1, "Error creating file");
        }
        hotel.saveToFile(new File("./hotels/"+hotel.hotelName+".csv"), "uuuu-MM-dd");
    }

    @Description("Loads hotel data from selected csv file")
    public static void load(ConsoleWrapper csl, Hotel hotel) {
        ArrayList<File> existingFiles = HMUtils.listFilesForFolder(new File("./hotels"));
        ArrayList<String> filenames = new ArrayList<>();
        if (existingFiles.size() == 1) {
            hotel.loadFromFileWithName(existingFiles.getFirst().getName().replace(".csv", ""));
            return;
        }
        csl.print(1, "Available files:");
        for (File file : existingFiles) {
            filenames.add(file.getName());
            csl.print(2, "%s, modified at: %s" , file.getName(), file.lastModified());
        }
        String chosenFilename = "";
        boolean isNotValid = true;
        while (isNotValid) {
            chosenFilename = csl.getString(1, "Choose a file: ");
            if (filenames.contains(chosenFilename)) {
                isNotValid = false;
            }
        }
        hotel.loadFromFileWithName(chosenFilename);
    }

    @Description("Shows hotel room prices")
    public static void prices(ConsoleWrapper csl, Hotel hotel) {
        if (hotel.getRooms() == null) {
            csl.print(1, NO_ROOMS_MSG);
            return;
        }
        for (Room room : hotel.getRooms()) {
            HMUtils.displayRoomInfo(room, csl, true, false);
        }
    }

    @Description("Shows description for selected room")
    public static void view(ConsoleWrapper csl, Hotel hotel) {
        if (hotel.getRooms() == null) {
            csl.print(1, NO_ROOMS_MSG);
            return;
        }
        String roomName = csl.getString(1, CHOOSE_ROOM_MSG);
        Room room = hotel.getRoom(roomName);
        if (room == null) {
            csl.print(1, NOT_FOUND_MSG);
            return;
        }
        HMUtils.displayRoomInfo(room, csl, true, true);
    }

    @Description("Checks in one or multiple guests")
    public static void checkin(ConsoleWrapper csl, Hotel hotel) {
        if (hotel.getRooms() == null) {
            csl.print(1, NO_ROOMS_MSG);
            return;
        }
        String roomName = csl.getString(1, CHOOSE_ROOM_MSG);
        Room room = hotel.getRoom(roomName);
        if (room == null) {
            csl.print(1, NOT_FOUND_MSG);
            return;
        }
        int numberOfGuests = csl.getInt(1, "Number of guests: ");
        if (numberOfGuests > room.getCapacity()) {
            csl.print(1, "Room too small!");
            return;
        }
        for (int i = 0; i < numberOfGuests; i++) {
            String name = csl.getString(2, "Name of guest #%d: ", i+1);
            String surname = csl.getString(2, "Surname of guest #%d: ", i+1);
            room.addGuest(new Guest(name, surname));
        }
        LocalDate dateOfCheckin = csl.getDate(1, "Date of checkin", "uuuu-MM-dd");
        room.setCheckInDate(dateOfCheckin);
        int daysOfStay = csl.getInt(1, "Days of stay: ");
        room.setCheckOutDate(dateOfCheckin.plusDays(daysOfStay));
    }

    @Description("Checks out a guest")
    public static void checkout(ConsoleWrapper csl, Hotel hotel) {
        if (hotel.getRooms() == null) {
            csl.print(1, NO_ROOMS_MSG);
            return;
        }
        String roomName = csl.getString(1, CHOOSE_ROOM_MSG);
        Room room = hotel.getRoom(roomName);
        if (room == null) {
            csl.print(1, NOT_FOUND_MSG);
            return;
        }
        if (room.getGuests().isEmpty()) {
            csl.print(1, "Room has no guests");
            return;
        }
        String guestnames = String.join(",", room.getGuests().stream().map(Guest::toString).toArray(String[]::new));
        long totalDays = ChronoUnit.DAYS.between(room.getCheckInDate(), LocalDate.now(ZoneId.systemDefault()));
        boolean shouldCheckout = true;
        boolean isNotValid = true;
        csl.print(1, "Amount due: %d", totalDays);
        while (isNotValid) {
            isNotValid = false;
            String y = csl.getString(1, "Are you sure about checking out %s? (Y/n): ", guestnames).toLowerCase();
            if (y.equals("n")) {
                return;
            } else if (!y.equals("y")) {
                isNotValid = true;
            }
        }
        room.removeGuests();
        csl.print(1, "Checkout of %s successful", guestnames);
    }

    @Description("Returns the information about rooms and their guests")
    public static void list(ConsoleWrapper csl, Hotel hotel) {
        if (hotel.getRooms() == null) {
            csl.print(1, NO_ROOMS_MSG);
            return;
        }
        for (Room room : hotel.getRooms()) {
            HMUtils.displayRoomInfo(room, csl, false, true);
        }
    }
    @Description("Creates a new hotel, either room-by-room, or automatically")
    public static void create(ConsoleWrapper csl, Hotel hotel) {
        csl.print(1, "Choose creation options:");
        csl.print(2, "B - Basic creator (floors, rooms per floor, hotel name)");
        csl.print(2, "A - Advanced creator (create hotel room by room)");
        csl.print(2, "Submit anything else to cancel");
        String x = csl.getString(0, ">>> ").toLowerCase();
        if (x.equals("b")) {
            hotel.hotelName = csl.getString(1, "Hotel name (currently: %s): ", hotel.hotelName);
            int numberOfFloors = csl.getInt(1, "Number of floors: ");
            int roomsPerFloor = csl.getInt(1, "Number of rooms per floor: ");
            hotel.setRooms(numberOfFloors, roomsPerFloor);
            return;
        }
        if (x.equals("a")) {
            hotel.hotelName = csl.getString(1, "Hotel name (currently: %s): ", hotel.hotelName);
            boolean  hasEnded = false;
            while (!hasEnded) {
                int floor = csl.getInt(1, "Room floor: ");
                String roomName = HMUtils.getValidRoomName(csl, floor);
                int capacity = csl.getInt(1, "Room capacity: ");
                double price = csl.getDouble(1, "Room price: ");
                String description = csl.getString(1, "Room description: ").trim();
                int roomNumber = Integer.parseInt(roomName.replaceFirst(String.valueOf(floor), ""));
                hotel.addRoom(new Room(roomName, description, price, floor, roomNumber, capacity));
                hasEnded = HMUtils.shouldMoreRoomsBeCreated(csl);
            }
        }
    }
}
