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
 * Redoes the previous undone command in the task manager.
 * Entering a new mutable command clears the stack of undone commands to redo.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD_REDO = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD_REDO + ": Redoes the previous undone command "
                                               + "in the current session.\n"
                                               + "Parameters: [NUMBER]\n"
                                               + "Example: " + COMMAND_WORD_REDO + " 2";
    public static final String MESSAGE_SUCCESS = "Redone successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Redone all successfully!";
    public static final String MESSAGE_SINGLE_SUCCESS = "Redone successfully 1 command!";
    public static final String MESSAGE_MULTIPLE_SUCCESS = "Redone successfully %1$s commands!";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Redone successfully %1$s commands only!";
    public static final String MESSAGE_SINGLE_PARTIAL_SUCCESS = "Redone successfully 1 command only!";
    public static final String MESSAGE_NO_COMMAND_TO_REDO = "There is no undo to redo!";
    public static final String MESSAGE_ERROR = "Cannot redo previous undo!";

    private static final int ALL_NUM = -1;
    private static final int INVALID_INDEX = -1;

    private int numberOfCmdsToRedo;
    private String type;

    public RedoCommand() {
        numberOfCmdsToRedo = 1;
        type = "no number";
    }

    public RedoCommand(int num) {
        numberOfCmdsToRedo = num;
        if (num == ALL_NUM) {
            type = "all";
        } else {
            type = "number";
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        int maxNumberToRedo = session.getRedoStack().size();

        if (numberOfCmdsToRedo == ALL_NUM) {
            numberOfCmdsToRedo = maxNumberToRedo;
        }

        if (maxNumberToRedo == 0) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_REDO);
        }

        int actualNumberOfCmdsToRedo = numberOfCmdsToRedo;
        if (numberOfCmdsToRedo > maxNumberToRedo) {
            actualNumberOfCmdsToRedo = maxNumberToRedo;
        }

        for (int count = 0; count < actualNumberOfCmdsToRedo; count++) {
            executeRedoCommand();
        }

        switch (type) {

        case "no number":
            return new CommandResult(MESSAGE_SUCCESS);

        case "all":
            return new CommandResult(MESSAGE_ALL_SUCCESS);

        case "number":
            return commandResultBasedOnNumber(maxNumberToRedo);

        default:
            return new CommandResult(MESSAGE_SUCCESS);

        }
    }

    private CommandResult commandResultBasedOnNumber(int maxNumber) {

        int actualNumber = numberOfCmdsToRedo;
        if (numberOfCmdsToRedo > maxNumber) {
            actualNumber = maxNumber;
        }

        if (numberOfCmdsToRedo == 1) {
            return new CommandResult(MESSAGE_SINGLE_SUCCESS);
        } else if ((actualNumber < numberOfCmdsToRedo)
                   && (actualNumber == 1)) {
            return new CommandResult(MESSAGE_SINGLE_PARTIAL_SUCCESS);
        } else if (actualNumber < numberOfCmdsToRedo) {
            return new CommandResult(String.format(MESSAGE_PARTIAL_SUCCESS,
                                                   actualNumber));
        } else {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_SUCCESS,
                                                   numberOfCmdsToRedo));
        }
    }


    @SuppressWarnings("unchecked")
    private void executeRedoCommand() throws CommandException {
        Optional<Triple<String, Integer, Object>> optionalTriple = session.popRedoStack();

        Triple<String, Integer, Object> toPush = optionalTriple.get();
        String command = toPush.getFirst();
        int index = toPush.getSecond();
        Object change = toPush.getThird();

        try {
            switch(command) {

            case AddCommand.COMMAND_WORD_ADD:
                model.addTask(index, (Task) change);
                toPush.setFirst(DeleteCommand.COMMAND_WORD_DELETE);
                session.updateUndoRedoStacks(COMMAND_WORD_REDO, INVALID_INDEX, toPush);
                break;

            case DeleteCommand.COMMAND_WORD_DELETE:
                TaskManager currTaskManager = new TaskManager(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(AddCommand.COMMAND_WORD_ADD);
                toPush.setThird(currTaskManager);
                session.updateUndoRedoStacks(COMMAND_WORD_REDO, INVALID_INDEX, toPush);
                break;

            case EditCommand.COMMAND_WORD_EDIT:
                Task currentTask = new TaskBuilder(model.getTaskAt(index)).build();
                toPush.setThird(currentTask);
                model.updateTaskForUndoRedo(index, (ReadOnlyTask) change);
                session.updateUndoRedoStacks(COMMAND_WORD_REDO, INVALID_INDEX, toPush);
                break;

            case ClearCommand.COMMAND_WORD_CLEAR:
                TaskManager currentTaskManager = new TaskManager(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(ClearCommand.COMMAND_WORD_UNCLEAR);
                toPush.setThird(currentTaskManager);
                session.updateUndoRedoStacks(COMMAND_WORD_REDO, INVALID_INDEX, toPush);
                break;

            case CompleteCommand.COMMAND_WORD_COMPLETE:
                TaskManager current = new TaskManager(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setFirst(CompleteCommand.COMMAND_WORD_UNCOMPLETE);
                toPush.setThird(current);
                session.updateUndoRedoStacks(COMMAND_WORD_REDO, INVALID_INDEX, toPush);
                break;

            default:
                break;

            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}
