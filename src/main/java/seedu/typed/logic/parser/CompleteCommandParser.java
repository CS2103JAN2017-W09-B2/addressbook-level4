package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.util.IndexRangeUtil;

public class CompleteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */


    //@@author A0139379M
    public Command parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equals("all")) {
            return new CompleteCommand();
        }

        IndexRangeUtil range = new IndexRangeUtil(args);
        if (range.isValid()) {
            return new CompleteCommand(range.getStartIndex(),
                    range.getEndIndex());
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CompleteCommand.MESSAGE_USAGE));
        }
    }
    //@@author
}
