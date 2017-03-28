package seedu.typed.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
//@@author A0139379M
/**
 * DateTime represents our Date and Time in TaskManager using Java's LocalDateTime
 * It provides other static methods such as getting a new DateTime object one week,
 * one month or one year from now. The time zone and current time is based on User's
 * system default clock.
 *
 * @author YIM CHIA HUI
 *
 */
public class DateTime {

    private static final long LONG_ONE = (long) 1.0;

    private LocalDateTime localDateTime;

    public DateTime(LocalDateTime localDateTime) {
        super();
        this.localDateTime = localDateTime;
    }

    public DateTime() {
        this.localDateTime = LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
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

    public DateTime tomorrow() {
        LocalDateTime nextDay = this.localDateTime.plusDays(LONG_ONE);
        return new DateTime(nextDay);
    }
    public DateTime nextWeek() {
        LocalDateTime nextWeek = this.localDateTime.plusWeeks(LONG_ONE);
        return new DateTime(nextWeek);
    }

    public DateTime nextMonth() {
        LocalDateTime nextMonth = this.localDateTime.plusMonths(LONG_ONE);
        return new DateTime(nextMonth);
    }

    public DateTime nextYear() {
        LocalDateTime nextYear = this.localDateTime.plusYears(LONG_ONE);
        return new DateTime(nextYear);
    }

    public static DateTime getDateTime(int year, Month month, int day, int hr, int min) {
        return new DateTime(LocalDateTime.of(year, month, day, hr, min));
    }
    @Override
    public String toString() {
        return localDateTime.toLocalDate().toString();
    }

}
//@@author A0139379M