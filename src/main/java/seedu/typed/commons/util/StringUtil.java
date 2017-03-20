package seedu.typed.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import seedu.typed.logic.parser.FindUtil;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private static final String NEWLINE_DELIMITER = "\n";
    private static final String WHITESPACE_DELIMITER = "\\s+";
    private static final String UNSIGNED_INTEGER_VALIDATION_REGEX = "^0*[1-9]\\d*$";

    /**
     * Returns true if the {@code sentence} contains the {@code query} or a similar {@code query}.
     * Ignores case, and both full word match or similar word match are allowed. <br>
     * examples:
     *
     * <pre>
     *       containsFuzzyWordIgnoreCase("ABc def", "abc") == true // a full word match
     *       containsFuzzyWordIgnoreCase("ABc def", "DEF") == true // a full word match
     *       containsFuzzyWordIgnoreCase("ABc def", "dfg") == true // a similar match
     *       containsFuzzyWordIgnoreCase("ABc def", "zzz") == false //not a full word match and not a similar match
     * </pre>
     *
     * @param sentence
     *            cannot be null
     * @param query
     *            cannot be null, cannot be empty, must be a single word
     */
    public static boolean isFuzzyKeywordSearchIgnoreCase(String sentence, String query) {
        assert query != null : "Query parameter cannot be null";
        assert sentence != null : "Sentence parameter cannot be null";

        String trimmedQuery = query.trim();
        assert !trimmedQuery.isEmpty() : "Query parameter cannot be empty";
        assert trimmedQuery.split(WHITESPACE_DELIMITER).length == 1 : "Query parameter should be a single word";

        String[] wordsInSentence = sentence.split(WHITESPACE_DELIMITER);

        for (String word : wordsInSentence) {
            if (FindUtil.isFuzzyWordMatchIgnoreCase(word, trimmedQuery)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + NEWLINE_DELIMITER + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is: null, empty string, "-1", "0", "+1",
     * and " 2 " (untrimmed) "3 0" (contains whitespace).
     *
     * @param s
     *            Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches(UNSIGNED_INTEGER_VALIDATION_REGEX);
    }
}
