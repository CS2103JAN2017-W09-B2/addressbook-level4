package seedu.typed.schedule;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * RangeEachYear represents a particular range in a year
 * It could be from March to April or more specifically
 * 17 March to 19 April.
 * includes returns true if a particular date falls
 * within this range
 * For example, 18 March falls in the range of 17 March to 19 April
 *
 * Caveat: startDay or endDay could be 0 implying the whole of the month is allowed
 * @author YIM CHIA HUI
 *
 */
public class RangeEachYearTE implements TimeExpression {

    private int startMonth;
    private int endMonth;
    private int startDay;
    private int endDay;

    /**
     * Range of months where startDay = 1 and endDay = last day of the endMonth
     * @param startMonth
     * @param endMonth
     */
    public RangeEachYearTE(int startMonth, int endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = 1;
        this.endDay = DateTime.getLastDayOfMonth(endMonth);
    }

    /**
     * Range of 1 month where startDay = 1 and endDay = last day of the month
     * @param month
     */
    public RangeEachYearTE(int month) {
        this.startMonth = month;
        this.endMonth = month;
        this.startDay = 1;
        this.endDay = DateTime.getLastDayOfMonth(endMonth);
    }

    public RangeEachYearTE(int startMonth, int endMonth, int startDay, int endDay) {
        super();
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    @Override
    public boolean includes(DateTime date) {
        return monthsInclude(date) ||
                startMonthIncludes(date) ||
                endMonthIncludes(date);
    }

    private boolean endMonthIncludes(DateTime date) {
        if (date.getLocalDateTime().getMonthValue() != endMonth) {
            return false;
        }
        if (endDay == 0) {
            return true;
        }
        return (date.getLocalDateTime().getDayOfMonth() <= endDay);
    }

    private boolean startMonthIncludes(DateTime date) {
        if (date.getLocalDateTime().getMonthValue() != startMonth) {
            return false;
        }
        if (startDay == 1) {
            return true;
        }
        return (date.getLocalDateTime().getDayOfMonth() >= startDay);
    }

    private boolean monthsInclude(DateTime date) {
        int month = date.getMonth();
        return (month > startMonth && month < endMonth);
    }

    /*
     * Represents every day in the year, every year
     */
    public static RangeEachYearTE year() {
        // From Jan 1st to Dec 31st
        return new RangeEachYearTE(1, 12, 1, 31);
    }

    @Override
    public DateTime nextDeadlineOccurrence(DateTime date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        if (month < startMonth || month > endMonth) {
            // next occurrence is startMonth
            if (month < startMonth) {
                // current year
                return DateTime.getDateTime(year, startMonth, startDay, 0, 0);
            } else {
                // next year start Month
                return DateTime.getDateTime(year + 1, startMonth, startDay, 0, 0);
            }
        } else {
            // month is in the start month to end month period
            if (day < startDay) {
                // start month start date
                return DateTime.getDateTime(year, startMonth, startDay, 0, 0);
            } else if (day > endDay && endMonth == month) {
                // next occurrence is next year
                return DateTime.getDateTime(year + 1, startMonth, startDay, 0, 0);
            } else {
                // within occurrence.. check if next day is within occurrence
                // if includes nextday, return next day
                DateTime nextDay = date.nextDays(1);
                if (includes(nextDay)) {
                    return nextDay;
                } else {
                    // if doesn't include nextday means next occurrence is next year
                    return DateTime.getDateTime(year + 1, startMonth, startDay, 0, 0);
                }
            }
        }
    }
    //@@author

    //@@author A0143853A
    @Override
    public TimeExpression getDuplicate() {
        return new RangeEachYearTE(startMonth, endMonth, startDay, endDay);
    }
    //@@author

}
