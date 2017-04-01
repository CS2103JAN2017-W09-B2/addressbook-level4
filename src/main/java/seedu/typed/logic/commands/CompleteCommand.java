package seedu.typed.logic.commands;

import java.util.ArrayList;

import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;

public class CompleteCommand extends Command {
    //@@author A0139379M
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task as completed "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_COMPLETED_TASKS_SUCCESS = "Completed %1$d tasks.";
    public static final String MESSAGE_NOT_COMPLETED = "Task does not exist in the task manager";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task is already completed in the task manager.";

    private int startIndex;
    private int endIndex;

    /**
     * @param startIndex
     *            the index of the task in the filtered task list to complete
     */
    public CompleteCommand(int index) {
        assert index > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        startIndex = index - 1;
        endIndex = startIndex;
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
        this.endIndex = model.getFilteredTaskList().size() - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ArrayList<Integer> listOfIndices = new ArrayList<Integer>();
        try {
            model.completeTasksAndStoreIndices(startIndex, endIndex, listOfIndices);
            session.updateUndoRedoStacks(CommandTypeUtil.TYPE_COMPLETE, -1, listOfIndices);
            session.updateValidCommandsHistory(commandText);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return commandResultBasedOnIndicesList(listOfIndices);
    }
    //@@author

    //@@author A0143853A
    private CommandResult commandResultBasedOnIndicesList(ArrayList<Integer> list) {
        if (list.size() == 1) {
            int taskIndex = list.get(0);
            String taskName = model.getTaskAt(taskIndex).getName().getValue();
            return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskName));
        } else {
            return new CommandResult(String.format(MESSAGE_COMPLETED_TASKS_SUCCESS, list.size()));
        }
    }
    //@@author
}
