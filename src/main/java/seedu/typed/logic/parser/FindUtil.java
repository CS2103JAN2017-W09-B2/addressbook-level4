//@@author A0141094M
package seedu.typed.logic.parser;

/**
 * Contains utility methods used for parsing strings for the command Find.
 */
public class FindUtil {

    static final String QUERY_CANNOT_BE_EMPTY_MESSAGE = "query cannot be empty";
    static final String QUERY_LEN_SHOULD_BE_ONE_MESSAGE = "query parameter should be a single word";
    static final String QUERY_LEN_SHOULD_BE_LESS_THAN_ONE_MESSAGE = "query parameter should be empty or a single word";
    static final String TAG_LEN_SHOULD_BE_ONE_MESSAGE = "tag parameter should be a single word";
    static final String QUERY_CANNOT_BE_NULL_MESSAGE = "query cannot be null";
    static final String STR_CANNOT_BE_NULL_MESSAGE = "str cannot be null";
    private static final String QUERY_LEN_MUST_BE_POSITIVE_INT_MESSAGE = "length of query must be a positive int";
    private static final String STR_LEN_MUST_BE_POSITIVE_INT_MESSAGE = "length of str must be a positive int";
    private static final String WHITESPACE_DELIMITER = "\\s+";
    private static final int MAX_EDIT_DISTANCE = 2; // lower distance = higher similarity
    private static final int MAX_EDIT_DISTANCE_STRICT = 1;


    /**
     * Checks if {@code query} and {@code str} are an exact match, ignoring case.
     */
    public static boolean isExactWordMatchIgnoreCase(String str, String query) {
        assertCheckStrAndQuery(str, query);
        str = str.toLowerCase();
        query = query.toLowerCase();
        return str.equals(query);
    }

    /**
     * Checks if {@code query} is a substring of {@code str}, ignoring case.
     */
    public static boolean isSubstringWordMatchIgnoreCase(String str, String query) {
        assertCheckStrAndQuery(str, query);
        str = str.toLowerCase();
        query = query.toLowerCase();
        return str.contains(query);
    }

    /**
     * Checks if {@code query} is a fuzzy match to {@code str}, ignoring case.
     * @param str non-null string
     * @param query non-null, non-empty string that contains a single word
     * @return isFuzzyMatch boolean indicating if str and query are a fuzzy match, i.e. similar
     */
    public static boolean isFuzzyWordMatchIgnoreCase(String str, String query) {
        assertCheckStrAndQuery(str, query);
        str = str.toLowerCase();
        query = query.toLowerCase();
        if (str.length() <= 2) {
            return isExactWordMatchIgnoreCase(str, query);
        } else if (str.length() <= 4) {
            return computeMinEditDistance(str, query) <= MAX_EDIT_DISTANCE_STRICT;
        } else {
            return computeMinEditDistance(str, query) <= MAX_EDIT_DISTANCE || isSubstringWordMatchIgnoreCase(str, query);
        }
    }

    /**
     * Checks if specified {@code str} is a similar match to any stored tags, ignoring case.
     * @param tag non-null string that contains a single word
     * @param query non-null string that contains a single word
     *          empty string always returns true
     * @return isFuzzyMatch boolean indicating if str and word are a fuzzy match, i.e. similar
     */
    public static boolean isFuzzyTagMatchIgnoreCase(String tag, String query) {
        assert tag != null : STR_CANNOT_BE_NULL_MESSAGE;
        assert query != null : QUERY_CANNOT_BE_NULL_MESSAGE;
        assert tag.split(WHITESPACE_DELIMITER).length == 1 : TAG_LEN_SHOULD_BE_ONE_MESSAGE;
        assert query.split(WHITESPACE_DELIMITER).length <= 1 : QUERY_LEN_SHOULD_BE_LESS_THAN_ONE_MESSAGE;
        tag = tag.toLowerCase();
        query = query.toLowerCase();
        if (query.length() == 0) {
            return true;
        }
        if (tag.length() <= 4) {
            return isExactWordMatchIgnoreCase(tag, query);
        } else {
            return computeMinEditDistance(tag, query) <= MAX_EDIT_DISTANCE_STRICT || isSubstringWordMatchIgnoreCase(tag, query);
        }
    }

    /**
     * Computes the minimum edit distance between specified strings as a measure of similarity.
     * @param str non-null string
     * @param query non-null, non-empty string that contains a single word
     * @return minimumEditDistance an int representation of how similar str and query are
     */
    public static int computeMinEditDistance(String str, String query) {
        assertCheckStrAndQuery(str, query);
        int lenStr = str.length();
        int lenWord = query.length();
        return computeLevenshtein(initDistance(lenStr, lenWord), str, query);
    }

    /**
     * Initializes the minimum edit distance table by padding the first row and column.
     * @param lenStr length of str where str cannot be null
     * @param lenQuery length of query where query cannot be null, empty, and must contain a single word
     * @return editDistance an int array containing the initialized distances
     */
    private static int[][] initDistance(int lenStr, int lenQuery) {
        assert lenStr >= 0 : STR_LEN_MUST_BE_POSITIVE_INT_MESSAGE;
        assert lenQuery >= 0 : QUERY_LEN_MUST_BE_POSITIVE_INT_MESSAGE;
        int[][] editDistance = new int[lenStr + 1][lenQuery + 1];
        for (int c = 0; c < lenQuery + 1; c++) {
            editDistance[0][c] = c;
        }
        for (int r = 0; r < lenStr + 1; r++) {
            editDistance[r][0] = r;
        }
        return editDistance;
    }

    /**
     * Computes the edit distance of given indices using Levenshtein's operations.
     * @param distance an array containing initialized distances
     * @param str non-null string
     * @param query non-null, non-empty string that contains a single word
     * @return minimumEditDistance an int representation of how similar str and word are
     */
    private static int computeLevenshtein(int[][] distance, String str, String query) {
        assertCheckStrAndQuery(str, query);
        for (int i = 1; i < distance.length; i++) {
            for (int j = 1; j < distance[0].length; j++) {
                int a = distance[i - 1][j] + 1;
                int b = distance[i][j - 1] + 1;
                int c = distance[i - 1][j - 1];
                if (str.charAt(i - 1) != query.charAt(j - 1)) {
                    c += 1;
                }
                distance[i][j] = Math.min(a, Math.min(b, c));
            }
        }
        return distance[distance.length - 1][distance[0].length - 1];
    }

    /**
     * Checks that str is not null, and query is not null, not empty, and is a single word.
     */
    private static void assertCheckStrAndQuery(String str, String query) {
        assert str != null : STR_CANNOT_BE_NULL_MESSAGE;
        assert query != null : QUERY_CANNOT_BE_NULL_MESSAGE;
        assert !query.isEmpty() : QUERY_CANNOT_BE_EMPTY_MESSAGE;
        assert query.split(WHITESPACE_DELIMITER).length == 1 : QUERY_LEN_SHOULD_BE_ONE_MESSAGE;
    }

}
//@@author

