package seedu.typed.schedule;

import java.time.LocalDateTime;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.parser.DateTimeParser;
import seedu.typed.model.task.DateTime;

public class ScheduleElement implements TimeExpression, Comparable<ScheduleElement> {
//@@author A0139379M
/**
 * ScheduleElement is a class that handles time and recurrence
 * In particular, it would parse the recurrence rule and base on the
 * initial dates input and determine whether it is recurring every week,
 * or every monday based on the recurring rule.
 *
 * It also help to determine whether a task is an event, deadline or floating task.
 * The dates, timeexpression and rule are final. Guarantees immutable
 *
 * @author YIM CHIA HUI
 */

    private final DateTime date; // deadlines, due dates...
    private final DateTime startDate; // start time of the event
    private final DateTime endDate; // end time of the event
    private final TimeExpression te; // representation of the recurrence

    private final String rule; // string representation of the recurrence rule
    private static final String BY_DISPLAY_IDENTIFIER = "By:";
    private static final String FROM_DISPLAY_IDENTIFIER = "From:";
    private static final String TO_DISPLAY_IDENTIFIER = "To:";

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
     *
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
     * Constructor used for Edit Command
     *
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
     *
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
        this.te = parseEventRecurrenceRule(rule);
        this.rule = rule;
    }
    /**
     * Creates a ScheduleElement that supports recurring deadlines
     *
     * @param date
     * @param rule
     */
    public ScheduleElement(DateTime date, String rule) throws IllegalValueException {
        this.date = date;
        this.startDate = null;
        this.endDate = null;
        this.te = parseDeadlineRecurrenceRule(rule);
        this.rule = rule;
    }

    /**
     * Representation of a deadline in our TaskManager
     *
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
     *
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
     * This is used in conjunction with makeDeadline
     *
     * @param rule specifies the recurrence rule
     * @throws IllegalValueException if rule is not of the given format
     */
    public ScheduleElement(String rule) throws IllegalValueException {
        this.date = DateTime.getToday();
        this.te = parseDeadlineRecurrenceRule(rule);
        this.rule = rule;
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
     *
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
     *
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
        if (te == null) {
            return false;
        }
        return te.includes(date);
    }

    /*
     * Static Methods to help ease constructing Schedule Elements
     */

    public static ScheduleElement makeEvent(DateTime startDate, DateTime endDate) {
        return new ScheduleElement(startDate, endDate);
    }

    public static ScheduleElement makeDeadline(DateTime date) {
        return new ScheduleElement(date);
    }

    // Constructing schedule elements where date is not specified
    public static ScheduleElement makeDeadline(String rule) throws IllegalValueException {
        return (new ScheduleElement(rule)).updateDate();
    }

    public static ScheduleElement makeFloating() {
        return new ScheduleElement();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleElement // instanceof handles nulls
                        && date == ((ScheduleElement) other).getDate()
                        && startDate == ((ScheduleElement) other).getStartDate()
                        && endDate == ((ScheduleElement) other).getEndDate()
                        && te == ((ScheduleElement) other).getTe()); // state check
    }

    @Override
    public DateTime nextOccurrence(DateTime dateTime) {
        if (!isRecurring()) {
            return null;
        }
        DateTime nextOccurrence = te.nextOccurrence(dateTime);
        int year = nextOccurrence.getYear();
        int month = nextOccurrence.getMonth();
        int day = nextOccurrence.getDay();
        int hr = nextOccurrence.getHour();
        int min = nextOccurrence.getMin();
        if (date != null && isDeadline()) {
            hr = date.getHour();
            min = date.getMin();
        }
        if (startDate != null && isEvent()) {
            hr = startDate.getHour();
            min = startDate.getMin();
        }
        DateTime correctOccurrence = DateTime.getDateTime(year, month, day, hr, min);
        return correctOccurrence;
    }

    public boolean isRecurring() {
        return rule != "";
    }

    /**
     * Facilitates the update of the recurring tasks' dates
     * Specifically, it will get the next occurrence of the deadline/event
     *
     * @return a scheduleElement with the updated date but with the same occurrence
     */
    public ScheduleElement updateDate() {
        if (isDeadline() && isRecurring()) {
            DateTime updatedDate = nextOccurrence(this.date);
            return new ScheduleElement(updatedDate, this.startDate, this.endDate, this.te, this.rule);
        } else if (isEvent() && isRecurring()) {
            int days = DateTime.duration(startDate, endDate);
            DateTime updatedStartDate = nextOccurrence(this.endDate); // find next occurrence after end date
            DateTime updatedEndDate = updatedStartDate.nextDays(days);
            int year = updatedEndDate.getYear();
            int month = updatedEndDate.getMonth();
            int day = updatedEndDate.getDay();
            int hr = endDate.getHour();
            int min = endDate.getMin();
            // needs to correct the end date with the original end date hour and min
            DateTime correctEndDate = DateTime.getDateTime(year, month, day, hr, min);
            return new ScheduleElement(this.date, updatedStartDate, correctEndDate, this.te, this.rule);
        } else {
            return null;
        }
    }

    //@@author A0141094M
    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        if (isEvent()) {
            display.append(" From: " + this.startDate + " To: " + this.endDate);
        } else if (isDeadline()) {
            display.append(" By: " + this.date);
        }
        if (isRecurring()) {
            display.append(" Every: " + this.rule);
        }
        return display.toString();
    }

    public String teToString() {
        return "";
    }
    //@@author

    //@@author A0139379M

    /**
     * Interprets the recurrence rule in the context of events
     *
     * @param rule which specifies the recurrence rule
     * @return TimeExpression fulfiling the Recurrence rule
     * @throws IllegalValueException if the duration of the event exceeds recurrence
     * like an event lasting 1 month can't recur every week.
     */
    private TimeExpression parseEventRecurrenceRule(String rule) throws IllegalValueException {
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
                if (duration == 0) {
                    return Recurrence.recurEveryDay();
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "week" :
                if (duration >= 0 && duration <= 7) {
                    return Recurrence.recurEveryWeek(startDayIndex, endDayIndex);
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "month" :
                if (duration >= 0 && duration <= 30) {
                    return Recurrence.recurEventEveryMonth(startDay, endDay);
                } else {
                    throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
                }
            case "year" :
                if (duration >= 0 && duration <= 365) {
                    return Recurrence.recurEveryYear(startDay, startMonth, endDay, endMonth);
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
     * Interprets the recurrence rule in the context of deadlines
     * Flexible in parsing every monday, every tuesday etc.
     *
     * @param rule which specifies the recurrence rule
     * @return TimeExpression which supports the recurrence rule
     */
    private TimeExpression parseDeadlineRecurrenceRule(String rule) throws IllegalValueException {
        if (rule.matches(FREQUENCY)) {
            // handle frequency like every day, month, week etc
            int dayIndex = date.getDayIndex();
            int day = date.getDay();
            int month = date.getMonth();
            switch (rule.trim()) {
            case "day" :
                return Recurrence.recurEveryDay();
            case "week" :
                return Recurrence.recurEveryWeek(dayIndex);
            case "month" :
                return Recurrence.recurEveryMonth(day);
            case "year" :
                return Recurrence.recurEveryYear(day, month);
            default:
                return null;
            }
        } else if (rule.matches(WEEKDAYS)) {
            // handle weekdays
            switch (rule.trim()) {
            case "monday" :
                return Recurrence.MONDAY;
            case "tuesday" :
                return Recurrence.TUESDAY;
            case "wednesday" :
                return Recurrence.WEDNESDAY;
            case "thursday" :
                return Recurrence.THURSDAY;
            case "friday" :
                return Recurrence.FRIDAY;
            case "saturday" :
                return Recurrence.SATURDAY;
            case "sunday" :
                return Recurrence.SUNDAY;
            default :
                return null;
            }
        } else {
            // invalid arguments
            throw new IllegalValueException(MESSAGE_EVERY_CONSTRAINTS);
        }
    }

    /**
     * Earlier dates are ranked higher; deadlines uses date whereas events using startDate
     * as comparison. Floating tasks are by default last.
     */
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

    //@@author

    //@@author A0143853A
    @Override
    public ScheduleElement getDuplicate() {
        DateTime dateCopy = null;
        DateTime startDateCopy = null;
        DateTime endDateCopy = null;
        TimeExpression teCopy = null;

        if (date != null) {
            dateCopy = date.getDuplicate();
        }
        if (startDate != null) {
            startDateCopy = startDate.getDuplicate();
        }
        if (endDate != null) {
            endDateCopy = endDate.getDuplicate();
        }
        if (te != null) {
            teCopy = te.getDuplicate();
        }

        return new ScheduleElement(dateCopy,
                                   startDateCopy,
                                   endDateCopy,
                                   teCopy,
                                   rule);
    }
    //@@author
}
