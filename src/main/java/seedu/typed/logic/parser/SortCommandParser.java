package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.SortCommand;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * SortCommand and returns an SortCommand object for execution.
     */

    //@TODO Support sort by name, tags, priority only or allow flexible argument but only
    // accepting 3 ?

    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        final String keyword = matcher.group();
        return new SortCommand(keyword);
    }
}
