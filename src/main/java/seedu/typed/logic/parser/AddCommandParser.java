//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_EVERY;
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
import seedu.typed.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String STARTDATE_AFTER_ENDDATE_ERROR_MESSAGE = "Did you key the wrong dates? The end date "
            + "for your event is earlier than its start date. ";
    private static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     * @param {@code args} user input arguments for an Add command
     * @return Command to add a task specified by {@code args}
     */
    public Command parse(String args) {
        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NOTES, PREFIX_DATE, PREFIX_ON,
                    PREFIX_FROM, PREFIX_TO, PREFIX_EVERY, PREFIX_TAG);
            argsTokenizer.tokenize(args);

            String name = argsTokenizer.getPreamble().get();
            String notes = getNotes(argsTokenizer);
            String deadline = getDeadline(argsTokenizer);
            String startString = getFieldValue(argsTokenizer, PREFIX_FROM);
            String endString = getFieldValue(argsTokenizer, PREFIX_TO);
            String every = getFieldValue(argsTokenizer, PREFIX_EVERY);
            Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            LocalDateTime deadlineDateTime = DateTimeParser.getLocalDateTimeFromString(deadline);
            LocalDateTime startDateTime = DateTimeParser.getLocalDateTimeFromString(startString);
            LocalDateTime endDateTime = DateTimeParser.getLocalDateTimeFromString(endString);

            deadlineDateTime = setTimeToEndOfDay(deadline, deadlineDateTime);
            startDateTime = setTimeToStartOfDay(startString, startDateTime);
            startDateTime = setTimeToNowIfStartTimeHasPassed(startDateTime);
            endDateTime = setTimeToEndOfDay(endString, endDateTime);

            if (startString != null && endString != null) {
                if (startDateTime.isAfter(endDateTime)) {
                    return new IncorrectCommand(STARTDATE_AFTER_ENDDATE_ERROR_MESSAGE);
                    //endDateTime = endDateTime.plusDays(7); // cheap hack to be changed
                }
            }

            if (hasBothByAndOn(argsTokenizer) || isBothDeadlineAndEvent(argsTokenizer)) {
                return new IncorrectCommand(getIncorrectAddMessage());
            }
            if (isRecurrent(every)) {
                return new AddCommand(name, notes, deadlineDateTime, startDateTime, endDateTime, tags, every);
            }
            return new AddCommand(name, notes, deadlineDateTime, startDateTime, endDateTime, tags);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(getIncorrectAddMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * @param every
     * @return
     */
    private boolean isRecurrent(String every) {
        return every != null;
    }

    /**
     * @param startString
     * @param startDateTime
     * @return
     * @throws IllegalValueException
     */
    private LocalDateTime setTimeToNowIfStartTimeHasPassed(LocalDateTime startDateTime)
            throws IllegalValueException {
        if (startDateTime != null && startDateTime.isBefore(LocalDateTime.now())) {
            startDateTime = LocalDateTime.now();
        }
        return startDateTime;
    }

    /**
     * @param dateString
     * @param dateLdt
     * @return
     * @throws IllegalValueException
     */
    private LocalDateTime setTimeToEndOfDay(String dateString, LocalDateTime dateLdt)
            throws IllegalValueException {
        if (dateString != null && DateTimeParser.isTimeInferred(dateString)) {
            dateLdt = dateLdt.withHour(23).withMinute(59).withSecond(59).withNano(59);
        }
        return dateLdt;
    }

    private LocalDateTime setTimeToStartOfDay(String dateString, LocalDateTime dateLdt)
            throws IllegalValueException {
        if (dateString != null && DateTimeParser.isTimeInferred(dateString)) {
            dateLdt = dateLdt.withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return dateLdt;
    }

    private String getFieldValue(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        if (isFieldPresent(argsTokenizer, prefix)) {
            return argsTokenizer.getValue(prefix).get();
        }
        return null;
    }

    private String getDeadline(ArgumentTokenizer argsTokenizer) {
        String byValue = getFieldValue(argsTokenizer, PREFIX_DATE);
        String onValue = getFieldValue(argsTokenizer, PREFIX_ON);
        if (byValue != null) {
            return byValue;
        } else if (onValue != null) {
            return onValue;
        }
        return null;
    }

    private String getNotes(ArgumentTokenizer argsTokenizer) {
        String notesValue = getFieldValue(argsTokenizer, PREFIX_NOTES);
        if (notesValue == null) {
            return EMPTY_STRING;
        }
        return notesValue;
    }

    private String getIncorrectAddMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
    }

    private boolean isFieldPresent(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        return argsTokenizer.getValue(prefix).isPresent();
    }

    private boolean isDeadline(ArgumentTokenizer argsTokenizer) {
        return isFieldPresent(argsTokenizer, PREFIX_DATE)
                || isFieldPresent(argsTokenizer, PREFIX_ON);
    }

    private boolean hasBothByAndOn(ArgumentTokenizer argsTokenizer) {
        return isFieldPresent(argsTokenizer, PREFIX_DATE)
                && isFieldPresent(argsTokenizer, PREFIX_ON);
    }

    private boolean isBothDeadlineAndEvent(ArgumentTokenizer argsTokenizer) {
        return isDeadline(argsTokenizer) && isEvent(argsTokenizer);
    }

    private boolean isEvent(ArgumentTokenizer argsTokenizer) {
        return isFieldPresent(argsTokenizer, PREFIX_FROM)
                && isFieldPresent(argsTokenizer, PREFIX_TO);
    }

}
