package org.fnt.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTransferUtils {
    private static String dateFormatPattern = "dd/MM/yyyy";
    private static String dateAndTimeFormatPattern = "dd/MM/yyyy HH:MM";

    public static LocalDate getDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
        Date parseDate = null;
        try {
            parseDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Неверный формат даты");
            return LocalDate.now();
        }
        return parseDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
