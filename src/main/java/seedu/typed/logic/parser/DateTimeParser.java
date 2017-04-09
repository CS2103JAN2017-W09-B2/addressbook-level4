//@@author A0141094M

package seedu.typed.logic.parser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.task.DateTime;

/**
 * Parses date from a non-null string to LocalDateTime.
 * Currently assumes date contains only one date, not multiple.
 */
public class DateTimeParser {

    private static final String MESSAGE_FOR_INVALID_DATE_FORMAT = "The date you entered is invalid or ambiguous. "
            + "Try again with more specific dates! ";

    private static Parser natty = new Parser();

    /**
     * Returns a {@code date} in its Date equivalent.
     * @param {@code date} String containing a {@code date}, cannot be null
     * @return a Date instance of the {@code date} parsed
     */
    public static Date getDateFromString(String date) throws IllegalValueException {
        assert date != null;
        DateGroup dateGroup = getDateGroupFromString(date);
        return dateGroup.getDates().get(0);
    }

    /**
     * Returns a date in its DateTime equivalent.
     * @param {@code ldt} LocalDateTime representing a valid date and time, may be null
     * @return a DateTime instance of the LocalDateTime parsed
     */
    public static DateTime getDateTimeFromLocalDateTime(LocalDateTime ldt) {
        return new DateTime(ldt);
    }

    /**
     * Returns a {@code date} in its DateTime equivalent.
     * @param {@code date} String containing a {@code date}, may be null
     * @return a DateTime instance of the LocalDateTime parsed
     * @throws IllegalValueException
     */
    public static DateTime getDateTimeFromString(String date) throws IllegalValueException {
        return getDateTimeFromLocalDateTime(getLocalDateTimeFromString(date));
    }

    /**
     * Returns a {@code date} in its LocalDateTime equivalent.
     * @param {@code date} String containing a {@code date}, may be null
     * @return a LocalDateTime instance of the parsed {@code date}
     * @throws IllegalValueException if {@code date} is in an invalid or ambiguous format
     */
    public static LocalDateTime getLocalDateTimeFromString(String date) throws IllegalValueException {
        if (date == null) {
            return null;
        }
        DateGroup dateGroup = getDateGroupFromString(date);
        Instant instant = dateGroup.getDates().get(0).toInstant();
        return LocalDateTime.ofInstant(instant, getSystemDefaultTimeZone());
    }

    /**
     * Checks if natty parser inferred date in given {@code date}.
     * @param {@code date} String containing a date, may be null
     * @return true if date is inferred, false if date is specified in {@code date}
     * @throws IllegalValueException if {@code date} is in an invalid or ambiguous format
     */
    public static boolean isDateInferred(String date) throws IllegalValueException {
        return getDateGroupFromString(date).isDateInferred();
    }

    /**
     * Checks if natty parser inferred time in given {@code date}.
     * @param {@code date} String containing a date, may be null
     * @return true if time is inferred, false if time is specified in {@code date}
     * @throws IllegalValueException if {@code date} is in an invalid or ambiguous format
     */
    public static boolean isTimeInferred(String date) throws IllegalValueException {
        return getDateGroupFromString(date).isTimeInferred();
    }

    /**
     * Parses the {@code date} using natty.
     * @param {@code date} String containing a date, may be null
     * @return DateGroup from the natty-parsed {@code date}
     * @throws IllegalValueException if {@code date} is in an invalid or ambiguous format
     */
    public static DateGroup getDateGroupFromString(String date) throws IllegalValueException {
        List<DateGroup> dateGroup = natty.parse(date);
        if (!isEmptyDateGroup(dateGroup)) {
            return dateGroup.get(0);
        }
        throw new IllegalValueException(MESSAGE_FOR_INVALID_DATE_FORMAT);
    }

    /**
     * Checks if the specified {@code DateGroup} is empty.
     */
    private static boolean isEmptyDateGroup(List<DateGroup> dateGroup) {
        return dateGroup.isEmpty() || dateGroup.get(0).getDates().isEmpty();
    }

    /**
     * Returns the system's default timezone.
     */
    private static ZoneId getSystemDefaultTimeZone() {
        return ZoneId.systemDefault();
    }
}
