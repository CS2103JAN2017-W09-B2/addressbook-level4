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
    //@@author A0141094M
    private final String BY_DISPLAY_IDENTIFIER = "By:";
    private final String FROM_DISPLAY_IDENTIFIER = "From:";
    private final String TO_DISPLAY_IDENTIFIER = "To:";
    private final String WHITESPACE_DELIMITER_REGEX = "\\s+";

    public ScheduleElement() {
        this.date = null;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
    }

    public ScheduleElement(DateTime date, DateTime startDate, DateTime endDate) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.te = null;
    }
    //@@author

    /**
     * Representation of a deadline in our TaskManager
     * @param date
     */
    public ScheduleElement(DateTime date) {
        this.date = date;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
    }
    /**
     * Representation of an event in our TaskManager
     * @param startDate
     * @param endDate
     */
    public ScheduleElement(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = null;
        this.te = null;
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
            String[] dateTime = dateInput.trim().split(WHITESPACE_DELIMITER_REGEX);
            LocalDateTime deadline = DateTimeParser.getLocalDateTimeFromString(dateTime[1]);
            return makeDeadline(DateTimeParser.getDateTimeFromLocalDateTime(deadline));
        }
        if (dateInput.contains(FROM_DISPLAY_IDENTIFIER) && dateInput.contains(TO_DISPLAY_IDENTIFIER)) {
            String[] dateTime = dateInput.trim().split(WHITESPACE_DELIMITER_REGEX);
            if (!dateTime[1].equals(TO_DISPLAY_IDENTIFIER)) {
                LocalDateTime startDateTime = DateTimeParser.getLocalDateTimeFromString(dateTime[1]);
                LocalDateTime endDateTime = DateTimeParser.getLocalDateTimeFromString(dateTime[3]);
                return makeEvent(DateTimeParser.getDateTimeFromLocalDateTime(startDateTime),
                        DateTimeParser.getDateTimeFromLocalDateTime(endDateTime));
            }
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
    //@@author
    
    /**
     * Need to be able to create time expression accordingly to fit the recurrence
     */
    
    /*
     * TimeExpression representing the recurring event everyday
     */
    public TimeExpression recurEveryDay() {
        return RangeEachYearTE.year();
    }
    
    /*
     * TimeExpression representing the recurring event every week
     */
    public TimeExpression recurEveryWeek(int dayIndex) {
        return DayInMonthTE.weekly(dayIndex);
    }
    
    /*
     * TimeExpression representing the recurring event every month
     */
    public TimeExpression recurEveryMonth(int count, int dayIndex) {
        return DayInMonthTE.monthly(count, dayIndex);
    }
}
