package seedu.typed.logic.commands;

import java.util.Optional;

import seedu.typed.commons.util.Triple;
import seedu.typed.logic.commands.exceptions.CommandException;
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

    public static final String COMMAND_WORD_UNDO = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD_UNDO + ": Undoes the previous "
                                               + "add/delete/edit/clear command "
                                               + "in the current session.\n"
                                               + "Parameters: [NUMBER]\n"
                                               + "Example: " + COMMAND_WORD_UNDO + " 2";
    public static final String MESSAGE_SUCCESS = "Undone successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Undone all successfully!";
    public static final String MESSAGE_SINGLE_SUCCESS = "Undone successfully 1 command!";
    public static final String MESSAGE_MULTIPLE_SUCCESS = "Undone successfully %1$s commands!";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Undone successfully %1$s commands only!";
    public static final String MESSAGE_SINGLE_PARTIAL_SUCCESS = "Redone successfully 1 command only!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to undo!";
    public static final String MESSAGE_ERROR = "Cannot undo previous command!";

    private static final int ALL_NUM = -1;
    private static final int INVALID_INDEX = -1;

    private int numberOfCmdsToUndo;
    private String type;


    public UndoCommand() {
        numberOfCmdsToUndo = 1;
        type = "no number";
    }

    public UndoCommand(int num) {
        numberOfCmdsToUndo = num;
        if (num == ALL_NUM) {
            type = "all";
        } else {
            type = "number";
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        int maxNumberToUndo = session.getUndoStack().size();

        if (numberOfCmdsToUndo == ALL_NUM) {
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
        Optional<Triple<String, Integer, Object>> optionalTriple = session.popUndoStack();

        Triple<String, Integer, Object> toPush = optionalTriple.get();
        String command = toPush.getFirst();
        int index = toPush.getSecond();
        Object change = toPush.getThird();

        try {
            switch(command) {

            case AddCommand.COMMAND_WORD_ADD:
                TaskManager currentTaskManager = new TaskManager(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(DeleteCommand.COMMAND_WORD_DELETE);
                toPush.setThird(currentTaskManager);
                session.updateUndoRedoStacks(COMMAND_WORD_UNDO, INVALID_INDEX, toPush);
                break;

            case DeleteCommand.COMMAND_WORD_DELETE:
                model.deleteTaskAt(index);
                toPush.setFirst(AddCommand.COMMAND_WORD_ADD);
                session.updateUndoRedoStacks(COMMAND_WORD_UNDO, INVALID_INDEX, toPush);
                break;

            case EditCommand.COMMAND_WORD_EDIT:
                Task currentTask = new TaskBuilder(model.getTaskAt(index)).build();
                toPush.setThird(currentTask);
                model.updateTaskForUndoRedo(index, (ReadOnlyTask) change);
                session.updateUndoRedoStacks(COMMAND_WORD_UNDO, INVALID_INDEX, toPush);
                break;

            case ClearCommand.COMMAND_WORD_UNCLEAR:
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(ClearCommand.COMMAND_WORD_CLEAR);
                toPush.setThird(new TaskManager());
                session.updateUndoRedoStacks(COMMAND_WORD_UNDO, INVALID_INDEX, toPush);
                break;

            case CompleteCommand.COMMAND_WORD_UNCOMPLETE:
                TaskManager currTaskManager = new TaskManager(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(CompleteCommand.COMMAND_WORD_COMPLETE);
                toPush.setThird(currTaskManager);
                session.updateUndoRedoStacks(COMMAND_WORD_UNDO, INVALID_INDEX, toPush);
                break;

            default:
                break;

            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}


