//@@author A0141094M

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
            + "the specified keywords or tags (not case-sensitive).\n"
            + "Parameters: KEYWORD #TAG [MORE_KEYWORDS_OR_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " broccoli #green #healthy";

    private final Set<String> keywords;
    private final Set<String> tagKeywords;

    public FindCommand(Set<String> keywords, Set<String> tagKeywords) {
        assert keywords != null;
        assert tagKeywords != null;
        this.keywords = keywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, tagKeywords);
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_FIND_TASK, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
