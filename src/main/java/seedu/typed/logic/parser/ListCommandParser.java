//@@author A0141094M

package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.PREFIX_SPACE;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {

    private static final String ALL_STRING = "all";
    private static final String DONE_STRING = "done";
    private static final String UNDONE_STRING = "undone";
    private static final String DEADLINE_STRING = "deadline";
    //private static final String FLOATING_STRING = "floating";

    private static final Object DURATION_STRING = "duration";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListCommand and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        // ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_WITH);
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_SPACE);
        argsTokenizer.tokenize(args);
        try {
            String type = ALL_STRING; // default is show all
            // Optional<String> value = argsTokenizer.getValue(PREFIX_WITH);
            Optional<String> value = argsTokenizer.getValue(PREFIX_SPACE);
            if (value.isPresent() && !value.get().isEmpty()) {
                String[] split = value.get().split(" ");
                String lowered = split[0].toLowerCase();
                if (lowered.equals(ALL_STRING)
                        || lowered.equals(DONE_STRING)
                        || lowered.equals(DEADLINE_STRING)
                        || lowered.equals(UNDONE_STRING)
                        || lowered.equals(DURATION_STRING)) {
                    type = lowered;
                }
            }
            System.out.println("passing to ListCommand " + type);
            return new ListCommand(type);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
