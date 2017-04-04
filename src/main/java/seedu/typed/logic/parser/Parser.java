//@@author A0141094M

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
import seedu.typed.logic.commands.ExportCommand;
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

        if (isAddCommandWord(commandWord)) {
            return new AddCommandParser().parse(args);
        }
        if (isCompleteCommandWord(commandWord)) {
            return new CompleteCommandParser().parse(args);
        }
        if (isEditCommandWord(commandWord)) {
            return new EditCommandParser().parse(args);
        }
        if (isHelpCommandWord(commandWord)) {
            return new HelpCommand();
        }
        if (isExitCommandWord(commandWord)) {
            return new ExitCommand();
        }
        if (isListCommandWord(commandWord)) {
            return new ListCommandParser().parse(args);
        }
        if (isFindCommandWord(commandWord)) {
            return new FindCommandParser().parse(args);
        }
        if (isDeleteCommandWord(commandWord)) {
            return new DeleteCommandParser().parse(args);
        }
        if (isClearCommandWord(commandWord)) {
            return new ClearCommand();
        }
        if (isSaveCommand(commandWord)) {
            return new SaveCommandParser().parse(args);
        }
        if (isSelectCommand(commandWord)) {
            return new SelectCommandParser().parse(args);
        }
        if (isUndoCommand(commandWord)) {
            return new UndoCommandParser().parse(args);
        }
        if (isRedoCommand(commandWord)) {
            return new RedoCommandParser().parse(args);
        }
        if (isHistoryCommand(commandWord)) {
            return new HistoryCommand();
        }
        if (isExportCommand(commandWord)) {
            return new ExportCommandParser().parse(args);
        }
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }

    private boolean isExportCommand(String commandWord) {
        return commandWord.equals(ExportCommand.EXPORT_COMMAND_WORD);
    }

    private boolean isHistoryCommand(String commandWord) {
        return commandWord.equals(HistoryCommand.HISTORY_COMMAND_WORD) ||
                commandWord.equals(HistoryCommand.LOG_COMMAND_WORD) ||
                commandWord.equals(HistoryCommand.HIST_COMMAND_WORD);
    }

    private boolean isRedoCommand(String commandWord) {
        return commandWord.equals(RedoCommand.REDO_COMMAND_WORD);
    }

    private boolean isUndoCommand(String commandWord) {
        return commandWord.equals(UndoCommand.UNDO_COMMAND_WORD);
    }

    private boolean isSelectCommand(String commandWord) {
        return commandWord.equals(SelectCommand.SELECT_COMMAND_WORD);
    }

    private boolean isSaveCommand(String commandWord) {
        return commandWord.equals(SaveCommand.SAVE_COMMAND_WORD);
    }

    private boolean isClearCommandWord(String commandWord) {
        return commandWord.equals(ClearCommand.CLEAR_COMMAND_WORD) ||
                commandWord.equals(ClearCommand.EMPTY_COMMAND_WORD);
    }

    private boolean isDeleteCommandWord(String commandWord) {
        return commandWord.equals(DeleteCommand.DELETE_COMMAND_WORD) ||
                commandWord.equals(DeleteCommand.DEL_COMMAND_WORD) ||
                commandWord.equals(DeleteCommand.REMOVE_COMMAND_WORD);
    }

    private boolean isFindCommandWord(String commandWord) {
        return commandWord.equals(FindCommand.FIND_COMMAND_WORD) ||
                commandWord.equals(FindCommand.SEARCH_COMMAND_WORD) ||
                commandWord.equals(FindCommand.QUERY_COMMAND_WORD);
    }

    private boolean isListCommandWord(String commandWord) {
        return commandWord.equals(ListCommand.LIST_COMMAND_WORD) ||
                commandWord.equals(ListCommand.FILTER_COMMAND_WORD) ||
                commandWord.equals(ListCommand.SHOW_COMMAND_WORD);
    }

    private boolean isExitCommandWord(String commandWord) {
        return commandWord.equals(ExitCommand.EXIT_COMMAND_WORD) ||
                commandWord.equals(ExitCommand.QUIT_COMMAND_WORD) ||
                commandWord.equals(ExitCommand.LOGOUT_COMMAND_WORD);
    }

    private boolean isHelpCommandWord(String commandWord) {
        return commandWord.equals(HelpCommand.HELP_COMMAND_WORD) ||
                commandWord.equals(HelpCommand.MAN_COMMAND_WORD);
    }

    private boolean isEditCommandWord(String commandWord) {
        return commandWord.equals(EditCommand.EDIT_COMMAND_WORD) ||
                commandWord.equals(EditCommand.UDPATE_COMMAND_WORD) ||
                commandWord.equals(EditCommand.CHANGE_COMMAND_WORD);
    }

    private boolean isCompleteCommandWord(String commandWord) {
        return commandWord.equals(CompleteCommand.COMPLETE_COMMAND_WORD) ||
                commandWord.equals(CompleteCommand.FINISH_COMMAND_WORD) ||
                commandWord.equals(CompleteCommand.DONE_COMMAND_WORD) ||
                commandWord.equals(CompleteCommand.CHECK_COMMAND_WORD) ||
                commandWord.equals(CompleteCommand.MARK_COMMAND_WORD);
    }

    private boolean isAddCommandWord(String commandWord) {
        return commandWord.equals(AddCommand.ADD_COMMAND_WORD) ||
                commandWord.equals(AddCommand.CREATE_COMMAND_WORD) ||
                commandWord.equals(AddCommand.DO_COMMAND_WORD);
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
//@@author

