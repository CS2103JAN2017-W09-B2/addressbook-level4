//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TO;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NOTES, PREFIX_DATE, PREFIX_ON,
                PREFIX_FROM, PREFIX_TO, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String notes = null;
        if (isNotesPresent(argsTokenizer)) {
            notes = argsTokenizer.getValue(PREFIX_NOTES).get();
        }

        if (hasByAndOnField(argsTokenizer)) {
            return new IncorrectCommand(getIncorrectAddMessage());
        }

        try {
            if (isFloatingActivity(argsTokenizer)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), notes, null, null, null,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else if (isEvent(argsTokenizer)) {
                return new AddCommand(argsTokenizer.getPreamble().get(), notes, null,
                        argsTokenizer.getValue(PREFIX_FROM).get(),
                        argsTokenizer.getValue(PREFIX_TO).get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else if (isTask(argsTokenizer)) {
                String deadline = null;
                if (isByField(argsTokenizer)) {
                    deadline = argsTokenizer.getValue(PREFIX_DATE).get();
                } else {
                    deadline = argsTokenizer.getValue(PREFIX_ON).get();
                }
                return new AddCommand(argsTokenizer.getPreamble().get(), notes, deadline,
                        null, null, ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else {
                return new IncorrectCommand(getIncorrectAddMessage());
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(getIncorrectAddMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * @return
     */
    private String getIncorrectAddMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
    }

    private boolean isByField(ArgumentTokenizer argsTokenizer) {
        return !argsTokenizer.getValue(PREFIX_DATE).equals(Optional.empty());
    }

    private boolean isOnField(ArgumentTokenizer argsTokenizer) {
        return !argsTokenizer.getValue(PREFIX_ON).equals(Optional.empty());
    }

    private boolean hasDeadlineField(ArgumentTokenizer argsTokenizer) {
        return isByField(argsTokenizer) || isOnField(argsTokenizer);
    }

    private boolean hasByAndOnField(ArgumentTokenizer argsTokenizer) {
        return isByField(argsTokenizer) && isOnField(argsTokenizer);
    }

    private boolean hasFromToFields(ArgumentTokenizer argsTokenizer) {
        return !argsTokenizer.getValue(PREFIX_FROM).equals(Optional.empty()) &&
                !argsTokenizer.getValue(PREFIX_TO).equals(Optional.empty());
    }

    private boolean isNotesPresent(ArgumentTokenizer argsTokenizer) {
        return !argsTokenizer.getValue(PREFIX_NOTES).equals(Optional.empty());
    }

    private boolean isEvent(ArgumentTokenizer argsTokenizer) {
        return (!hasDeadlineField(argsTokenizer) && hasFromToFields(argsTokenizer));
    }

    private boolean isTask(ArgumentTokenizer argsTokenizer) {
        return (hasDeadlineField(argsTokenizer) && !hasFromToFields(argsTokenizer));
    }

    private boolean isFloatingActivity(ArgumentTokenizer argsTokenizer) {
        return (!hasDeadlineField(argsTokenizer) && !hasFromToFields(argsTokenizer));
    }
}
