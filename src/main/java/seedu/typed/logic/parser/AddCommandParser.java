//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_BY;
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
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NOTES, PREFIX_BY, PREFIX_ON,
                    PREFIX_FROM, PREFIX_TO, PREFIX_EVERY, PREFIX_TAG);
            argsTokenizer.tokenize(args);

            String name = argsTokenizer.getPreamble().get();
            String notes = getNotes(argsTokenizer);
            String every = getFieldValue(argsTokenizer, PREFIX_EVERY);
            Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));

            String[] dates = getAllDates(argsTokenizer);
            LocalDateTime[] dateTimes = getAllDateTimes(dates);
            dateTimes = checkDatesWrapper(dates, dateTimes);
            if (dateTimes[1] != null && dateTimes[2] != null && dateTimes[1].isAfter(dateTimes[2])) {
                return new IncorrectCommand(STARTDATE_AFTER_ENDDATE_ERROR_MESSAGE);
            }

            if (hasBothByAndOn(argsTokenizer) || isBothDeadlineAndEvent(argsTokenizer)) {
                return new IncorrectCommand(getIncorrectAddMessage());
            }
            if ((isDeadline(argsTokenizer) || isEvent(argsTokenizer)) && isValidRecurrence(every)) {
                return new AddCommand(name, notes, dateTimes[0], dateTimes[1], dateTimes[2], tags, every);
            }
            return new AddCommand(name, notes, dateTimes[0], dateTimes[1], dateTimes[2], tags);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(getIncorrectAddMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private LocalDateTime[] checkDatesWrapper(String[] dates, LocalDateTime[] dateTimes)
            throws IllegalValueException {
        dateTimes = checkDeadlineWrapper(dates, dateTimes);
        dateTimes = checkEventWrapper(dates, dateTimes);
        return dateTimes;
    }

    private LocalDateTime[] checkEventWrapper(String[] dates, LocalDateTime[] dateTimes)
            throws IllegalValueException {
        String start = dates[1];
        String end = dates[2];
        LocalDateTime startDateTime = dateTimes[1];
        LocalDateTime endDateTime = dateTimes[2];
        if (startDateTime == null || endDateTime == null) {
            return dateTimes;
        }
        if (DateTimeParser.isDateInferred(start) || DateTimeParser.isDateInferred(end)) {
            if (startDateTime.isAfter(endDateTime)) {
                dateTimes[1] = startDateTime.plusWeeks(1);
            }
        }
        if (DateTimeParser.isTimeInferred(start)) {
            dateTimes[1] = startDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        if (DateTimeParser.isTimeInferred(end)) {
            dateTimes[2] = endDateTime.withHour(23).withMinute(59).withSecond(59).withNano(59);
        }
        return dateTimes;
    }

    private LocalDateTime[] checkDeadlineWrapper(String[] dates, LocalDateTime[] dateTimes)
            throws IllegalValueException {
        String deadline = dates[0];
        LocalDateTime deadlineDateTime = dateTimes[0];
        if (deadlineDateTime != null && DateTimeParser.isTimeInferred(deadline)) {
            dateTimes[0] = deadlineDateTime.withHour(23).withMinute(59).withSecond(59)
                    .withNano(59);
        }
        return dateTimes;
    }

    private String[] getAllDates(ArgumentTokenizer argsTokenizer)
            throws IllegalValueException {
        assert argsTokenizer != null;
        String[] dates = new String[3];
        dates[0] = getDeadline(argsTokenizer);
        dates[1] = getFieldValue(argsTokenizer, PREFIX_FROM);
        dates[2] = getFieldValue(argsTokenizer, PREFIX_TO);
        return dates;
    }

    private LocalDateTime[] getAllDateTimes(String[] dates)
            throws IllegalValueException {
        assert dates != null;
        LocalDateTime[] dateTimes = new LocalDateTime[3];
        for (int i = 0; i < dates.length; i++) {
            dateTimes[i] = DateTimeParser.getLocalDateTimeFromString(dates[i]);
        }
        return dateTimes;
    }

    private boolean isValidRecurrence(String every) {
        if (every != null) {
            return every.matches("day|week|month|year");
        }
        return false;
    }

    private String getFieldValue(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        if (isFieldPresent(argsTokenizer, prefix)) {
            return argsTokenizer.getValue(prefix).get();
        }
        return null;
    }

    private String getDeadline(ArgumentTokenizer argsTokenizer) {
        String byValue = getFieldValue(argsTokenizer, PREFIX_BY);
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
        return isFieldPresent(argsTokenizer, PREFIX_BY)
                || isFieldPresent(argsTokenizer, PREFIX_ON);
    }

    private boolean hasBothByAndOn(ArgumentTokenizer argsTokenizer) {
        return isFieldPresent(argsTokenizer, PREFIX_BY)
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
