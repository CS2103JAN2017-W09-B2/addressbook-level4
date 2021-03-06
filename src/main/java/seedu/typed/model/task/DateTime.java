package seedu.typed.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

//@@author A0139379M
/**
 * DateTime represents our Date and Time in TaskManager using Java's LocalDateTime
 * It provides other static methods such as getting a new DateTime object one week,
 * one month or one year from now. The time zone and current time is based on User's
 * system default clock.
 * Guarantees: immutable;
 * @author YIM CHIA HUI
 *
 */
public class DateTime {

    //@@author A0141094M
    private static final String SINGLE_SPACE_DELIMITER = " ";
    private static final String DISPLAY_DATE_TIME_FORMAT = "EEEE, MMM dd, yyyy HH:mm";
    //@@author

    private static final long LONG_ONE = (long) 1.0;

    private final LocalDateTime localDateTime;

    public DateTime(LocalDateTime localDateTime) {
        super();
        this.localDateTime = localDateTime;
    }

    public DateTime() {
        this.localDateTime = null;
    }

    public static DateTime parseDateString(String date) {
        // assume in dd/mm/yyyy format
        String[] dates = date.trim().split("-");
        System.out.println(dates[0]);
        int year = Integer.valueOf(dates[0]);
        int month = Integer.valueOf(dates[1]);
        int day = Integer.valueOf(dates[2]);
        return DateTime.getDateTime(year, month, day, 0, 0);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public boolean equals(DateTime other) {
        LocalDateTime self = this.localDateTime;
        LocalDateTime others = other.localDateTime;
        return (self.getDayOfMonth() == others.getDayOfMonth())
                && (self.getMonth() == others.getMonth())
                && (self.getYear() == others.getYear())
                && (self.getHour() == others.getHour())
                && (self.getMinute() == others.getMinute())
                && (self.getSecond() == others.getSecond());

    }

    public boolean isAfter(DateTime other) {
        return this.localDateTime.isAfter(other.getLocalDateTime());
    }

    public boolean isToday() {
        LocalDate today = LocalDate.now();
        return this.localDateTime.toLocalDate().equals(today);
    }

    /*
    public static DateTime getTomorrow() {
        LocalDateTime nextDay = LocalDateTime.now(defaultClock).plusDays(LONG_ONE);
        return new DateTime(nextDay);
    }
    public static DateTime getNextWeek() {
        LocalDateTime nextWeek = LocalDateTime.now(defaultClock).plusWeeks(LONG_ONE);
        return new DateTime(nextWeek);
    }

    public static DateTime getNextMonth() {
        LocalDateTime nextMonth = LocalDateTime.now(defaultClock).plusMonths(LONG_ONE);
        return new DateTime(nextMonth);
    }

    public static DateTime getNextYear() {
        LocalDateTime nextYear = LocalDateTime.now(defaultClock).plusYears(LONG_ONE);
        return new DateTime(nextYear);
    }
     */

    public int getHour() {
        return this.localDateTime.getHour();
    }

    public int getMin() {
        return this.localDateTime.getMinute();
    }
    public int getDayIndex() {
        return this.localDateTime.getDayOfWeek().getValue();
    }

    public int getDay() {
        return this.localDateTime.getDayOfMonth();
    }

    public int getWeekCount() {
        int daysFromMonthStart = this.localDateTime.getDayOfMonth();
        return weekInMonth(daysFromMonthStart);
    }

    public int getMonth() {
        return this.localDateTime.getMonthValue();
    }

    public int getYear() {
        return this.localDateTime.getYear();
    }

    private int weekInMonth(int dayNumber) {
        return ((dayNumber - 1) / 7) + 1;
    }
    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static int getLastDayOfMonth(int month) {
        int numDays = 0;
        int year = LocalDateTime.now().getYear();
        switch (month) {
        case 1: case 3: case 5:
        case 7: case 8: case 10:
        case 12:
            numDays = 31;
            break;
        case 4: case 6:
        case 9: case 11:
            numDays = 30;
            break;
        case 2:
            if (isLeapYear(year)) {
                numDays = 29;
            } else {
                numDays = 28;
            }
            break;
        }
        return numDays;
    }

    public DateTime tomorrow() {
        LocalDateTime nextDay = this.localDateTime.plusDays(LONG_ONE);
        return new DateTime(nextDay);
    }
    public DateTime nextDays(int count) {
        LocalDateTime nextDays = this.localDateTime.plusDays(count);
        return new DateTime(nextDays);
    }
    public DateTime nextWeek() {
        LocalDateTime nextWeek = this.localDateTime.plusWeeks(LONG_ONE);
        return new DateTime(nextWeek);
    }

    public DateTime nextWeeks(int count) {
        LocalDateTime nextFewWeeks = this.localDateTime.plusWeeks(count);
        return new DateTime(nextFewWeeks);
    }

    public DateTime nextMonth() {
        LocalDateTime nextMonth = this.localDateTime.plusMonths(LONG_ONE);
        return new DateTime(nextMonth);
    }

    public DateTime nextYear() {
        LocalDateTime nextYear = this.localDateTime.plusYears(LONG_ONE);
        return new DateTime(nextYear);
    }

    public static DateTime getToday() {
        return new DateTime(LocalDateTime.now());
    }

    public static DateTime getDateTime(int year, Month month, int day, int hr, int min) {
        return new DateTime(LocalDateTime.of(year, month, day, hr, min));
    }

    public static DateTime getDateTime(int year, int month, int day, int hr, int min) {
        return new DateTime(LocalDateTime.of(year, month, day, hr, min));
    }

    public static int duration(DateTime date, DateTime other) {
        Long duration;
        if (date.isAfter(other)) {
            duration = other.getLocalDateTime().until(date.getLocalDateTime(), ChronoUnit.DAYS);
            return duration.intValue();
        } else {
            duration = date.getLocalDateTime().until(other.getLocalDateTime(), ChronoUnit.DAYS);
            return duration.intValue();
        }
    }

    //@@author A0141094M
    @Override
    public String toString() {
        if (localDateTime ==  null) {
            return SINGLE_SPACE_DELIMITER;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(DISPLAY_DATE_TIME_FORMAT));
    }

    public boolean isAfterNow() {
        return localDateTime.isAfter(LocalDateTime.now());
    }
    //@@author

    //@@author A0143853A
    public DateTime getDuplicate() {
        LocalDateTime copy = localDateTime.plusYears(0);
        return new DateTime(copy);
    }
    //@@author

}
