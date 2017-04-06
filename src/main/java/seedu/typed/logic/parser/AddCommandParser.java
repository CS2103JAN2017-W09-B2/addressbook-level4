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

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NOTES, PREFIX_DATE, PREFIX_ON,
                    PREFIX_FROM, PREFIX_TO, PREFIX_EVERY, PREFIX_TAG);
            argsTokenizer.tokenize(args);

            String name = argsTokenizer.getPreamble().get();
            String notes = getNotes(argsTokenizer);
            String deadline = getDeadline(argsTokenizer);
            String startString = getFrom(argsTokenizer);
            String endString = getTo(argsTokenizer);
            String every = getEvery(argsTokenizer);
            System.out.println(every); // tuesday, stuff
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
                    return new IncorrectCommand("Did you key the wrong dates? The end date "
                            + "for your event is earlier than its start date. ");
                    //endDateTime = endDateTime.plusDays(7); // cheap hack to be changed
                }
            }

            if (hasBothByAndOnFields(argsTokenizer) || isBothDeadlineTaskAndEventTask(argsTokenizer)) {
                return new IncorrectCommand(getIncorrectAddMessage());
            }
            if (isRecurrent(every)) {
                return new AddCommand(name, notes, deadlineDateTime, startDateTime, endDateTime, tags);
            }
            return new AddCommand(name, notes, deadlineDateTime, startDateTime, endDateTime, tags, every);
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
        return every == null;
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

    private String getEvery(ArgumentTokenizer argsTokenizer) {
        if (isEveryPresent(argsTokenizer)) {
            return argsTokenizer.getValue(PREFIX_EVERY).get();
        } else {
            return null;
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
            return EMPTY_STRING;
        }
    }

    private String getIncorrectAddMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
    }

    private boolean isByPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_DATE).isPresent();
    }

    private boolean isEveryPresent(ArgumentTokenizer argsTokenizer) {
        return argsTokenizer.getValue(PREFIX_EVERY).isPresent();
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
