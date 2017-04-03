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
    private int count;
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
        if (count > 0) {
            return weekFromStartMatches(date);
        } else {
            return weekFromEndMatches(date);
        }
    }
    private boolean weekFromEndMatches(DateTime date) {
        int daysFromMonthEnd = daysLeftInMonth(date);
        return weekInMonth(daysFromMonthEnd) == Math.abs(count);
    }
    private int daysLeftInMonth(DateTime date) {
        LocalDateTime dateTime = date.getLocalDateTime();
        int days = dateTime.getMonth().length(isLeapYear(dateTime.getYear()));
        return days - dateTime.getDayOfMonth();
    }
    private boolean weekFromStartMatches(DateTime date) {
        return this.weekInMonth(date.getLocalDateTime().getDayOfMonth()) == count;
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
     * @param count represents which week is it (negative numbers count from the back of the month
     * whereas positive numbers count from the front)
     * @param dayIndex represents which day of the week (Monday = 1 to Sunday = 7)
     */
    public DayInMonthTE(int count, int dayIndex) {
        this.count = count;
        this.dayIndex = dayIndex;
    }

}
