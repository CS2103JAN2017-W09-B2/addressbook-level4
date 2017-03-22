package seedu.typed.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.typed.commons.core.Messages;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

public class CompleteCommand extends Command {
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task as completed "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD
            + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_NOT_COMPLETED = "Task does not exists in the task manager";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task is already completed in the task manager.";

    private final int startIndex;
    private final int endIndex;

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
        int size = model.getFilteredTaskList().size();
        this.startIndex = 0;
        this.endIndex = size - 1;
    }

    /**
     * Search through the filtered task list to get the list of tasks that should be completed
     * @param startIndex
     * @param endIndex
     * @return A List<ReadOnlyTask> which contains the list of tasks to be completed
     * @throws CommandException
     */
    private List<ReadOnlyTask> getAffectedTasks(int startIndex, int endIndex) throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        List<ReadOnlyTask> tasksToCompleteList = new ArrayList<>();
        if (startIndex >= lastShownList.size() || endIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int i = startIndex; i <= endIndex; i++) {
            tasksToCompleteList.add(lastShownList.get(i));
        }
        return tasksToCompleteList;
    }

    /**
     * Updates the model and session of the completed tasks
     * @param tasksList
     * @throws DuplicateTaskException
     * @throws TaskNotFoundException
     */
    private void updateCompletedTasks(List<ReadOnlyTask> tasksList) throws DuplicateTaskException,
        TaskNotFoundException {
        for (int i = 0; i < tasksList.size(); i++) {
            Task taskToCompleteCopy = new TaskBuilder(tasksList.get(i)).build();
            Task completedTask = new TaskBuilder(taskToCompleteCopy).isCompleted(true).build();
            this.model.updateTask(startIndex + i, completedTask);
            this.model.completeTask(completedTask);
            //session.update(CommandTypeUtil.TYPE_EDIT_TASK, taskToCompleteCopy, completedTask);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> tasksToCompleteList = getAffectedTasks(startIndex, endIndex);
        try {
            updateCompletedTasks(tasksToCompleteList);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NOT_COMPLETED);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, tasksToCompleteList.get(0)));
    }
}
