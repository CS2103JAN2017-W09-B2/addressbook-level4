package seedu.typed.logic.commands;

import java.util.ArrayList;
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
 * Redoes the previous undone command in the task manager.
 * Entering a new mutable command clears the stack of undone commands to redo.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previous undone command "
                                               + "in the current session.\n"
                                               + "Parameters: [NUMBER]\n"
                                               + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_SUCCESS = "Redone successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Redone all successfully!";
    public static final String MESSAGE_SINGLE_SUCCESS = "Redone successfully 1 command!";
    public static final String MESSAGE_MULTIPLE_SUCCESS = "Redone successfully %1$s commands!";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Redone successfully %1$s commands only!";
    public static final String MESSAGE_SINGLE_PARTIAL_SUCCESS = "Redone successfully 1 command only!";
    public static final String MESSAGE_NO_COMMAND_TO_REDO = "There is no undo to redo!";
    public static final String MESSAGE_ERROR = "Cannot redo previous undo!";

    private int numberOfCmdsToRedo;
    private String type;

    public RedoCommand() {
        numberOfCmdsToRedo = 1;
        type = "no number";
    }

    public RedoCommand(int num) {
        numberOfCmdsToRedo = num;
        if (num == -1) {
            type = "all";
        } else {
            type = "number";
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        int maxNumberToRedo = session.getRedoStack().size();

        if (numberOfCmdsToRedo == -1) {
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

        session.updateValidCommandsHistory(commandText);
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
        Optional<TripleUtil<String, Integer, Object>> optionalTriple = session.popRedoStack();

        TripleUtil<String, Integer, Object> toPush = optionalTriple.get();
        String command = toPush.getFirst();
        int index = toPush.getSecond();
        Object change = toPush.getThird();

        try {
            switch(command) {

            case CommandTypeUtil.TYPE_ADD_TASK:
                model.addTask(index, (Task) change);
                toPush.setFirst(CommandTypeUtil.TYPE_DELETE_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_REDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                model.deleteTask((ReadOnlyTask) change);
                toPush.setFirst(CommandTypeUtil.TYPE_ADD_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_REDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_EDIT_TASK:
                Task currentTask = new TaskBuilder(model.getTaskAt(index)).build();
                toPush.setThird(currentTask);
                model.updateTaskForUndoRedo(index, (ReadOnlyTask) change);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_REDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_CLEAR:
                TaskManager currentTaskManager = new TaskManager();
                currentTaskManager.copyData(model.getTaskManager());
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setThird(currentTaskManager);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_REDO, -1, toPush);
                break;

            case CommandTypeUtil.TYPE_COMPLETE:
                ArrayList<Integer> listOfIndices = (ArrayList<Integer>) toPush.getThird();
                for (int curr = 0; curr < listOfIndices.size(); curr++) {
                    int indexToComplete = listOfIndices.get(curr);
                    model.completeTaskAtForUndoRedo(indexToComplete);
                }
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_REDO, -1, toPush);
                break;

            default:
                break;

            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}
