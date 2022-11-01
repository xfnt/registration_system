package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhoneFormatUtils {

//    private static String regex = "^([+7]|(?![+])|[8]){1,2}[0-9]{10}$";
    private static String regex = "^[+7]{2}[0-9]{10}$";
    private static Pattern pattern = Pattern.compile(regex);

    // Пока не нужный метод, но возможно с его помощью сделаю читабильное отображение телефонов
    @Deprecated
    public static String getFormattedPhone(String line) {
//        "+7 (000) 000-0000";
        StringBuilder builder = new StringBuilder(17);
        builder.append(line.substring(0,2));
        builder.append(" ");
        builder.append("(");
        builder.append(line.substring(2, 5));
        builder.append(")");
        builder.append(" ");
        builder.append(line.substring(5, 8));
        builder.append("-");
        builder.append(line.substring(8));
        return builder.toString();
    }

    public static boolean isParsablePhone(String line) {
        Matcher matcher = pattern.matcher(line);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }
}
