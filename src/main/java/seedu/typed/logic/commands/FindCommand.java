package seedu.typed.logic.commands;

import java.util.Set;

import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " broccoli green healthy";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        assert keywords != null;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_FIND_TASK, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
