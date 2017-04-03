package seedu.typed.logic.commands;

import java.util.ArrayList;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.util.Pair;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0143853A
/**
 * Deletes a task identified using its last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) | RANGE\n"
            + "Example: " + COMMAND_WORD + " 1 to 2";


    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_TASKS_SUCCESS = "Deleted %1$d tasks!";

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
        startIndex = -1;
        endIndex = -1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ArrayList<Pair<Integer, Task>> listOfIndicesAndTasks = new ArrayList<Pair<Integer, Task>>();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (startIndex == -1) {
            startIndex = 0;
            endIndex = model.getFilteredTaskList().size() - 1;
        }

        if (endIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        try {
            model.deleteTasksAndStoreTasksAndIndices(startIndex, endIndex, listOfIndicesAndTasks);
            session.updateUndoRedoStacks(CommandTypeUtil.TYPE_DELETE_TASK, -1, listOfIndicesAndTasks);
            session.updateValidCommandsHistory(commandText);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task(s) cannot be missing";
        }

        return commandResultBasedOnIndicesAndTasksList(listOfIndicesAndTasks);
    }

    private CommandResult commandResultBasedOnIndicesAndTasksList(ArrayList<Pair<Integer, Task>> list) {
        if (list.size() == 1) {
            String taskName = list.get(0).getSecond().getName().getValue();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskName));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_TASKS_SUCCESS, list.size()));
        }
    }
}
