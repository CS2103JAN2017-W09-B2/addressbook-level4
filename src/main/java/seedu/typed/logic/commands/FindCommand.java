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
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " broccoli green healthy";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        this.model.updateFilteredTaskList(this.keywords);
        String keywordsString = String.join(",", this.keywords);
        this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_FIND_TASK, keywordsString, null);
        this.session.updateValidCommandsHistory(this.commandText);
        return new CommandResult(getMessageForTaskListShownSummary(this.model.getFilteredTaskList().size()));
    }

}
