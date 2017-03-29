package seedu.typed.Schedule;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * RangeEachYear represents a particular range in a year 
 * It could be from March to April or more specifically
 * 17 March to 19 April. 
 * includes returns true if a particular date falls 
 * within this range
 * For example, 18 March falls in the range of 17 March to 19 April
 * @author YIM CHIA HUI
 *
 */
public class RangeEachYearTE extends TimeExpression {
    
    private int startMonth;
    private int endMonth;
    private int startDay;
    private int endDay;

    public RangeEachYearTE(int startMonth, int endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = 0;
        this.endDay = 0;
    }
    
    public RangeEachYearTE(int month) {
        this.startMonth = month;
        this.endMonth = month;
        this.startDay = 0;
        this.endDay = 0;
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
        if (startDay == 0) {
            return true;
        }
        return (date.getLocalDateTime().getDayOfMonth() >= startDay);
    }

    private boolean monthsInclude(DateTime date) {
        int month = date.getLocalDateTime().getMonthValue();
        return (month > startMonth && month < endMonth);
    }

}
