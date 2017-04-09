package seedu.typed.logic.parser;
//@@author A0141094M-unused

/**
 * Wrapper methods to improve natty parsing.
 * Subsequently unused upon stricter restrictions placed on the dates allowed.
 */

public class NattyWrapper {

    private final String VALID_TIME_REGEX = "[0-2][0-3][0-5][0-9]";
    private final String EMPTY_STRING = "";
    private final String WHITESPACE_DELIMITER = "\\s+";
    private final String COLON_DELIMITER = ":";
    private final String FULLSTOP_DELIMITER = ".";
    private final String ZERO_STRING = "0";

    /**
     * Wrapper class for natty that checks if the {@code date} contains a time.
     * @param {@code date} String containing a date, may be null
     * @return true if {@code date} contains a time
     */
    public boolean containsTimeInString(String date) {
        if (date == null) {
            return false;
        }
        boolean hasTimeInString = false;
        String[] dateArgs = date.split(WHITESPACE_DELIMITER);
        for (String arg : dateArgs) {
            hasTimeInString |= isGroupOfFourDigits(arg);
            hasTimeInString |= isGroupOfThreeDigits(arg);
        }
        return hasTimeInString;
    }

    /**
     * Wrapper class for natty that checks if {@code arg} is a time expressed as a group of four digits.
     * @param {@code arg} non-null String
     * @return true if {@code arg} is a group of four digits representative of a valid time
     */
    private boolean isGroupOfFourDigits(String arg) {
        assert arg != null;
        arg = arg.replaceAll(FULLSTOP_DELIMITER, EMPTY_STRING).replaceAll(COLON_DELIMITER, EMPTY_STRING);
        return VALID_TIME_REGEX.matches(arg);
    }

    /**
     * Wrapper class for natty that checks if {@code arg} is a group of three digits that can be
     * processed to represent a valid time.
     * @param {@code arg} non-null String
     * @return true if {@code args} is a group of three digits representative of a valid time
     */
    private boolean isGroupOfThreeDigits(String arg) {
        assert arg != null;
        arg = ZERO_STRING + arg;
        return isGroupOfFourDigits(arg);
    }

}
