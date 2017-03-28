//@@author A0141094M

package seedu.typed.logic.parser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Parses date from a non-null string to LocalDateTime.
 * Currently assumes date contains only one date, not multiple.
 */
public class DateParser {
    private static final String MESSAGE_FOR_INVALID_DATE_FORMAT = "The date you entered is invalid or ambiguous. ";

    private static Parser natty = new Parser();

    /**
     * Returns a date in its LocalDateTime equivalent.
     * @param date non-null String containing a date
     * @return a LocalDateTime instance of the parsed date
     * @throws IllegalValueException if date is in an invalid or ambiguous format
     */
    public static LocalDateTime getLocalDateTimeFromString(String date) throws IllegalValueException {
        assert date != null;
        DateGroup dateGroup = getDateGroupFromString(date);
        Instant instant = dateGroup.getDates().get(0).toInstant();
        return LocalDateTime.ofInstant(instant, getSystemDefaultTimeZone());
    }

    /**
     * Parses the specified date using natty.
     * @param date non-null String containing a date
     * @return the natty-parsed date
     * @throws IllegalValueException if date is in an invalid or ambiguous format
     */
    private static DateGroup getDateGroupFromString(String date) throws IllegalValueException {
        assert date != null;
        List<DateGroup> dateGroup = natty.parse(date);
        if (!isEmptyDateGroup(dateGroup)) {
            return dateGroup.get(0);
        }
        throw new IllegalValueException(MESSAGE_FOR_INVALID_DATE_FORMAT);
    }

    /**
     * Checks if specified DateGroup is empty.
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
