package seedu.typed.schedule;

import java.time.LocalDateTime;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.parser.DateTimeParser;
import seedu.typed.model.task.DateTime;

public class ScheduleElement implements TimeExpression {

    private final DateTime date; // deadlines, duedates...
    private final DateTime startDate; // start time of the event
    private final DateTime endDate; // end time of the event
    private final TimeExpression te; // representation of the recurrence
    private final String rule;
    //@@author A0141094M
    private final String BY_DISPLAY_IDENTIFIER = "By:";
    private final String FROM_DISPLAY_IDENTIFIER = "From:";
    private final String TO_DISPLAY_IDENTIFIER = "To:";
  
    private final String WEEKDAYS = "monday|tuesday|wednesday|thursday|friday|saturday|sunday";
    private final String FREQUENCY = "day|week|month|year";
    private final String MESSAGE_EVERY_CONSTRAINTS = "Recurring Rule is not supported.";

    public ScheduleElement() {
        this.date = null;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
        this.rule = "";
    }

    public ScheduleElement(DateTime date, DateTime startDate,
            DateTime endDate, TimeExpression te, String rule) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = te;
        this.rule = rule;
    }

    public ScheduleElement(DateTime date, DateTime startDate, DateTime endDate) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = null;
        this.rule = "";
    }
    //@@author

    /**
     * Creates a ScheduleElement that supports recurring events
     * @param startDate
     * @param endDate
     * @param rule
     */
    public ScheduleElement(DateTime startDate, DateTime endDate, String rule) throws IllegalValueException {
        // handle every recurrence
        // create relevant time expression
        this.date = null;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = this.parseEventRecurrenceRule(rule);
        this.rule = rule;
    }
    /**
     * Creates a ScheduleElement that supports recurring deadlines
     * @param date
     * @param rule
     */
    public ScheduleElement(DateTime date, String rule) throws IllegalValueException {
        this.date = date;
        this.startDate = null;
        this.endDate = null;
        this.te = this.parseDeadlineRecurrenceRule(rule);
        this.rule = rule;
        System.out.println(te);
    }

    /**
     * Representation of a deadline in our TaskManager
     * @param date
     */
    public ScheduleElement(DateTime date) {
        this.date = date;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
        this.rule = "";
    }
    /**
     * Representation of an event in our TaskManager..
     * @param startDate
     * @param endDate
     */
    public ScheduleElement(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = null;
        this.te = null;
        this.rule = "";
    }

    /**
     *
     * @param every
     * @throws IllegalValueException
     */
    public ScheduleElement(String rule) throws IllegalValueException {
        this.te = parseDeadlineRecurrenceRule(rule);
        this.rule = rule;
        this.date = nextDeadlineOccurrence();
        this.startDate = null;
        this.endDate = null;
    }

    public ScheduleElement seToCopy(DateTime date, DateTime startDate,
            DateTime endDate, TimeExpression te, String rule) {
        return new ScheduleElement(date, startDate, endDate, te, rule);
    }

    //@@author A0141094M
    /**
     * Returns a ScheduleElement built according to the {@code dateInput}.
     */
    public ScheduleElement parseDateString(String dateInput) throws IllegalValueException {
        if (dateInput == null || dateInput.isEmpty()) {
            return makeFloating();
        }
        if (dateInput.contains(BY_DISPLAY_IDENTIFIER)) {
            String[] dateTime = dateInput.trim().split(BY_DISPLAY_IDENTIFIER);
            LocalDateTime deadline = DateTimeParser.getLocalDateTimeFromString(dateTime[1]);
            return makeDeadline(DateTimeParser.getDateTimeFromLocalDateTime(deadline));
        }
        if (dateInput.contains(FROM_DISPLAY_IDENTIFIER) && dateInput.contains(TO_DISPLAY_IDENTIFIER)) {
            String[] dateTime = dateInput.trim().split(TO_DISPLAY_IDENTIFIER);
            LocalDateTime startDateTime = DateTimeParser.getLocalDateTimeFromString(dateTime[0]
                    .replaceAll(FROM_DISPLAY_IDENTIFIER, ""));
            LocalDateTime endDateTime = DateTimeParser.getLocalDateTimeFromString(dateTime[1]);
            return makeEvent(DateTimeParser.getDateTimeFromLocalDateTime(startDateTime),
                    DateTimeParser.getDateTimeFromLocalDateTime(endDateTime));
        }
        return makeFloating();
    }
    //@@author

    public DateTime getDate() {
        return date;
    }
    public DateTime getStartDate() {
        return startDate;
    }
    public DateTime getEndDate() {
        return endDate;
    }
    public TimeExpression getTe() {
        return te;
    }
    public String getRule() {
        return rule;
    }
    public boolean isEvent() {
        return date == null && startDate != null && endDate != null;
    }
    public boolean isDeadline() {
        return date != null && startDate == null && endDate == null;
    }
    public boolean isFloating() {
        return date == null && startDate == null && endDate == null;
    }
    @Override
    public boolean includes(DateTime date) {
        return te.includes(date);
    }

    public static ScheduleElement makeEvent(DateTime startDate, DateTime endDate) {
        return new ScheduleElement(startDate, endDate);
    }

    public static ScheduleElement makeDeadline(DateTime date) {
        return new ScheduleElement(date);
    }

    public static ScheduleElement makeFloating() {
        return new ScheduleElement();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleElement // instanceof handles nulls
                        && this.date == ((ScheduleElement) other).getDate()
                        && this.startDate == ((ScheduleElement) other).getStartDate()
                        && this.endDate == ((ScheduleElement) other).getEndDate()
                        && this.te == ((ScheduleElement) other).getTe()); // state check
    }

    //@@author A0141094M
    @Override
    public String toString() {
        if (isEvent()) {
            return " From: " + this.startDate + " To: " + this.endDate;
        } else if (isDeadline()) {
            return " By: " + this.date;
        } else {
            return " ";
        }
    }

    public String teToString() {
        return this.rule;
    }
    //@@author

    /**
     * Need to be able to create time expression accordingly to fit the recurrence
     */

    /*
     * TimeExpression representing the recurring deadline/event everyday
     */
    public TimeExpression recurEveryDay() {
        return RangeEachYearTE.year();
    }

    /*
     * TimeExpression representing the recurring deadline every week
     */
    public TimeExpression recurEveryWeek(int dayIndex) {
        return DayInMonthTE.weekly(dayIndex);
    }
    /**
     *
     * TimeExpression representing the recurring event every week
     * Assumes that the event is less than a week to recur
     * @param startDayIndex
     * @param endDayIndex
     * @return
     */
    public TimeExpression recurEveryWeek(int startDayIndex, int endDayIndex) {
        UnionTE unionTE = new UnionTE();
        for (int dayIndex = startDayIndex; dayIndex <= endDayIndex; dayIndex++) {
            unionTE.addTE(DayInMonthTE.weekly(dayIndex));
        }
        return unionTE;
    }

    /*
     * TimeExpression representing the recurring deadline every month
     */
    public TimeExpression recurEveryMonth(int weekCount, int dayIndex) {
        return DayInMonthTE.monthly(weekCount, dayIndex);
    }

    public TimeExpression recurEventEveryMonth(int startDay, int endDay) {
        UnionTE unionTE = new UnionTE();
        for (int month = 1; month <= 12; month++) {
            unionTE.addTE(new RangeEachYearTE(month, month, startDay, endDay));
        }
        return unionTE;


    }
    /*
     * TimeExpression representing the recurring deadline every year
     */
    public TimeExpression recurEveryYear(int weekCount, int dayIndex, int monthCount) {
        DayInMonthTE day = new DayInMonthTE(weekCount, dayIndex);
        RangeEachYearTE month = new RangeEachYearTE(monthCount);
        IntersectionTE intersectionTE = new IntersectionTE(day, month);
        return intersectionTE;
    }

    public TimeExpression recurEveryYear(int startDayIndex, int startMonth, int endDayIndex, int endMonth) {
        return new RangeEachYearTE(startMonth, endMonth, startDayIndex, endDayIndex);
    }
    /**
     * Parses the current rule into its respective recurring meaning
     * This is for events recurrence only
     */
    public TimeExpression parseEventRecurrenceRule(String rule) throws IllegalValueException {
        // need to assert the range of the event fits the frequency properly
        // every day should work only for event of duration less than a day
        if (rule.matches(FREQUENCY)) {
            int duration = DateTime.duration(startDate, endDate);
            int startDayIndex = startDate.getDayIndex();
            int startDay = startDate.getDay();
            int startMonth = startDate.getMonth();

            int endDay = endDate.getDay();
            int endDayIndex = endDate.getDayIndex();
            int endMonth = endDate.getMonth();

            switch (rule.trim()) {
            case "day" :
                System.out.println("every day");
                if (duration == 0) {
                    // less than a day ok
                    return this.recurEveryDay();
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "week" :
                System.out.println("every week");
                if (duration >= 0 && duration <= 7) {
                    // within a week
                    return this.recurEveryWeek(startDayIndex, endDayIndex);
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "month" :
                System.out.println("every month");
                if (duration >= 0 && duration <= 30) {
                    return this.recurEventEveryMonth(startDay, endDay);
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "year" :
                System.out.println("every year");
                if (duration >= 0 && duration <= 365) {
                    return this.recurEveryYear(startDay, startMonth, endDay, endMonth);
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            default :
                return null;
            }
        } else {
            throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
        }
    }
    /**
     * Parses the current rule into its respective recurring meaning
     * Rule should be either a frequency or a particular weekday
     * Frequency refers to week, year, month etc
     * Weekday refers to Monday, Tuesday etc
     * @param rule
     * @return
     */
    public TimeExpression parseDeadlineRecurrenceRule(String rule) throws IllegalValueException {
        if (rule.matches(FREQUENCY)) {
            // handle frequency
            int dayIndex = date.getDayIndex();
            int weekCount = date.getWeekCount();
            int month = date.getMonth();

            switch (rule.trim()) {
            case "day" :
                System.out.println("every day");
                return this.recurEveryDay();
            case "week" :
                System.out.println("every week");
                return this.recurEveryWeek(dayIndex);
            case "month" :
                System.out.println("every month");
                return this.recurEveryMonth(weekCount, dayIndex);
            case "year" :
                System.out.println("every year");
                return this.recurEveryYear(weekCount, dayIndex, month);
            default:
                return null;
            }
        } else if (rule.matches(WEEKDAYS)) {
            // handle weekdays
            int dayIndex;
            switch (rule.trim()) {
            case "monday" :
                dayIndex = 1;
                break;
            case "tuesday" :
                dayIndex = 2;
                break;
            case "wednesday" :
                dayIndex = 3;
                break;
            case "thursday" :
                dayIndex = 4;
                break;
            case "friday" :
                dayIndex = 5;
                break;
            case "saturday" :
                dayIndex = 6;
                break;
            case "sunday" :
                dayIndex = 7;
                break;
            default :
                return null;
            }
            return this.recurEveryWeek(dayIndex);
        } else {
            // invalid arguments
            throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
        }
    }

    public DateTime nextDeadlineOccurrence() {
        return this.nextDeadlineOccurrence(DateTime.getToday());
    }

    @Override
    public DateTime nextDeadlineOccurrence(DateTime dateTime) {
        return this.te.nextDeadlineOccurrence(dateTime);
    }

    public boolean isRecurring() {
        return rule != "";
    }

    public ScheduleElement updateDate() {
        if (isDeadline()) {
            DateTime updatedDate = nextDeadlineOccurrence(this.date);
            System.out.println(updatedDate.toString());
            return new ScheduleElement(updatedDate, this.startDate, this.endDate, this.te, this.rule);
        } else if (isEvent()) {
            int days = DateTime.duration(startDate, endDate);
            DateTime updatedStartDate = nextDeadlineOccurrence(this.endDate);
            DateTime updatedEndDate = updatedStartDate.nextDays(days);
            System.out.println(updatedStartDate.toString());
            System.out.println(updatedEndDate.toString());
            return new ScheduleElement(this.date, updatedStartDate, updatedEndDate, this.te, this.rule);
        } else {
            return null;
        }
    }


}
