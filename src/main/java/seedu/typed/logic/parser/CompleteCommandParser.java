package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.IncorrectCommand;

public class CompleteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */

    private static final String COMPLETE_ARG_REGEX = "\\d+\\s*\\d*";

    private int startIndex;
    private int endIndex;


    //@@author A0139379M
    public Command parse(String args) {
        String trimmedArgs = args.trim();

            if (trimmedArgs.equals("all")) {
                return new CompleteCommand();
            } else if (trimmedArgs.matches(COMPLETE_ARG_REGEX)) {
                String[] arguments = trimmedArgs.split("\\s+");
                if (arguments.length == 2) {
                    startIndex = Integer.parseInt(arguments[0]);
                    endIndex = Integer.parseInt(arguments[1]);
                } else {
                    startIndex = Integer.parseInt(arguments[0]);
                    return new CompleteCommand(startIndex);
                }
                if (!validIndices(startIndex, endIndex)) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            CompleteCommand.MESSAGE_USAGE));
                }
                return new CompleteCommand(startIndex, endIndex);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        CompleteCommand.MESSAGE_USAGE));
            }
    }
    //@@author

    //@@author A0143853A
    private static boolean validIndices(int startIndex, int endIndex) {
        if (startIndex <= 0) {
            return false;
        }

        if (endIndex <= 0) {
            return false;
        }

        if (startIndex > endIndex) {
            return false;
        }

        return true;
    }
    //@@author
}
