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
                                               + "Parameters: none\n"
                                               + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Undone successfully!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to undo!";
    public static final String MESSAGE_ERROR = "Cannot undo previous command!";


    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        Optional<TripleUtil<String, Integer, Object>> optionalTriple = session.popUndoStack();

        if (optionalTriple.equals(Optional.empty())) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }

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
                session.updateValidCommandsHistory(commandText);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                model.deleteTask((ReadOnlyTask) change);
                toPush.setFirst(CommandTypeUtil.TYPE_ADD_TASK);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                session.updateValidCommandsHistory(commandText);
                break;

            case CommandTypeUtil.TYPE_EDIT_TASK:
                Task currentTask = new TaskBuilder(model.getTaskAt(index)).build();
                toPush.setThird(currentTask);
                model.updateTaskForUndoRedo(index, (ReadOnlyTask) change);
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                session.updateValidCommandsHistory(commandText);
                break;

            case CommandTypeUtil.TYPE_CLEAR:
                model.resetData((ReadOnlyTaskManager) change);
                toPush.setThird(new TaskManager());
                session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, -1, toPush);
                session.updateValidCommandsHistory(commandText);
                break;

            default:
                break;

            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}