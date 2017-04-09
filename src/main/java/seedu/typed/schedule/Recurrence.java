package seedu.typed.schedule;

import seedu.typed.logic.commands.util.Day;
//@@author A0139379M
/**
 * A factory that handles recurrence rules and returns the
 * correct TimeExpression that matches the rules.
 *
 * @author YIM CHIA HUI
 *
 */
public class Recurrence {

    /**
     * Fixed recurrence rules for every monday, tuesday etc.
     *
     */
    public static final TimeExpression MONDAY = recurEveryWeek(Day.MON);
    public static final TimeExpression TUESDAY = recurEveryWeek(Day.TUE);
    public static final TimeExpression WEDNESDAY = recurEveryWeek(Day.WED);
    public static final TimeExpression THURSDAY = recurEveryWeek(Day.THU);
    public static final TimeExpression FRIDAY = recurEveryWeek(Day.FRI);
    public static final TimeExpression SATURDAY = recurEveryWeek(Day.SAT);
    public static final TimeExpression SUNDAY = recurEveryWeek(Day.SUN);

    /*
     * TimeExpression representing the recurring deadline/event everyday
     * If event, assume that the duration of event is strictly less than a day
     *
     */
    public static TimeExpression recurEveryDay() {
        return RangeEachYearTE.year();
    }

    /*
     * TimeExpression representing the recurring deadline every week
     * This is for flexible time expressions where recur every monday for example
     *
     * @param day of the week
     */
    private static TimeExpression recurEveryWeek(Day day) {
        return DayInMonthTE.weekly(day.day());
    }

    /*
     * TimeExpression representing the recurring deadline every week
     * This is for flexible time expressions where recur every monday for example
     *
     * @param day of the week
     */
    public static TimeExpression recurEveryWeek(int dayIndex) {
        return DayInMonthTE.weekly(dayIndex);
    }

    /**
     * TimeExpression representing the recurring event every week
     * Assumes that the event is less than a week to recur
     *
     * @param startDayIndex
     * @param endDayIndex
     * @return TimeExpression which recurs the duration of the event every week
     */
    public static TimeExpression recurEveryWeek(int startDayIndex, int endDayIndex) {
        UnionTE unionTE = new UnionTE();
        for (int dayIndex = startDayIndex; dayIndex <= endDayIndex; dayIndex++) {
            unionTE.addTE(DayInMonthTE.weekly(dayIndex));
        }
        return unionTE;
    }

    /**
     * TimeExpression representing the recurring deadline every month
     * where it is exactly the same day but 1 month later each occurrence
     *
     * @param day which day of the month
     * @return TimeExpression that recurs a deadline exactly 1 month later
     */
    public static TimeExpression recurEveryMonth(int day) {
        UnionTE unionTE = new UnionTE();
        for (int month = 1; month <= 12; month++) {
            RangeEachYearTE dayInAMonth = new RangeEachYearTE(month, month, day, day);
            unionTE.addTE(dayInAMonth);
        }
        return unionTE;
    }

    /**
     * TimeExpression representing recurring events every month
     *
     * @param startDay of the event
     * @param endDay of the event
     * @return a TimeExpression that supports event that recurs every month
     */
    public static TimeExpression recurEventEveryMonth(int startDay, int endDay) {
        UnionTE unionTE = new UnionTE();
        for (int month = 1; month <= 12; month++) {
            unionTE.addTE(new RangeEachYearTE(month, month, startDay, endDay));
        }
        return unionTE;
    }

    /**
     * TimeExpression representing the recurring deadline every year
     * recurEveryYear(30, 04) next occurrence will be 30th April 1 year later
     *
     * @param day
     * @param month
     * @return TimeExpression that recurs on this exact day every year
     */
    public static TimeExpression recurEveryYear(int day, int month) {
        RangeEachYearTE dayInAMonth = new RangeEachYearTE(month, month, day, day);
        return dayInAMonth;
    }

    /**
     * TimeExpression that supports recurrence of event every year
     * Assumes start month <= end month and in the event where
     * start month = end month, start day <= end day
     *
     * @param startDay start day of the month
     * @param startMonth starting month
     * @param endDay end day of the month
     * @param endMonth ending month
     * @return TimeExpression that recurs this event duration every year
     */
    public static TimeExpression recurEveryYear(int startDay, int startMonth, int endDay, int endMonth) {
        return new RangeEachYearTE(startMonth, endMonth, startDay, endDay);
    }
}
