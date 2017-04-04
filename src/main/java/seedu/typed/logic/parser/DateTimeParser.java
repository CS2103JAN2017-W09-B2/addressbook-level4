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
    private static final String MESSAGE_FOR_INVALID_DATE_FORMAT = "The date you entered is invalid or ambiguous. ";
    private static final String VALID_TIME_REGEX = "[0-2][0-3][0-5][0-9]";


    private static Parser natty = new Parser();

    /**
     * Returns a date in its Date equivalent.
     */
    public static Date getDateFromString(String date) throws IllegalValueException {
        assert date != null;
        DateGroup dateGroup = getDateGroupFromString(date);
        return dateGroup.getDates().get(0);
    }

    /**
     * Returns a date in its DateTime equivalent.
     * @param ldt LocalDateTime
     * @return a DateTime instance of the LocalDateTime parsed
     */
    public static DateTime getDateTimeFromLocalDateTime(LocalDateTime ldt) {
        return new DateTime(ldt);
    }

    /**
     * Returns a date in its DateTime equivalent.
     * @param ldt LocalDateTime
     * @return a DateTime instance of the LocalDateTime parsed
     * @throws IllegalValueException
     */
    public static DateTime getDateTimeFromString(String date) throws IllegalValueException {
        return getDateTimeFromLocalDateTime(getLocalDateTimeFromString(date));
    }

    /**
     * Returns a date in its LocalDateTime equivalent.
     * @param date non-null String containing a date
     * @return a LocalDateTime instance of the parsed date
     * @throws IllegalValueException if date is in an invalid or ambiguous format
     */
    public static LocalDateTime getLocalDateTimeFromString(String date) throws IllegalValueException {
        if (date == null) {
            return null;
        }
        DateGroup dateGroup = getDateGroupFromString(date);
        //System.out.println(dateGroup.isTimeInferred());
        Instant instant = dateGroup.getDates().get(0).toInstant();
        return LocalDateTime.ofInstant(instant, getSystemDefaultTimeZone());
    }

    public static boolean isTimeInferred(String date) throws IllegalValueException {
        System.out.println(getDateGroupFromString(date).isTimeInferred());
        return getDateGroupFromString(date).isTimeInferred();
    }

    /**
     * Parses the specified date using natty.
     * @param date non-null String containing a date
     * @return the natty-parsed date
     * @throws IllegalValueException if date is in an invalid or ambiguous format
     */
    public static DateGroup getDateGroupFromString(String date) throws IllegalValueException {
        //assert date != null;
        List<DateGroup> dateGroup = natty.parse(date);
        if (!isEmptyDateGroup(dateGroup)) {
            return dateGroup.get(0);
        }
        throw new IllegalValueException(MESSAGE_FOR_INVALID_DATE_FORMAT);
    }

    /**
     * Wrapper class pre-natty parsing that checks if the raw date string contains a time.
     * @param date
     * @return
     * @throws IllegalValueException
     */
    private static boolean containsTimeInString(String date) throws IllegalValueException {
        boolean hasTimeInString = false;
        String[] dateArgs = date.split(" ");
        for (String arg : dateArgs) {
            hasTimeInString |= isGroupOfFourDigits(arg);
        }
        return hasTimeInString;
    }

    private static boolean isGroupOfFourDigits(String arg) {
        arg = arg.replaceAll(".", "").replaceAll(":", "");
        return VALID_TIME_REGEX.matches(arg);
    }

    private static boolean isGroupOfThreeDigits(String arg) {
        arg = "0" + arg.replaceAll(".", "").replaceAll(":", "");
        return isGroupOfFourDigits(arg);
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
