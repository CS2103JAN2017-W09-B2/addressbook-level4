package seedu.typed.logic.commands;

import java.util.ArrayList;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.task.ReadOnlyTask;

public class CompleteCommand extends Command {
    //@@author A0141094M
    public static final String COMMAND_WORD_FINISH = "finish";
    public static final String COMMAND_WORD_MARK = "mark";
    public static final String COMMAND_WORD_DONE = "done";
    public static final String COMMAND_WORD_CHECK = "check";
    public static final String COMMAND_WORD_COMPLETE = "complete";
    public static final String COMMAND_WORD_END = "end";
    //@@author

    //@@author A0143853A
    public static final String MESSAGE_USAGE = COMMAND_WORD_FINISH + ": Marks task(s) as completed "
            + "by the index number(s) used in the last task listing.\n"
            + "Parameters: INDEX or RANGE\n"
            + "Example: " + COMMAND_WORD_FINISH + " 1 to 3";
    //@@author

    //@@author A0139379M
    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_COMPLETED_TASKS_SUCCESS = "Completed %1$d tasks!";
    public static final String MESSAGE_NOT_COMPLETED = "Task does not exist on Typed!";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task is already completed on Typed.";
    public static final String COMMAND_WORD_UNCOMPLETE = "uncomplete";

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
     * Assumes that startIndex <= endIndex
     *
     * @param startIndex the start index as seen on Typed
     * @param endIndex the end index as seen on Typed
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
        startIndex = -1;
        endIndex = -1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ArrayList<Integer> listOfIndices = new ArrayList<Integer>();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (startIndex == -1) {
            startIndex = 0;
            endIndex = model.getFilteredTaskList().size() - 1;
        }

        if (endIndex > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }


        try {
            model.completeTasksAndStoreIndices(startIndex, endIndex, listOfIndices);
            session.updateUndoRedoStacks(COMMAND_WORD_COMPLETE, -1, listOfIndices);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return commandResultBasedOnListOfIndices(listOfIndices);
    }
    //@@author

    //@@author A0143853A
    /**
     * Returns a CommandResult with a message depending on the number of tasks completed.
     *
     * @param list
     *          used to store indices of tasks completed
     * @return CommandResult
     */
    private CommandResult commandResultBasedOnListOfIndices(ArrayList<Integer> list) {
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
