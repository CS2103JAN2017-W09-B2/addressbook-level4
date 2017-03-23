package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_LIST_TASK, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
