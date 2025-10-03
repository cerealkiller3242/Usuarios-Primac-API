package com.example.usuariosprimacapi.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Utility class for handling timezone-aware datetime operations
 */
public class DateTimeUtils {
    
    /**
     * Get current UTC LocalDateTime
     * @return Current UTC LocalDateTime
     */
    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
    
    /**
     * Convert LocalDateTime to UTC if needed
     * @param dateTime LocalDateTime to convert
     * @return UTC LocalDateTime
     */
    public static LocalDateTime toUtc(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime;
    }
}