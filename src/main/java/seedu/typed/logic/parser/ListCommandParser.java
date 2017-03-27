package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.ListCommand;

//@@author A0141094M

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {

    private static final String ALL_STRING = "all";
    private static final String DONE_STRING = "done";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListCommand and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        try {
            String type = ALL_STRING;
            Optional<String> value = argsTokenizer.getPreamble();
            if (value.isPresent() && !value.get().isEmpty()) {
                //type = Type.valueOf(argsTokenizer.getPreamble().get());
                String lowered = argsTokenizer.getPreamble().get().trim().toLowerCase();
                if (lowered.equals(DONE_STRING)) {
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
