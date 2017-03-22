package seedu.typed.logic.commands;

import java.util.Optional;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;

/**
 * Undoes an add/delete/edit command on the task manager.
 * @author Le Yuan
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous add/delete/edit/clear command"
            + "in the current session."
            + "Parameters: none" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undone previous command";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to be undone";
    public static final String MESSAGE_ERROR = "Cannot undo previous command";


    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        Optional<TripleUtil<String, Object, Object>> optionalCmd = this.session.popUndoStack();

        if (optionalCmd.equals(Optional.empty())) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }

        TripleUtil<String, Object, Object> cmd = optionalCmd.get();
        String cmdString = cmd.getFirst();
        Object first = cmd.getSecond();
        Object second = cmd.getThird();
        TripleUtil<String, Object, Object> toPush = new TripleUtil<String, Object, Object>("", null, null);
        try {

            switch(cmdString) {

            case CommandTypeUtil.TYPE_ADD_TASK:
                this.model.addTask((Task) first);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_ADD_TASK));
                toPush.setSecond(first);
                this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, toPush, null);
                this.session.updateValidCommandsHistory(this.commandText);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                this.model.deleteTask((ReadOnlyTask) first);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_DELETE_TASK));
                toPush.setSecond(first);
                this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, toPush, null);
                this.session.updateValidCommandsHistory(this.commandText);
                break;

            case CommandTypeUtil.TYPE_EDIT_TASK:
                this.model.deleteTask((ReadOnlyTask) first);
                this.model.addTask((Task) second);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_EDIT_TASK));
                toPush.setSecond(second);
                toPush.setThird(first);
                this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, toPush, null);
                this.session.updateValidCommandsHistory(this.commandText);
                break;

            case CommandTypeUtil.TYPE_CLEAR:
                ReadOnlyTaskManager secondTaskManager = (ReadOnlyTaskManager) second;
                this.model.resetData(secondTaskManager);
                TaskManager third = new TaskManager();
                third.copyData(secondTaskManager);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_CLEAR));
                toPush.setSecond(third);
                toPush.setThird(first);
                this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_UNDO, toPush, null);
                this.session.updateValidCommandsHistory(this.commandText);
                break;

            default:
                throw new CommandException(MESSAGE_ERROR);
            }

            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}
