package com.omnicloud.timestamp;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimestampUtil {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String now() {
        return LocalDateTime.now().format(FORMAT);
    }

    public String format(LocalDateTime time) {
        return time != null ? time.format(FORMAT) : "";
    }
}