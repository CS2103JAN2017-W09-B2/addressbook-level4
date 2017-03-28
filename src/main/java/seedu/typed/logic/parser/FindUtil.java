//@@author A0140194M
package seedu.typed.logic.parser;

/**
 * Contains utility methods used for parsing strings for the command Find.
 */
public class FindUtil {

    private static final int MAX_EDIT_DISTANCE = 2; // lower distance = higher similarity
    private static final int MAX_EDIT_DISTANCE_STRICT = 1;
    private static final String WHITESPACE_DELIMITER = "\\s+";

    /**
     * Checks if specified strings are an exact match.
     */
    public static boolean isExactWordMatchIgnoreCase(String str, String word) {
        assert str != null : "str cannot be null";
        assert word != null : "word cannot be null";
        assert !word.isEmpty() : "word cannot be empty";
        assert word.split(WHITESPACE_DELIMITER).length == 1 : "word parameter should be a single word";
        str = str.toLowerCase();
        word = word.toLowerCase();
        return str.equals(word);
    }

    /**
     * Checks if specified strings are substring match.
     */
    public static boolean isSubstringWordMatchIgnoreCase(String str, String word) {
        assert str != null : "str cannot be null";
        assert word != null : "word cannot be null";
        assert !word.isEmpty() : "word cannot be empty";
        assert word.split(WHITESPACE_DELIMITER).length == 1 : "word parameter should be a single word";
        str = str.toLowerCase();
        word = word.toLowerCase();
        return str.contains(word);
    }

    /**
     * Checks if specified strings are similar.
     * @param str non-null string
     * @param word non-null, non-empty string that contains a single word
     * @return isFuzzyMatch boolean indicating if str and word are a fuzzy match, i.e. similar
     */
    public static boolean isFuzzyWordMatchIgnoreCase(String str, String word) {
        assert str != null : "str cannot be null";
        assert word != null : "word cannot be null";
        assert !word.isEmpty() : "word cannot be empty";
        assert word.split(WHITESPACE_DELIMITER).length == 1 : "word parameter should be a single word";
        str = str.toLowerCase();
        word = word.toLowerCase();
        if (str.length() <= 2) {
            return isExactWordMatchIgnoreCase(str, word);
        } else if (str.length() <= 4) {
            return computeMinEditDistance(str, word) <= MAX_EDIT_DISTANCE_STRICT;
        } else {
            return computeMinEditDistance(str, word) <= MAX_EDIT_DISTANCE || isSubstringWordMatchIgnoreCase(str, word);
        }
    }

    /**
     * Computes the minimum edit distance between specified strings as a measure of similarity.
     * @param str non-null string
     * @param word non-null, non-empty string that contains a single word
     * @return minimumEditDistance an int representation of how similar str and word are
     */
    private static int computeMinEditDistance(String str, String word) {
        assert str != null : "str cannot be null";
        assert word != null : "word cannot be null";
        assert !word.isEmpty() : "word cannot be empty";
        assert word.split(WHITESPACE_DELIMITER).length == 1 : "word parameter should be a single word";
        int lenStr = str.length();
        int lenWord = word.length();
        return computeLevenshtein(initDistance(lenStr, lenWord), str, word);
    }

    /**
     * Initializes the minimum edit distance table by padding the first row and column.
     * @param lenStr length of str where str cannot be null
     * @param lenWord length of word where word cannot be null, empty, and must contain a single word
     * @return editDistance an int array containing the initialized distances
     */
    private static int[][] initDistance(int lenStr, int lenWord) {
        assert lenStr >= 0 : "length of str must be a positive int";
        assert lenWord >= 0 : "length of word must be a positive int";
        int[][] editDistance = new int[lenStr + 1][lenWord + 1];
        for (int c = 0; c < lenWord + 1; c++) {
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
     * @param word non-null, non-empty string that contains a single word
     * @return minimumEditDistance an int representation of how similar str and word are
     */
    private static int computeLevenshtein(int[][] distance, String str, String word) {
        assert str != null : "str cannot be null";
        assert word != null : "word cannot be null";
        assert !word.isEmpty() : "word cannot be empty";
        assert word.split(WHITESPACE_DELIMITER).length == 1 : "word parameter should be a single word";
        for (int i = 1; i < distance.length; i++) {
            for (int j = 1; j < distance[0].length; j++) {
                int a = distance[i - 1][j] + 1;
                int b = distance[i][j - 1] + 1;
                int c = distance[i - 1][j - 1];
                if (str.charAt(i - 1) != word.charAt(j - 1)) {
                    c += 1;
                }
                distance[i][j] = Math.min(a, Math.min(b, c));
            }
        }
        return distance[distance.length - 1][distance[0].length - 1];
    }

}
