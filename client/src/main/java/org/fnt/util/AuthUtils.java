package org.fnt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AuthUtils {
    private static String passRegex = "^[0-9a-zA-Z]{4,16}$";
    private static String loginRegex = "^[_a-zA-Z]{4,16}$";

    public static boolean isNormalLogin(String login) {
        Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(login);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isNormalPass(String pass) {
        Pattern pattern = Pattern.compile(passRegex);
        Matcher matcher = pattern.matcher(pass);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }
}
