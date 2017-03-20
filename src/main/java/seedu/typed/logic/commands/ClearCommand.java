package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    @Override
    public CommandResult execute() {
        assert this.model != null;
        TaskManager oldTaskManager = new TaskManager();
        oldTaskManager.copyData(this.model.getTaskManager());
        this.model.resetData(new TaskManager());
        ReadOnlyTaskManager newTaskManager = new TaskManager();
        this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_CLEAR, oldTaskManager, newTaskManager);
        this.session.updateValidCommandsHistory(this.commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
