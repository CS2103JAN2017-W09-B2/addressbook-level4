package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_EMPTY_COMMAND;
import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.typed.logic.commands.AddCommand;
import seedu.typed.logic.commands.ClearCommand;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.DeleteCommand;
import seedu.typed.logic.commands.EditCommand;
import seedu.typed.logic.commands.ExitCommand;
import seedu.typed.logic.commands.FindCommand;
import seedu.typed.logic.commands.HelpCommand;
import seedu.typed.logic.commands.HistoryCommand;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.ListCommand;
import seedu.typed.logic.commands.RedoCommand;
import seedu.typed.logic.commands.SaveCommand;
import seedu.typed.logic.commands.SelectCommand;
import seedu.typed.logic.commands.UndoCommand;

/**
 * Parses user input into a Command if input is valid.
 */
public class Parser {

    /**
     * Used for initial separation of command word and arguments.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<args>.*)");

    /**
     * Parses a raw user input and returns the corresponding Command if input is valid.
     *
     * @param input
     *            full user input string
     * @return the command based on the user input
     *          or IncorrectCommand if input is invalid
     */
    public Command parseInput(String input) {
        assert input != null;
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());

        if (isEmptyUserInput(input)) {
            return new IncorrectCommand(getEmptyUserInputMessage());
        }

        if (!isValidUserInput(matcher)) {
            return new IncorrectCommand(getInvalidUserInputMessage());
        }
        final String command = matcher.group("commandWord");
        final String args = matcher.group("args");

        return parseValidUserInput(command, args);
    }

    /**
     * Parses a valid user input into its corresponding Command.
     * @param commandWord
     * @param args
     * @return
     */
    private Command parseValidUserInput(final String commandWord, final String args) {
        assert commandWord != null;

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(args);

        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(args);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(args);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(args);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(args);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(args);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(args);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private String getEmptyUserInputMessage() {
        return MESSAGE_EMPTY_COMMAND;
    }

    private String getInvalidUserInputMessage() {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
    }

    private boolean isEmptyUserInput(String input) {
        assert input != null;
        return input.isEmpty();
    }

    private boolean isValidUserInput(Matcher matcher) { //include String input?
        return matcher.matches();
    }


}
