package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.RedoCommand;

//@@author A0143853A
/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser {

    public static String POSITIVE_INTEGER_REGEX = "[1-9]+[0-9]*";
    /**
     * Parses the given {@code String} of arguments in the context of the
     * RedoCommand and returns an RedoCommand object for execution.
     */
    public Command parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equals("")) {
            return new RedoCommand();
        } else if (trimmedArgs.equals("all")) {
            return new RedoCommand(-1);
        } else if (trimmedArgs.matches(POSITIVE_INTEGER_REGEX)) {
            int num = Integer.parseInt(trimmedArgs);
            return new RedoCommand(num);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RedoCommand.MESSAGE_USAGE));
        }
    }

}
