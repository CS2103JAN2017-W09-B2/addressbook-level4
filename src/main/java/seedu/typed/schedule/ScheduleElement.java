package seedu.typed.schedule;

import java.time.LocalDateTime;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.parser.DateTimeParser;
import seedu.typed.model.task.DateTime;

<<<<<<< HEAD
public class ScheduleElement implements TimeExpression, Comparable<ScheduleElement> {
=======
//@@author A0139379M
/**
 * ScheduleElement is a class that handles time and recurrence
 * In particular, it would parse the recurrence and determine whether
 * it is recurring every week, or every monday based on the recurring rule.
 * It also help to determine whether a task is an event, deadline or floating task.
 * The dates, timeexpression and rule are final. Guarantees immutable
 * @author YIM CHIA HUI
 */
public class ScheduleElement implements TimeExpression {
>>>>>>> 3eea7f5879d0d06b30a13c6e2c4d45de91fad0b0

    private final DateTime date; // deadlines, duedates...
    private final DateTime startDate; // start time of the event
    private final DateTime endDate; // end time of the event
    private final TimeExpression te; // representation of the recurrence
    private final String rule;
    private final String BY_DISPLAY_IDENTIFIER = "By:";
    private final String FROM_DISPLAY_IDENTIFIER = "From:";
    private final String TO_DISPLAY_IDENTIFIER = "To:";

    public static final String WEEKDAYS = "monday|tuesday|wednesday|thursday|friday|saturday|sunday";
    public static final String FREQUENCY = "day|week|month|year";
    public static final String MESSAGE_EVERY_CONSTRAINTS = "Recurring Rule is not supported.";


    // =========== ScheduleElement Constructors ====================
    // =============================================================

    /**
     * Representation of a floating task
     */
    public ScheduleElement() {
        this.date = null;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
        this.rule = "";
    }

    /**
     * Constructor with everything to help in copying the values
     * @param date
     * @param startDate
     * @param endDate
     * @param te
     * @param rule
     */
    public ScheduleElement(DateTime date, DateTime startDate,
            DateTime endDate, TimeExpression te, String rule) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = te;
        this.rule = rule;
    }

    /**
     * TODO Why do we need this? Editing to support changes from floating to other tasks?
     * @param date
     * @param startDate
     * @param endDate
     */
    public ScheduleElement(DateTime date, DateTime startDate, DateTime endDate) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = null;
        this.rule = "";
    }

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
     * Representation of a deadline when only recurrence is specified
     * but date is not specified
     * Example: Add task every monday
     * This will set task deadline starting from the upcoming monday
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


    // =========== ScheduleElement Getters =========================
    // =============================================================

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


    // =========== ScheduleElement Utility =========================
    // =============================================================


    /**
     * A helper method to help do a shallow copy of a schedule element
     * @param date
     * @param startDate
     * @param endDate
     * @param te
     * @param rule
     * @return A copy of the ScheduleElement
     */
    public ScheduleElement seToCopy(DateTime date, DateTime startDate,
            DateTime endDate, TimeExpression te, String rule) {
        return new ScheduleElement(date, startDate, endDate, te, rule);
    }

    //@@author A0141094M
    /**
     * This is to support the conversion from XML file to ScheduleElement objects
     * Returns a ScheduleElement built according to the {@code dateInput}.
     */
    public ScheduleElement parseDateString(String dateInput) throws IllegalValueException {
        if (dateInput == null || dateInput.isEmpty()) {
            return makeFloating();
        }
        if (dateInput.contains(BY_DISPLAY_IDENTIFIER)) {
            String[] dateTime = dateInput.trim().split(BY_DISPLAY_IDENTIFIER);
            return makeDeadline(DateTimeParser.getDateTimeFromString(dateTime[1]));
        }
        if (dateInput.contains(FROM_DISPLAY_IDENTIFIER) && dateInput.contains(TO_DISPLAY_IDENTIFIER)) {
            String[] dateTime = dateInput.trim().split(TO_DISPLAY_IDENTIFIER);
            dateTime[0] = dateTime[0].replaceAll(FROM_DISPLAY_IDENTIFIER, "");
            return makeEvent(DateTimeParser.getDateTimeFromString(dateTime[0]),
                    DateTimeParser.getDateTimeFromString(dateTime[1]));
        }
        return makeFloating();
    }
    //@@author

    //@@author A0139379M
    public boolean isEvent() {
        return date == null && startDate != null && endDate != null;
    }
    public boolean isDeadline() {
        return date != null && startDate == null && endDate == null;
    }
    public boolean isFloating() {
        return date == null && startDate == null && endDate == null;
    }

    /**
     * Checks whether a certain task is overdue
     * @return true if the current time is way after the end date of the event
     * or the deadline
     */
    public boolean isOverdue() {
        DateTime now = new DateTime(LocalDateTime.now());
        if (isDeadline()) {
            return now.isAfter(date);
        } else if (isEvent()) {
            return now.isAfter(endDate);
        } else {
            return false;
        }
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

    /**
     * Facilitates the update of the recurring tasks' dates
     * Specifically, it will get the next occurrence of the deadline/event
     * @return a scheduleElement with the updated date but with the same occurrence
     */
    public ScheduleElement updateDate() {
        if (isDeadline()) {
            DateTime updatedDate = nextDeadlineOccurrence(this.date);
            return new ScheduleElement(updatedDate, this.startDate, this.endDate, this.te, this.rule);
        } else if (isEvent()) {
            int days = DateTime.duration(startDate, endDate);
            DateTime updatedStartDate = nextDeadlineOccurrence(this.endDate);
            DateTime updatedEndDate = updatedStartDate.nextDays(days);
            return new ScheduleElement(this.date, updatedStartDate, updatedEndDate, this.te, this.rule);
        } else {
            return null;
        }
    }

    //@@author A0141094M
    @Override
    public String toString() {
        String display = "";
        if (isEvent()) {
            display = " From: " + this.startDate + " To: " + this.endDate;
        } else if (isDeadline()) {
            display = " By: " + this.date;
        }
        if (isRecurring()) {
            display = " Every: " + this.rule;
        }
        return display;
    }

    public String teToString() {
        return this.rule;
    }
    //@@author

    //@@author A0139379M
    // =========== TimeExpression Utility ==========================
    // =============================================================
    // Private Utility methods to faciliate ease of recurrence creation
    /*
     * TimeExpression representing the recurring deadline/event everyday
     * If event, assume that the duration of event is strictly less than a day
     */
    private TimeExpression recurEveryDay() {
        return RangeEachYearTE.year();
    }

    /*
     * TimeExpression representing the recurring deadline every week
     * @param day of the week
     */
    private TimeExpression recurEveryWeek(int dayIndex) {
        return DayInMonthTE.weekly(dayIndex);
    }

    /**
     * TimeExpression representing the recurring event every week
     * Assumes that the event is less than a week to recur
     * @param startDayIndex
     * @param endDayIndex
     * @return TimeExpression which recurs the duration of the event every week
     */
    private TimeExpression recurEveryWeek(int startDayIndex, int endDayIndex) {
        UnionTE unionTE = new UnionTE();
        for (int dayIndex = startDayIndex; dayIndex <= endDayIndex; dayIndex++) {
            unionTE.addTE(DayInMonthTE.weekly(dayIndex));
        }
        return unionTE;
    }

    /**
     * TimeExpression representing the recurring deadline every month
     * @param weekCount 1st week or 2nd week etc
     * @param dayIndex monday, tuesday?
     * @return TimeExpression that recurs a deadline every month
     */
    private TimeExpression recurEveryMonth(int weekCount, int dayIndex) {
        return DayInMonthTE.monthly(weekCount, dayIndex);
    }

    /**
     * TimeExpression representing recurring events every month
     * @param startDay of the event
     * @param endDay of the event
     * @return a TimeExpression that supports event that recurs every month
     */
    private TimeExpression recurEventEveryMonth(int startDay, int endDay) {
        UnionTE unionTE = new UnionTE();
        for (int month = 1; month <= 12; month++) {
            unionTE.addTE(new RangeEachYearTE(month, month, startDay, endDay));
        }
        return unionTE;
    }

    /**
     * TimeExpression representing the recurring deadline every year
     * @param weekCount week of the month
     * @param dayIndex the day of the week
     * @param monthCount
     * @return TimeExpression that recurs on this day every year
     */
    private TimeExpression recurEveryYear(int weekCount, int dayIndex, int monthCount) {
        DayInMonthTE day = new DayInMonthTE(weekCount, dayIndex);
        RangeEachYearTE month = new RangeEachYearTE(monthCount);
        IntersectionTE intersectionTE = new IntersectionTE(day, month);
        return intersectionTE;
    }

    /**
     * TimeExpression that supports recurrence of event every year
     * Assumes start month <= end month and in the event where
     * start month = end month, start day <= end day
     * @param startDay start day of the month
     * @param startMonth starting month
     * @param endDay end day of the month
     * @param endMonth ending month
     * @return TimeExpression that recurs this event duration every year
     */
    private TimeExpression recurEveryYear(int startDay, int startMonth, int endDay, int endMonth) {
        return new RangeEachYearTE(startMonth, endMonth, startDay, endDay);
    }

    /**
     * Parses the current rule into its respective recurring meaning
     * This is for events recurrence only
     */
    private TimeExpression parseEventRecurrenceRule(String rule) throws IllegalValueException {
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
     * Recurrence for deadlines only
     * Rule should be either a frequency or a particular weekday
     * Frequency refers to week, year, month etc
     * Weekday refers to Monday, Tuesday etc
     * @param rule which specifies the recurrence rule
     * @return TimeExpression which supports the recurrence rule
     */
    private TimeExpression parseDeadlineRecurrenceRule(String rule) throws IllegalValueException {
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
<<<<<<< HEAD

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

    public boolean isOverdue() {
        DateTime now = new DateTime(LocalDateTime.now());
        if (isDeadline()) {
            return now.isAfter(date);
        } else if (isEvent()) {
            return now.isAfter(endDate);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(ScheduleElement other) {
        // floating tasks are lowest
        // otherwise sort deadlines using date and events using startdate
        // either this or other are floating or both are floating
        if (this.isFloating() && other.isFloating()) {
            return 0;
        }
        if (this.isFloating() && !other.isFloating()) {
            return 1;
        }
        if (!this.isFloating() && other.isFloating()) {
            return -1;
        }
        // deadlines or events cases
        DateTime thisDate;
        DateTime otherDate;
        if (this.isDeadline()) {
            thisDate = this.date;
        } else {
            thisDate = this.startDate;
        }
        if (other.isDeadline()) {
            otherDate = other.date;
        } else {
            otherDate = other.startDate;
        }
        if (thisDate.isAfter(otherDate)) {
            // is date is later than the other date => show it later
            // lower priority as we want to show deadlines due soon
            return 1;
        } else if (!thisDate.isAfter(otherDate)) {
            return -1;
        } else {
            return 0;
        }
    }


=======
>>>>>>> 3eea7f5879d0d06b30a13c6e2c4d45de91fad0b0
}
