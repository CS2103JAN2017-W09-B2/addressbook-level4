package seedu.typed.logic.parser;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.IncorrectCommand;
//@@author A0139379M
public class CompleteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */

    private int startIndex;
    private int endIndex;

    public Command parse(String args) {
        assert args != null;
        String trimmedArgs = args.trim();
        try {
            if ("all".equals(trimmedArgs)) {
                return new CompleteCommand();
            } else if (trimmedArgs.matches("\\d+\\s*\\d*")) {
                String[] arguments = trimmedArgs.split("\\s+");
                if (arguments.length == 2) {
                    startIndex = Integer.parseInt(arguments[0]);
                    endIndex = Integer.parseInt(arguments[1]);
                } else {
                    startIndex = Integer.parseInt(arguments[0]);
                    return new CompleteCommand(startIndex);
                }
                if (startIndex <= 0 || endIndex <= 0 || startIndex > endIndex) {
                    throw new IllegalValueException(CompleteCommand.MESSAGE_USAGE);
                }
                return new CompleteCommand(startIndex, endIndex);
            } else {
                throw new IllegalValueException(CompleteCommand.MESSAGE_USAGE);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
