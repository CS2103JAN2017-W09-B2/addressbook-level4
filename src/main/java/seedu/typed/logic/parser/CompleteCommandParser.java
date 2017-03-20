package seedu.typed.logic.parser;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.IncorrectCommand;

public class CompleteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    private static final String ALL = "all";
    private static final String RANGE = "range";
    private static final String INDEX = "index";

    private int startIndex;
    private int endIndex;

    public Command parse(String args) {
        assert args != null;
        startIndex = 0;
        endIndex = 0;
        String type = args;

        switch (type) {
        case ALL:
            return new CompleteCommand();
        case RANGE:
            return new CompleteCommand(startIndex, endIndex);
        case INDEX:
            return new CompleteCommand(startIndex);
        default:
            return new IncorrectCommand(CompleteCommand.MESSAGE_NOT_COMPLETED);
        }
    }
}
