package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;

public class SortCommand extends Command {


    public static final String COMMAND_WORD = "sort by";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by given format "
            + "Parameters: FORMAT\n" + "Example: " + COMMAND_WORD
            + " name ";

    public static final String MESSAGE_SUCCESS = "%1$s sorted";

    private String property;

    public SortCommand(String property) {
        this.property = property;
    }


    @Override
    public CommandResult execute() {
        // update filtered list
        // update session of given command
        String keywordsString = String.join(",", property);
        session.update(CommandTypeUtil.TYPE_SORT_TASK, (Object) keywordsString, null);
        // return according result
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }


}
