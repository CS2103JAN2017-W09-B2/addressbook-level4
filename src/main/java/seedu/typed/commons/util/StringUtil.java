package seedu.typed.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import seedu.typed.logic.parser.FindUtil;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private static final String TAGS_CANNOT_BE_NULL = "Tags parameter cannot be null!";
    private static final String QUERY_SHOULD_BE_A_SINGLE_WORD_MESSAGE = "Query parameter should be a single word!";
    private static final String QUERY_CANNOT_BE_EMPTY_MESSAGE = "Query parameter cannot be empty!";
    private static final String SENTENCE_CANNOT_BE_NULL_MESSAGE = "Sentence parameter cannot be null!";
    private static final String QUERY_CANNOT_BE_NULL_MESSAGE = "Query parameter cannot be null!";
    private static final String NEWLINE_DELIMITER = "\n";
    private static final String WHITESPACE_DELIMITER = "\\s+";
    private static final String UNSIGNED_INTEGER_VALIDATION_REGEX = "^0*[1-9]\\d*$";

    //@@author A0141094M
    /**
     * Returns true if the {@code sentence} contains the {@code query} or a similar {@code query}.
     * Ignores case, and both full word match or similar word match are allowed. <br>
     * @param sentence cannot be null
     * @param query cannot be null, cannot be empty, must be a single word
     */
    public static boolean isFuzzyKeywordSearchIgnoreCase(String sentence, String query) {
        assert sentence != null : SENTENCE_CANNOT_BE_NULL_MESSAGE;
        assert query != null : QUERY_CANNOT_BE_NULL_MESSAGE;
        String trimmedQuery = trimQuery(query);
        String[] wordsInSentence = sentence.split(WHITESPACE_DELIMITER);
        for (String word : wordsInSentence) {
            if (FindUtil.isFuzzyWordMatchIgnoreCase(word, trimmedQuery)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code tags} contain the {@code query} or a similar {@code query}.
     * Ignores case, and both exact tag match or similar tag match are allowed.
     * @param tags cannot be null
     * @param query cannot be null, cannot be empty, must be a single word
     */
    public static boolean isFuzzyKeywordSearchIgnoreCase(UniqueTagList tags, String query) {
        assert tags != null : TAGS_CANNOT_BE_NULL;
        assert query != null : QUERY_CANNOT_BE_NULL_MESSAGE;
        String trimmedQuery = trimQuery(query);
        for (Tag tag : tags) {
            String tagWord = tag.getValue();
            if (FindUtil.isFuzzyTagMatchIgnoreCase(tagWord, trimmedQuery)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trims {@code query} and checks that the trimmed query is non-empty and is a single word.
     */
    private static String trimQuery(String query) {
        String trimmedQuery = query.trim();
        assert !trimmedQuery.isEmpty() : QUERY_CANNOT_BE_EMPTY_MESSAGE;
        assert trimmedQuery.split(WHITESPACE_DELIMITER).length == 1 : QUERY_SHOULD_BE_A_SINGLE_WORD_MESSAGE;
        return trimmedQuery;
    }
    //@@author

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
