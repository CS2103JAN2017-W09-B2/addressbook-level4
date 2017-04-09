package seedu.typed.schedule;

import java.time.LocalDateTime;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * Represents a particular day in the month
 * Specified by the week and the day.
 * In particular, it is best used for every [weekday|weekend]
 * Example : every first monday would match
 * monday and first week in DayInMonthTE which holds
 * true either on January or December
 **/

public class DayInMonthTE implements TimeExpression {
    private int weekCount;
    private int dayIndex;

    /**
     * Returns true if the same week and same day of the week
     */
    @Override
    public boolean includes(DateTime date) {
        return dayMatches(date) && weekMatches(date);
    }
    /**
     * Week count of a day refers to which week does it belong to:
     * is it the 1st week or 2nd week of the month
     * @param date
     * @return true if the week count matches the week count of the date
     */
    private boolean weekMatches(DateTime date) {
        if (weekCount > 0) {
            return weekFromStartMatches(date);
        } else {
            return weekFromEndMatches(date);
        }
    }
    private boolean weekFromEndMatches(DateTime date) {
        int daysFromMonthEnd = daysLeftInMonth(date);
        return weekInMonth(daysFromMonthEnd) == Math.abs(weekCount);
    }
    private int daysLeftInMonth(DateTime date) {
        LocalDateTime dateTime = date.getLocalDateTime();
        int days = dateTime.getMonth().length(isLeapYear(dateTime.getYear()));
        return days - dateTime.getDayOfMonth();
    }
    private boolean weekFromStartMatches(DateTime date) {
        return this.weekInMonth(date.getLocalDateTime().getDayOfMonth()) == weekCount;
    }
    private boolean dayMatches(DateTime date) {
        return date.getLocalDateTime().getDayOfWeek().getValue() == dayIndex;
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
    /**
     * For example, DayInMonthTE(1, 3) would refer to every first Wednesday
     * @param weekCount represents which week is it (negative numbers count from the back of the month
     * whereas positive numbers count from the front)
     * @param dayIndex represents which day of the week (Monday = 1 to Sunday = 7)
     */
    public DayInMonthTE(int weekCount, int dayIndex) {
        this.weekCount = weekCount;
        this.dayIndex = dayIndex;
    }

    /*
     * Represent every day in a given weekCount
     * @param weekCount represents which week is it (1st week, 2nd week)
     */
    public static UnionTE week(int weekCount) {
        UnionTE unionTE = new UnionTE();
        for (int dayIndex = 1; dayIndex <= 7; dayIndex++) {
            DayInMonthTE te = new DayInMonthTE(weekCount, dayIndex);
            unionTE.addTE(te);
        }
        return unionTE;
    }

    /*
     * Represent a single day every week
     * It will match up to 5 similar days (monday, tuesday ...)
     * in a month
     */
    public static TimeExpression weekly(int dayIndex) {
        UnionTE unionTE = new UnionTE();
        for (int weekCount = 1; weekCount <= 5; weekCount++) {
            TimeExpression week = new DayInMonthTE(weekCount, dayIndex);
            unionTE.addTE(week);
        }
        return unionTE;
    }
    /*
     * Represent a day in a month
     * It will match only a single day in a month
     */
    public static TimeExpression monthly(int count, int dayIndex) {
        return new DayInMonthTE(count, dayIndex);
    }
    @Override
    public DateTime nextOccurrence(DateTime date) {
        int dayIndex = date.getDayIndex();
        int weekCount = date.getWeekCount();
        int month = date.getMonth();
        int year = date.getYear();
        int weekDiff = this.weekCount - weekCount;
        int dayDiff = this.dayIndex - dayIndex;
        if (month == 12) {
            year++;
            month = 1;
        } else {
            month++;
        }
        DateTime firstDayOfNextMonth = DateTime.getDateTime(year, month, 1, 0, 0);
        // find the minimum number of days to reach same dayIndex and weekCount
        // assumes this.weekCount is positive first
        if (weekDiff > 0) {
            // it's next few weeks...
            if (dayDiff > 0) {
                // next few weeks + few days
                DateTime nextFewWeeks = date.nextWeeks(weekDiff);
                return nextFewWeeks.nextDays(dayDiff);
            } else if (dayDiff < 0) {
                // next few weeks - 1 week + few days
                DateTime nextFewWeeks = date.nextWeeks(weekDiff);
                return nextFewWeeks.nextDays(dayDiff);
            } else {
                // same dayIndex so next occurrence is few weeks later
                return date.nextWeeks(weekDiff);
            }
        } else if (weekDiff < 0) {
            // it's actually before us so need nex month
            // finish up the month and get the next deadline
            if (includes(firstDayOfNextMonth)) {
                return firstDayOfNextMonth;
            } else {
                return nextOccurrence(firstDayOfNextMonth);
            }
        } else {
            // same week
            // need to check if wednesday is really after monday BECAUSE 1 MARCH IS WEDNESDAY BUT 6 MARCH IS MONDAY :(
            if (dayDiff > 0) {
                // next few days
                DateTime nextFewDays = date.nextDays(dayDiff);
                if (!includes(nextFewDays)) {
                    // first wednesday comes before first monday
                    // first sunday comes before first monday also
                    // so need next month
                    System.out.println(date.toString());
                    return nextOccurrence(firstDayOfNextMonth);
                } else {
                    return date.nextDays(dayDiff);
                }
            } else if (dayDiff < 0) {
                // next week - dayDiff
                DateTime nextFewWeeks = date.nextWeek();
                return nextFewWeeks.nextDays(dayDiff);
            } else {
                // same week same day
                // return next week if included in time expression
                DateTime nextWeek = date.nextWeek();
                if (this.includes(nextWeek)) {
                    return nextWeek;
                } else {
                    return nextOccurrence(nextWeek);
                }
            }
        }
    }
}
