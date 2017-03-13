package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        session.update(CommandTypeUtil.TYPE_CLEAR, null, null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
