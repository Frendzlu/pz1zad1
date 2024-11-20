package org.example;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Hotel {
    public String hotelName;
    private MyMap<String, Room> rooms = new MyMap<>();

    public Hotel() {
        hotelName = "new_hotel_" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        this.rooms = new MyMap<>();
    }

    public void loadFromFileWithName(String filename) {
        File file = new File("./hotels/%s.csv".formatted(filename));
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8)) {
            try (CSVReader cr = new CSVReaderBuilder(fr)
                    .withSkipLines(1)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build()
                    ).build()) {
                String[] values = null;
                while ((values = cr.readNext()) != null) {
                    String roomname = values[0];
                    int floor = Integer.parseInt(values[1]);
                    int number = Integer.parseInt(values[2]);
                    double price = Double.parseDouble(values[3]);
                    int capacity = Integer.parseInt(values[4]);
                    String description = values[5];
                    DateTimeFormatter fIn = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.UK);
                    LocalDate checkinDate = null;
                    if (!Objects.equals(values[6], "null")) {
                        checkinDate = LocalDate.parse(values[6], fIn);
                    }
                    LocalDate checkoutDate = null;
                    if (!Objects.equals(values[7], "null")) {
                        checkoutDate = LocalDate.parse(values[7], fIn);
                    }
                    ArrayList<Guest> guests = new ArrayList<>();
                    if (!values[8].isEmpty()) {
                        for (String nameSurname : values[8].split(";")) {
                            String[] split = nameSurname.split(",");
                            guests.add(new Guest(split[0], split[1]));
                        }
                    }
                    this.addRoom(new Room(roomname, description, price, floor, number, capacity, guests, checkinDate, checkoutDate));
                }
            }
            this.hotelName = filename;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(File file, String dateformat) {
        try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8)) {
            CSVWriter cw = new CSVWriter(fw, ';',
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] headers = { "Name", "Floor", "Number", "Price", "Capacity", "Description", "Checkin", "Checkout", "Guestlist"};
            List<String[]> data = new ArrayList<String[]>();
            data.add(headers);
            for (Room room : rooms.values()) {
                String[] row = room.toCSVFileRecord(dateformat);
                data.add(row);
            }
            cw.writeAll(data);
            cw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRooms(int floors, int roomsPerFloor) {
        for (int i = 1; i <= floors; i++) {
            for (int j = 1; j <= roomsPerFloor; j++) {
                int padding = (int) (Math.log10(roomsPerFloor)+1);
                String roomName = i + String.format("%" + padding + "s", j).replace(' ', '0');
                Room room = new Room(roomName, j, i);
                rooms.put(roomName, room);
            }
        }
    }

    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    public void addRoom(Room room) {
        rooms.put(room.getRoomName(), room);
    }

    public ArrayList<Room> getRooms() {
        return (ArrayList<Room>) this.rooms.values();
    }
}
