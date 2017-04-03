package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.UndoCommand;

//@@author A0143853A
/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser {

    public static final String POSITIVE_INTEGER_REGEX = "[1-9]+[0-9]*";
    /**
     * Parses the given {@code String} of arguments in the context of the
     * UndoCommand and returns an UndoCommand object for execution.
     */
    public Command parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equals("")) {
            return new UndoCommand();
        } else if (trimmedArgs.equals("all")) {
            return new UndoCommand(-1);
        } else if (trimmedArgs.matches(POSITIVE_INTEGER_REGEX)) {
            int num = Integer.parseInt(trimmedArgs);
            return new UndoCommand(num);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                      UndoCommand.MESSAGE_USAGE));
        }
    }

}
