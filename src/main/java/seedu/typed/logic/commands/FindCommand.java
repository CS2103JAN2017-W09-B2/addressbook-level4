//@@author A0141094M

package seedu.typed.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD_FIND = "find";
    public static final String COMMAND_WORD_SEARCH = "search";
    public static final String COMMAND_WORD_QUERY = "query";

    public static final String MESSAGE_USAGE = COMMAND_WORD_FIND + ": Finds all tasks whose names contain any of "
            + "the specified keywords or tags (not case-sensitive).\n"
            + "Example: " + COMMAND_WORD_FIND + " broccoli #green #healthy";

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
        session.updateUndoRedoStacks(COMMAND_WORD_FIND, -1, null);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
