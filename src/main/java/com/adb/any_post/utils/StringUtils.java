package com.adb.any_post.utils;

import java.util.Random;

public class StringUtils {
    public static String getUserInitial(String firstName, String lastName){
        return (firstName.charAt(0) + String.valueOf(lastName.charAt(0)));
    }

    public static String getUSerFullName(String firstName, String lastName){
        return firstName + " " + lastName;
    }

    public static String randomAvatarUrl(){
        String[] avatarStyles = {"pixel-art", "bottts", "identicon", "adventurer"};
        String[] names = {"Emmanuel", "Jonathan", "Goodness", "Liberty", "Esther", "Victoria", "Happiness"};
        Random random = new Random();

        return "https://api.dicebear.com/7.x/" + avatarStyles[random.nextInt(4)] + "/svg?seed=" + names[random.nextInt(7)];
    }

    public static String maskEmail(String email) {
        String [] splitEmail = email.split("@");
        String emailName = splitEmail[0];
        String emailDomain = splitEmail[1];
        int emailNameLength = emailName.length();
        int maskLength = emailNameLength / 2;

        maskLength = emailNameLength % 2 > 0 ? maskLength + 1 : maskLength;
        String maskCharacter = "*".repeat(maskLength);

        return maskCharacter + emailName.substring(maskLength) + "@" + emailDomain;
    }

    public static String linkify(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String URL_REGEX = "(https?://[\\w\\-._~:/?#@!$&'()*+,;=]+)";
        return text.replaceAll(URL_REGEX, "<a href=\"$1\" target=\"_blank\">$1</a>");
    }

    public static String trimToNull(String input){
        return input == null ? null : input.trim();
    }
}
