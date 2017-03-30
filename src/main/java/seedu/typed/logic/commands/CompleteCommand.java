package seedu.typed.logic.commands;

import seedu.typed.logic.commands.exceptions.CommandException;
//@@author A0139379M
public class CompleteCommand extends Command {
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task as completed "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_NOT_COMPLETED = "Task does not exists in the task manager";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task is already completed in the task manager.";

    private int startIndex;
    private int endIndex;

    /**
     * @param startIndex
     *            the index of the task in the filtered task list to complete
     */
    public CompleteCommand(int startIndex) {
        assert startIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.startIndex = startIndex - 1;
        this.endIndex = this.startIndex;
    }
    /**
     *
     * @param startIndex
     * @param endIndex
     * Assumes that startIndex <= endIndex
     */
    public CompleteCommand(int startIndex, int endIndex) {
        assert startIndex > 0;
        assert endIndex > 0;

        this.startIndex = startIndex - 1;
        this.endIndex = endIndex - 1;
    }
    /**
     * Default constructor assumes complete all tasks in filtered task list
     */
    public CompleteCommand() {
        this.startIndex = 0;
        this.endIndex = -1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            if (endIndex == -1) {
                endIndex = this.model.getFilteredTaskList().size() - 1;
            }
            this.model.completeTasks(startIndex, endIndex);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NOT_COMPLETED);
        }
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, "Task Name!"));
    }
}
