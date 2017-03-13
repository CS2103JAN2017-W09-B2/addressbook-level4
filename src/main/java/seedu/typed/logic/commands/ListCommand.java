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
        session.update(CommandTypeUtil.TYPE_LIST_TASK, null, null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
