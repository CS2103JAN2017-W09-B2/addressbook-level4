//@@author A0141094M
package seedu.typed.logic.commands;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String LIST_COMMAND_WORD = "list";
    public static final String FILTER_COMMAND_WORD = "filter";
    public static final String SHOW_COMMAND_WORD = "show";
    public static final String LS_COMMAND_WORD = "ls";

    public static final String MESSAGE_USAGE = LIST_COMMAND_WORD + ": Lists all undone and upcoming tasks by default, "
            + "or by the CATEGORY if specified. Valid CATEGORYs are: untimed, deadline, duration, done, undone and all."
            + "Parameters: [CATEGORY] \n"
            + "Example: " + LIST_COMMAND_WORD
            + " deadline ";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private final String type;

    public ListCommand(String type) throws IllegalValueException {
        this.type = type;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(type);
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_LIST_TASK, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
