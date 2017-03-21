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
        // is this the good way of "clearing" the manager by just creating a new one
        // it's not really just clearing what is required (lists) but remaking everything
        assert model != null;
        TaskManager oldTaskManager = new TaskManager();
        oldTaskManager.copyData(model.getTaskManager());
        model.resetData(new TaskManager());
        ReadOnlyTaskManager newTaskManager = new TaskManager();
        session.update(CommandTypeUtil.TYPE_CLEAR, oldTaskManager, newTaskManager);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
