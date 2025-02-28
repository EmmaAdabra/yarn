package com.adb.any_post.utils;

import java.time.Duration;
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
            return postDateTime.format(DateTimeFormatter.ofPattern("h:mm a, d MMM yy"));
        }
    }

    public static String shortDateFormat(Date date){
        LocalDateTime postDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(postDateTime, now);

        if (duration.getSeconds() < 60) {
            return duration.getSeconds() + "s";
        }
        else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "m";
        }
        else if (duration.toHours() < 24) {
            return duration.toHours() + "h";
        }
        else if (duration.toDays() < 7) {
            return duration.toDays() + "d";
        }
        else {
            return postDateTime.format(DateTimeFormatter.ofPattern("d MMM"));
        }
    }
}