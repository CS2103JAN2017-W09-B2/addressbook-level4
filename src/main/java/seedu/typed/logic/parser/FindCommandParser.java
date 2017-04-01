//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

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

    private static final char TAG_IDENTIFIER = '#';

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     *
     * @param args takes both keywords and tags
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(getIncorrectFindMessage());
        }
        final String[] keywords = matcher.group("keywords").split("\\s+");
        Set<String> nameKeywords = new HashSet<String>();
        Set<String> tagKeywords = new HashSet<String>();
        addKeywordsByType(keywords, nameKeywords, tagKeywords);
        return new FindCommand(nameKeywords, tagKeywords);
    }

    private void addKeywordsByType(String[] keywords, Set<String> nameKeywords, Set<String> tagKeywords) {
        for (String word : keywords) {
            if (isWordATag(word)) {
                tagKeywords.add(word.substring(1));
            } else {
                nameKeywords.add(word);
            }
        }
    }

    private String getIncorrectFindMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
    }

    private boolean isWordATag(String word) {
        assert word != null;
        assert !word.isEmpty();
        return (word.charAt(0) == TAG_IDENTIFIER);
    }

}
