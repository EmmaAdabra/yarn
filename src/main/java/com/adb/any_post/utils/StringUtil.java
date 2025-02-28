package com.adb.any_post.utils;

import java.util.Random;

public class StringUtil {
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
}
