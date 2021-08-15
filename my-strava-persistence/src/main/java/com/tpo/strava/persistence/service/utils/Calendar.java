package com.tpo.strava.persistence.service.utils;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Tiberiu
 * @since 2019-01-19
 */
public final class Calendar {

    public static LocalDateTime startOfTheYear(int year) {
        return LocalDateTime.of(year, 1, 1, 0, 0, 0);
    }

    public static LocalDateTime endOfTheYear(int year) {
        return LocalDateTime.of(year, 12, 31, 23, 59, 59);
    }

    public static LocalDateTime startOfTheMonth(Month month) {
        return LocalDateTime.of(year(), month, 1, 0, 0, 0);
    }

    public static LocalDateTime endOfTheMonth(Month month) {
        return Instant.now()
                .atZone(ZoneId.systemDefault())
                .with(ChronoField.MONTH_OF_YEAR, month.getValue())
                .with(TemporalAdjusters.lastDayOfMonth())
                .with(ChronoField.HOUR_OF_DAY, 23)
                .with(ChronoField.MINUTE_OF_DAY, 59)
                .with(ChronoField.SECOND_OF_DAY, 59)
                .toLocalDateTime();
    }

    public static int year() {
        return LocalDateTime.now().getYear();
    }

    public static int lastYear() {
        return LocalDateTime.now().minusYears(1).getYear();
    }

    private Calendar() {
    }

    public static int dayOfYear() {
        return LocalDate.now().getDayOfYear();
    }
}
