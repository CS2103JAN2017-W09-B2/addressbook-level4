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
        if (date.getMonth() != endMonth) {
            return false;
        }
        if (endDay == DateTime.getLastDayOfMonth(endMonth)) {
            return true;
        }
        return (date.getDay() <= endDay && date.getDay() >= startDay);
    }

    private boolean startMonthIncludes(DateTime date) {
        System.out.println(date.toString());
        int day = date.getDay();
        if (date.getMonth() != startMonth) {
            return false;
        }
        if (startDay == 1) {
            // check if endMonth == startMonth
            if (endMonth > startMonth) {
                return true;
            } else {
                return day <= endDay;
            }
        }
        return (day >= startDay && day <= endDay);
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
    public DateTime nextOccurrence(DateTime date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        DateTime dayAfterDate = date.nextDays(1);
        DateTime startDate = DateTime.getDateTime(year, startMonth, startDay, 0, 0);
        DateTime yearAfterStartDate = startDate.nextYear();

        if (month < startMonth || month > endMonth) {
            // next occurrence is startMonth
            if (month < startMonth) {
                // current year
                return startDate;
            } else {
                // next year start Month
                return yearAfterStartDate;
            }
        } else {
            // month is in the start month to end month period
            if (month == startMonth) {
                // if month same as start month, check if it is before start date
                if (day < startDay) {
                    return startDate;
                } else {
                    // if it's after start date, maybe endMonth is next Month
                    if (endMonth > startMonth) {
                        return dayAfterDate;
                    } else {
                        // endMonth == startMonth
                        // check if day < endDay
                        if (day < endDay) {
                            return dayAfterDate;
                        } else {
                            // day >= endDay means next occurrence is next year
                            return yearAfterStartDate;
                        }
                    }
                }
            } else if (month == endMonth) {
                if (day >= endDay) {
                    return yearAfterStartDate;
                } else {
                    return dayAfterDate;
                }
            } else {
                // month falls within start month and end month
                return dayAfterDate;
            }
        }
        /*if (day < startDay) {
                // start month start date
                return DateTime.getDateTime(year, startMonth, startDay, 0, 0);
            } else if (day > endDay && endMonth == month) {
                // next occurrence is next year
                System.out.println("huh");
                return DateTime.getDateTime(year + 1, startMonth, startDay, 0, 0);
            } else {
                // within occurrence.. check if next day is within occurrence
                // if includes nextday, return next day
                DateTime nextDay = date.nextDays(1);
                System.out.println(date.toString());
                if (includes(nextDay)) {
                    return nextDay;
                } else {
                    // if doesn't include nextday means next occurrence is next year
                    return DateTime.getDateTime(year + 1, startMonth, startDay, 0, 0);
                }
            }*/
    }

}
