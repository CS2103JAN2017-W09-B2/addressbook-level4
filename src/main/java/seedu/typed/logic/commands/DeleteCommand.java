package seedu.typed.logic.commands;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0143853A
/**
 * Deletes a task identified using its last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command {
    //@@author A0141094M
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_DEL = "del";
    public static final String COMMAND_WORD_REMOVE = "remove";
    public static final String COMMAND_WORD_RM = "rm";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORD_DELETE
            + ": Deletes the task(s) identified by the index number(s) used in the last task listing. "
            + "Parameters: INDEX or RANGE\n"
            + "Example: " + COMMAND_WORD_DELETE + " 1 to 2";


    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_TASKS_SUCCESS = "Deleted %1$d tasks!";

    private static final int ALL_INDEX = -1;


    private int startIndex;
    private int endIndex;


    public DeleteCommand(int index) {
        assert index > 0;

        startIndex = index - 1;
        endIndex = startIndex;
    }

    public DeleteCommand(int startIndex, int endIndex) {
        assert startIndex > 0;
        assert endIndex > 0;

        this.startIndex = startIndex - 1;
        this.endIndex = endIndex - 1;
    }

    public DeleteCommand() {
        startIndex = ALL_INDEX;
        endIndex = ALL_INDEX;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (startIndex == ALL_INDEX) {
            startIndex = 0;
            endIndex = model.getFilteredTaskList().size() - 1;
        }

        if (endIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        int numOfTasksToDelete = endIndex - startIndex + 1;
        String firstTaskName = lastShownList.get(startIndex).getName().getValue();

        try {
            TaskManager oldTaskManager = new TaskManager(model.getTaskManager());
            model.deleteTasks(startIndex, endIndex);
            session.updateUndoRedoStacks(COMMAND_WORD_DELETE, -1, oldTaskManager);
        } catch (TaskNotFoundException tnfe) {
            assert false: "The target task(s) cannot be missing!";
        } catch (IllegalValueException ive) {
            assert false: "The task manager must be able to be reset!";
        }

        return commandResultBasedOnNumberOfTasks(numOfTasksToDelete, firstTaskName);
    }

    private CommandResult commandResultBasedOnNumberOfTasks(int num, String firstTaskName) {
        if (num == 1) {
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, firstTaskName));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_TASKS_SUCCESS, num));
        }
    }
}
//@@author