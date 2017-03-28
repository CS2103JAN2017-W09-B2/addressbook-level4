package seedu.typed.logic.commands;

import java.util.Optional;

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

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous add/delete/edit/clear command "
                                               + "in the current session.\n"
                                               + "Parameters: [NUMBER]\n"
                                               + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_SUCCESS = "Undone successfully!";
    public static final String MESSAGE_MULTIPLE_SUCCESS = "Undone successfully %1s commands!";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Undone successfully %1s commands only!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to undo!";
    public static final String MESSAGE_ERROR = "Cannot undo previous command!";

    private int numberOfCmdsToUndo;


    public UndoCommand() {
        numberOfCmdsToUndo = 1;
    }

    public UndoCommand(int num) {
        numberOfCmdsToUndo = num;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        int maxNumberToUndo = session.getUndoStack().size();
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
        if (numberOfCmdsToUndo == 1) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (actualNumberOfCmdsToUndo < numberOfCmdsToUndo) {
            return new CommandResult(String.format(MESSAGE_PARTIAL_SUCCESS,
                                                   actualNumberOfCmdsToUndo));
        } else {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_SUCCESS,
                                                   numberOfCmdsToUndo));
        }

    }

    private void executeUndoCommand() throws CommandException {
        Optional<TripleUtil<String, Integer, Object>> optionalTriple = session.popUndoStack();

        TripleUtil<String, Integer, Object> toPush = optionalTriple.get();
        String command = toPush.getFirst();
        int index = toPush.getSecond();
        Object change = toPush.getThird();

        try {
            switch(command) {

            case CommandTypeUtil.TYPE_ADD_TASK:
                model.addTask(index, (Task) change);
                toPush.setFirst(CommandTypeUtil.TYPE_DELETE_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                model.deleteTask((ReadOnlyTask) change);
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

            default:
                break;

            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}


