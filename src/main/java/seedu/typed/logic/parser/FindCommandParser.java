package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.FindCommand;
import seedu.typed.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    private static final int MAX_EDIT_DISTANCE = 2;
    private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    /**
     * Checks if specified strings are similar.
     */
    private boolean isFuzzyMatch(String str1, String str2) {
        return computeMinEditDistance(str1, str2) <= MAX_EDIT_DISTANCE;
    }

    /**
     * Computes the minimum edit distance between specified strings as measure of similarity.
     */
    private int computeMinEditDistance(String str1, String str2) {
        int lenStr1 = str1.length();
        int lenStr2 = str2.length();
        return computeLevenshtein(initDistance(lenStr1, lenStr2), str1, str2);
    }

    /**
     * Initializes the minimum edit distance table.
     */
    private int[][] initDistance(int len1, int len2) {
        int[][] editDistance = new int[len1 + 1][len2 + 2];
        for (int c = 0; c < len2 + 1; c++) {
            editDistance[0][c] = c;
        }
        for (int r = 0; r < len1 + 1; r++) {
            editDistance[r][0] = r;
        }
        return editDistance;
    }

    /**
     * Computes the edit distance of given indices using Levenshtein's operations.
     */
    private int computeLevenshtein(int[][] distance, String str1, String str2) {
        int bestMin = POSITIVE_INFINITY;
        for (int i = 1; i < distance.length; i++) {
            for (int j = 1; j < distance[0].length; j++) {
                int a = distance[i - 1][j] + 1;
                int b = distance[i][j - 1] + 1;
                int c = distance[i - 1][j - 1];
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c += 1;
                }
                bestMin = Math.min(bestMin, Math.min(a, Math.min(b, c)));
            }
        }
        return bestMin;
    }

}
