package seedu.typed.logic.commands;

import java.util.List;

import seedu.typed.commons.core.Messages;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.Task.TaskBuilder;

public class CompleteCommand extends Command {
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task as completed "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD
            + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_NOT_COMPLETED = "Task does not exists in the task manager";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task is already completed in the task manager.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to complete
     */
    public CompleteCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(filteredTaskListIndex);
        Task taskToCompleteCopy = new TaskBuilder(taskToComplete).build();
        Task completedTask = new TaskBuilder(taskToCompleteCopy).isCompleted(true).build();

        try {
            model.updateTask(filteredTaskListIndex, completedTask);
            session.update(CommandTypeUtil.TYPE_EDIT_TASK, (Object) taskToCompleteCopy, (Object) completedTask);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NOT_COMPLETED);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToComplete));
    }
}
