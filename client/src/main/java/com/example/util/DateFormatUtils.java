package com.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatUtils {
    private static String dateFormatPattern = "dd/MM/yyyy";
    private static String dateAndTimeFormatPattern = "dd/MM/yyyy HH:mm";

    public static LocalDate parseToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
        return LocalDate.parse(date, formatter);
    }

    public static String parseToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
        return date.format(formatter);
    }

    public static String parseDateTimeToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateAndTimeFormatPattern);
        return date.format(formatter);
    }

    public static boolean isParsebaleDate(String date) {
        String dateRegex = "^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }
}
