package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AuthUtils {
    private static String passRegex = "^[0-9a-zA-Z]{4,16}$";
    private static String loginRegex = "^[_a-zA-Z]{4,16}$";
    private static Pattern loginPattern = Pattern.compile(loginRegex);
    private static Pattern passwordPattern = Pattern.compile(passRegex);

    public static boolean isNormalLogin(String login) {
        Matcher matcher = loginPattern.matcher(login);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isNormalPass(String pass) {
        Matcher matcher = passwordPattern.matcher(pass);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }
}
