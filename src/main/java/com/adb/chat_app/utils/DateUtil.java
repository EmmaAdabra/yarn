package com.adb.chat_app.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
    public static String formatPostDate(Date date) {
        LocalDateTime postDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (postDateTime.toLocalDate().equals(now.toLocalDate())) {
            // If today: "Today, 3:50"
            return "Today at " + postDateTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } else if (postDateTime.toLocalDate().equals(now.minus(1, ChronoUnit.DAYS).toLocalDate())) {
            // If yesterday: "Yesterday, 8:00"
            return "Yesterday at " + postDateTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } else {
            // Else: "February 12, at 3:17"
            return postDateTime.format(DateTimeFormatter.ofPattern("MMMM d, 'at' h:mm a"));
        }
    }
}