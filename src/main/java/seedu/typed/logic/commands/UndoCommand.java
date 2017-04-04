package seedu.typed.logic.commands;

import java.util.ArrayList;
import java.util.Optional;

import seedu.typed.commons.util.Pair;
import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;

//@@author A0143853A
/**
 * Undoes an a mutable command entered in the task manager.
 */
public class UndoCommand extends Command {

    public static final String UNDO_COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = UNDO_COMMAND_WORD + ": Undoes the previous "
                                               + "add/delete/edit/clear command "
                                               + "in the current session.\n"
                                               + "Parameters: [NUMBER]\n"
                                               + "Example: " + UNDO_COMMAND_WORD + " 2";
    public static final String MESSAGE_SUCCESS = "Undone successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Undone all successfully!";
    public static final String MESSAGE_SINGLE_SUCCESS = "Undone successfully 1 command!";
    public static final String MESSAGE_MULTIPLE_SUCCESS = "Undone successfully %1$s commands!";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Undone successfully %1$s commands only!";
    public static final String MESSAGE_SINGLE_PARTIAL_SUCCESS = "Redone successfully 1 command only!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to undo!";
    public static final String MESSAGE_ERROR = "Cannot undo previous command!";

    private int numberOfCmdsToUndo;
    private String type;


    public UndoCommand() {
        numberOfCmdsToUndo = 1;
        type = "no number";
    }

    public UndoCommand(int num) {
        numberOfCmdsToUndo = num;
        if (num == -1) {
            type = "all";
        } else {
            type = "number";
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        int maxNumberToUndo = session.getUndoStack().size();

        if (numberOfCmdsToUndo == -1) {
            numberOfCmdsToUndo = maxNumberToUndo;
        }

        if (maxNumberToUndo == 0) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }

        int actualNumberOfCmdsToUndo = numberOfCmdsToUndo;
        if (numberOfCmdsToUndo > maxNumberToUndo) {
            actualNumberOfCmdsToUndo = maxNumberToUndo;
        }

        for (int count = 0; count < actualNumberOfCmdsToUndo; count++) {
            executeUndoCommand();
        }

        session.updateValidCommandsHistory(commandText);
        switch (type) {

        case "no number":
            return new CommandResult(MESSAGE_SUCCESS);

        case "all":
            return new CommandResult(MESSAGE_ALL_SUCCESS);

        case "number":
            return commandResultBasedOnNumber(maxNumberToUndo);

        default:
            return new CommandResult(MESSAGE_SUCCESS);

        }
    }

    private CommandResult commandResultBasedOnNumber(int maxNumber) {

        int actualNumber = numberOfCmdsToUndo;
        if (numberOfCmdsToUndo > maxNumber) {
            actualNumber = maxNumber;
        }

        if (numberOfCmdsToUndo == 1) {
            return new CommandResult(MESSAGE_SINGLE_SUCCESS);
        } else if ((actualNumber < numberOfCmdsToUndo)
                   && (actualNumber == 1)) {
            return new CommandResult(MESSAGE_SINGLE_PARTIAL_SUCCESS);
        } else if (actualNumber < numberOfCmdsToUndo) {
            return new CommandResult(String.format(MESSAGE_PARTIAL_SUCCESS,
                                                   actualNumber));
        } else {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_SUCCESS,
                                                   numberOfCmdsToUndo));
        }
    }

    @SuppressWarnings("unchecked")
    private void executeUndoCommand() throws CommandException {
        Optional<TripleUtil<String, Integer, Object>> optionalTriple = session.popUndoStack();

        TripleUtil<String, Integer, Object> toPush = optionalTriple.get();
        String command = toPush.getFirst();
        int index = toPush.getSecond();
        Object change = toPush.getThird();

        try {
            switch(command) {

            case CommandTypeUtil.TYPE_ADD_TASK:
                ArrayList<Pair<Integer, Task>> listOfIndicesAndTasks = (ArrayList<Pair<Integer, Task>>) change;
                model.addTasksForUndo(listOfIndicesAndTasks);
                toPush.setFirst(CommandTypeUtil.TYPE_DELETE_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                model.deleteTaskAt(index);
                toPush.setFirst(CommandTypeUtil.TYPE_ADD_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_EDIT_TASK:
                Task currentTask = new TaskBuilder(model.getTaskAt(index)).build();
                toPush.setThird(currentTask);
                model.updateTaskForUndoRedo(index, (ReadOnlyTask) change);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_CLEAR:
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setThird(new TaskManager());
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_COMPLETE:
                ArrayList<Integer> listOfIndices = (ArrayList<Integer>) change;
                model.uncompleteTasksAtForUndo(listOfIndices);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            default:
                break;

            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}


