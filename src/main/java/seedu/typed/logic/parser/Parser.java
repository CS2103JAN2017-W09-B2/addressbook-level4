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
import seedu.typed.logic.commands.ImportCommand;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.ListCommand;
import seedu.typed.logic.commands.RedoCommand;
import seedu.typed.logic.commands.SaveCommand;
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
        /*if (isSelectCommand(commandWord)) {
            return new SelectCommandParser().parse(args);
        }*/
        if (isUndoCommand(commandWord)) {
            return new UndoCommandParser().parse(args);
        }
        if (isRedoCommand(commandWord)) {
            return new RedoCommandParser().parse(args);
        }
        if (isExportCommand(commandWord)) {
            return new ExportCommandParser().parse(args);
        }
        if (isImportCommand(commandWord)) {
            return new ImportCommandParser().parse(args);
        }
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }

    private boolean isImportCommand(String commandWord) {
        return commandWord.equals(ImportCommand.COMMAND_WORD_IMPORT) ||
                commandWord.equals(ImportCommand.COMMAND_WORD_LOAD) ||
                commandWord.equals(ImportCommand.COMMAND_WORD_OPEN);
    }

    private boolean isExportCommand(String commandWord) {
        return commandWord.equals(ExportCommand.COMMAND_WORD_EXPORT);
    }

    private boolean isRedoCommand(String commandWord) {
        return commandWord.equals(RedoCommand.COMMAND_WORD_REDO);
    }

    private boolean isUndoCommand(String commandWord) {
        return commandWord.equals(UndoCommand.COMMAND_WORD_UNDO);
    }

    /*private boolean isSelectCommand(String commandWord) {
        return commandWord.equals(SelectCommand.COMMAND_WORD_SELECT);
    }*/

    private boolean isSaveCommand(String commandWord) {
        return commandWord.equals(SaveCommand.COMMAND_WORD_SAVE);
    }

    private boolean isClearCommandWord(String commandWord) {
        return commandWord.equals(ClearCommand.COMMAND_WORD_CLEAR) ||
                commandWord.equals(ClearCommand.COMMAND_WORD_EMPTY);
    }

    private boolean isDeleteCommandWord(String commandWord) {
        return commandWord.equals(DeleteCommand.COMMAND_WORD_DELETE) ||
                commandWord.equals(DeleteCommand.COMMAND_WORD_DEL) ||
                commandWord.equals(DeleteCommand.COMMAND_WORD_REMOVE) ||
                commandWord.equals(DeleteCommand.COMMAND_WORD_RM);
    }

    private boolean isFindCommandWord(String commandWord) {
        return commandWord.equals(FindCommand.COMMAND_WORD_FIND) ||
                commandWord.equals(FindCommand.COMMAND_WORD_SEARCH) ||
                commandWord.equals(FindCommand.COMMAND_WORD_QUERY);
    }

    private boolean isListCommandWord(String commandWord) {
        return commandWord.equals(ListCommand.COMMAND_WORD_LIST) ||
                commandWord.equals(ListCommand.COMMAND_WORD_FILTER) ||
                commandWord.equals(ListCommand.COMMAND_WORD_SHOW) ||
                commandWord.equals(ListCommand.COMMAND_WORD_LS) ||
                commandWord.equals(ListCommand.COMMAND_WORD_DISPLAY);
    }

    private boolean isExitCommandWord(String commandWord) {
        return commandWord.equals(ExitCommand.COMMAND_WORD_EXIT) ||
                commandWord.equals(ExitCommand.COMMAND_WORD_QUIT) ||
                commandWord.equals(ExitCommand.COMMAND_WORD_LOGOUT) ||
                commandWord.equals(ExitCommand.COMMAND_WORD_BYE);
    }

    private boolean isHelpCommandWord(String commandWord) {
        return commandWord.equals(HelpCommand.COMMAND_WORD_HELP) ||
                commandWord.equals(HelpCommand.COMMAND_WORD_MAN) ||
                commandWord.equals(HelpCommand.COMMAND_WORD_SOS);
    }

    private boolean isEditCommandWord(String commandWord) {
        return commandWord.equals(EditCommand.COMMAND_WORD_EDIT) ||
                commandWord.equals(EditCommand.COMMAND_WORD_UPDATE) ||
                commandWord.equals(EditCommand.COMMAND_WORD_CHANGE);
    }

    private boolean isCompleteCommandWord(String commandWord) {
        return commandWord.equals(CompleteCommand.COMMAND_WORD_COMPLETE) ||
                commandWord.equals(CompleteCommand.COMMAND_WORD_FINISH) ||
                commandWord.equals(CompleteCommand.COMMAND_WORD_DONE) ||
                commandWord.equals(CompleteCommand.COMMAND_WORD_CHECK) ||
                commandWord.equals(CompleteCommand.COMMAND_WORD_MARK) ||
                commandWord.equals(CompleteCommand.COMMAND_WORD_END);
    }

    private boolean isAddCommandWord(String commandWord) {
        return commandWord.equals(AddCommand.COMMAND_WORD_ADD) ||
                commandWord.equals(AddCommand.COMMAND_WORD_CREATE) ||
                commandWord.equals(AddCommand.COMMAND_WORD_DO) ||
                commandWord.equals(AddCommand.COMMAND_WORD_NEW);
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

    private boolean isValidUserInput(Matcher matcher) {
        assert matcher != null;
        return matcher.matches();
    }

}
//@@author

