package com.project.fruit114net.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public final class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("DateUtil class");
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date createDate(int year, int month, int day) {
        return localDateToDate(LocalDate.of(year, month, day));
    }


    public static Date createDate(int year, int month, int day, int hour, int min, int sec) {
        return localDateTimeToDate(LocalDateTime.of(year, month, day, hour, min, sec));
    }


    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date toEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Long countBetweenDate(Date start, Date end){
        LocalDate localStart = dateToLocalDate(start);
        LocalDate localEnd = dateToLocalDate(end);
        return DAYS.between(localStart, localEnd);
    }

    public static boolean isSameDateTime(Date date1, Date date2) {
        LocalDateTime localDateTime1 = dateToLocalDateTime(date1).withNano(0);
        LocalDateTime localDateTime2 = dateToLocalDateTime(date2).withNano(0);
        return localDateTime1.equals(localDateTime2);
    }

    public static boolean isSameDate(Date date1, Date date2) {
        LocalDate localDate1 = dateToLocalDate(date1);
        LocalDate localDate2 = dateToLocalDate(date2);
        return localDate1.equals(localDate2);
    }


    public static Date addDays(Date date, int daysToAdd) {
        LocalDate localDate = dateToLocalDate(date);
        return localDateToDate(localDate.plusDays(daysToAdd));
    }

    public static Date subtractDays(Date date, int daysToSubtract) {
        LocalDate localDate = dateToLocalDate(date);
        return localDateToDate(localDate.minusDays(daysToSubtract));
    }

    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
