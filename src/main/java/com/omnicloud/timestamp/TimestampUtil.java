package com.omnicloud.timestamp;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimestampUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String now() {
        return format(LocalDateTime.now());
    }

    public String format(LocalDateTime time) {
        if (time == null) {
            return null; // better than empty string for clarity
        }
        return time.format(DATE_TIME_FORMATTER);
    }
}