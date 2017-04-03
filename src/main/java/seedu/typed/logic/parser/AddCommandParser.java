//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;

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
        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NOTES, PREFIX_DATE, PREFIX_ON,
                    PREFIX_FROM, PREFIX_TO, PREFIX_TAG);
            argsTokenizer.tokenize(args);

            String name = argsTokenizer.getPreamble().get();
            String notes = getNotes(argsTokenizer);
            String deadline = getDeadline(argsTokenizer);
            String startString = getFrom(argsTokenizer);
            String endString = getTo(argsTokenizer);
            Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            LocalDateTime deadlineDateTime = DateTimeParser.getLocalDateTimeFromString(deadline);
            LocalDateTime startDateTime = DateTimeParser.getLocalDateTimeFromString(startString);
            LocalDateTime endDateTime = DateTimeParser.getLocalDateTimeFromString(endString);

            if (hasBothByAndOnFields(argsTokenizer) || isBothDeadlineTaskAndEventTask(argsTokenizer)) {
                return new IncorrectCommand(getIncorrectAddMessage());
            }
            return new AddCommand(name, notes, deadlineDateTime, startDateTime, endDateTime, tags);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(getIncorrectAddMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private String getDeadline(ArgumentTokenizer argsTokenizer) {
        if (isByPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_DATE).get();
        } else if (isOnPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_ON).get();
        } else {
            return null;
        }
    }

    private String getFrom(ArgumentTokenizer argsTokenizer) {
        if (isFromPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_FROM).get();
        } else {
            return null;
        }
    }

    private String getTo(ArgumentTokenizer argsTokenizer) {
        if (isToPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_TO).get();
        } else {
            return null;
        }
    }

    private String getNotes(ArgumentTokenizer argsTokenizer) {
        if (isNotesPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_NOTES).get();
        } else {
            return "";
        }
    }

    private String getIncorrectAddMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
    }

    private boolean isByPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_DATE).isPresent();
    }

    private boolean isOnPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_ON).isPresent();
    }

    private boolean isFromPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_FROM).isPresent();
    }

    private boolean isToPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_TO).isPresent();
    }

    private boolean isNotesPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_NOTES).isPresent();
    }

    private boolean isDeadlineTask(ArgumentTokenizer argsTokenizer) {
        return isByPresent(argsTokenizer) || isOnPresent(argsTokenizer);
    }

    private boolean hasBothByAndOnFields(ArgumentTokenizer argsTokenizer) {
        return isByPresent(argsTokenizer) && isOnPresent(argsTokenizer);
    }

    private boolean isBothDeadlineTaskAndEventTask(ArgumentTokenizer argsTokenizer) {
        return isDeadlineTask(argsTokenizer) && isEventTask(argsTokenizer);
    }

    private boolean isEventTask(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_FROM).isPresent() && argsTokenizer.getValue(PREFIX_TO).isPresent();
    }

}
