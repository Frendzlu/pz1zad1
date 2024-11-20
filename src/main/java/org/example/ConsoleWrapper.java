package org.example;

import java.io.Console;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

public class ConsoleWrapper {
    public Console csl;

    public ConsoleWrapper() {
        csl = System.console();
    }

    public int getInt(int indent, String message) {
        boolean isInt = false;
        int number = 0;
        while (!isInt) {
            try {
                number = Integer.parseInt(csl.readLine("\t".repeat(indent) + message));
            } catch (NumberFormatException e) {
                csl.printf("\t".repeat(indent) + "Please enter an integer.\n");
                continue;
            }
            isInt = true;
        }
        return number;
    }

    public double getDouble(int indent, String message) {
        boolean isDouble = false;
        double number = 0;
        while (!isDouble) {
            try {
                number = Double.parseDouble(csl.readLine("\t".repeat(indent) + message));
            } catch (NumberFormatException e) {
                csl.printf("\t".repeat(indent) + "Please enter a numeric value.\n");
                continue;
            }
            isDouble = true;
        }
        return number;
    }

    public String getString(int indent, String message) {
        return csl.readLine("\t".repeat(indent) + message);
    }

    public String getString(int indent, String format, Object... args) {
        return csl.readLine("\t".repeat(indent) + format, args);
    }

    public void print(int indent, String format, Object... args) {
        csl.printf("\t".repeat(indent) + format + "\n", args);
    }

    public void print(int indent, String message) {
        csl.printf("\t".repeat(indent) + message + "\n");
    }

    public void print(String message) {
        csl.printf(message + "\n");
    }

    public LocalDate getDate(int indent, String message, String dateformat) {
        boolean isRightString = false;
        LocalDate ld = LocalDate.now(ZoneId.systemDefault());
        while (!isRightString) {
            String dateAsString = this.getString(1, "%s (%s, leave empty for current date): ", message, dateformat);
            DateTimeFormatter fIn = DateTimeFormatter.ofPattern( dateformat, Locale.UK );
            ld = LocalDate.parse(LocalDate.now(ZoneId.systemDefault()).format(fIn), fIn);
            if (Objects.equals(dateAsString, "")) {
                return ld;
            }
            try {
                ld = LocalDate.parse(dateAsString, fIn);
            } catch (DateTimeParseException e) {
                csl.printf("\t".repeat(indent) + "Please enter a correct, correctly formatted date.\n");
            }
            isRightString = true;
        }
        return ld;
    }

    public LocalDate getDate(String message, String dateformat) {
        return this.getDate(0, message, dateformat);
    }
}
