package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;

//@@author A0141094M

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_FROM, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            if (isFloatingActivity(argsTokenizer)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), null, null,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else if (isEvent(argsTokenizer)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), null, argsTokenizer.getValue(PREFIX_FROM).get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else if (isTask(argsTokenizer)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_DATE).get(), null,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private boolean isEvent(ArgumentTokenizer argsTokenizer) {
        return (!argsTokenizer.getValue(PREFIX_FROM).equals(Optional.empty())
                && argsTokenizer.getValue(PREFIX_DATE).equals(Optional.empty()));
    }

    private boolean isTask(ArgumentTokenizer argsTokenizer) {
        return (!argsTokenizer.getValue(PREFIX_DATE).equals(Optional.empty()));
    }

    private boolean isFloatingActivity(ArgumentTokenizer argsTokenizer) {
        return (argsTokenizer.getValue(PREFIX_DATE).equals(Optional.empty())
                && argsTokenizer.getValue(PREFIX_FROM).equals(Optional.empty()));
    }
}
