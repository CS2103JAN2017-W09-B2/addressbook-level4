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
 * Redoes the previous undone command on the task manager.
 * @author Le Yuan
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previous undone command"
            + "in the current session."
            + "Parameters: none" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redone previous undone command";
    public static final String MESSAGE_NO_COMMAND_TO_REDO = "There is no undone command to be redone";
    public static final String MESSAGE_ERROR = "Cannot redo previous undone command";


    public RedoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        Optional<TripleUtil<String, Object, Object>> optionalCmd = session.popRedoStack();

        if (optionalCmd.equals(Optional.empty())) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_REDO);
        }

        TripleUtil<String, Object, Object> cmd = optionalCmd.get();
        String cmdString = cmd.getFirst();
        Object first = cmd.getSecond();
        Object second = cmd.getThird();
        TripleUtil<String, Object, Object> toPush = new TripleUtil<String, Object, Object>("", null, null);
        try {

            switch(cmdString) {

            case CommandTypeUtil.TYPE_ADD_TASK:
                model.addTask((Task) first);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_ADD_TASK));
                toPush.setSecond(first);
                session.update(CommandTypeUtil.TYPE_REDO, toPush, null);
                break;

            case CommandTypeUtil.TYPE_DELETE_TASK:
                model.deleteTask((ReadOnlyTask) first);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_DELETE_TASK));
                toPush.setSecond(first);
                session.update(CommandTypeUtil.TYPE_REDO, toPush, null);
                break;

            case CommandTypeUtil.TYPE_EDIT_TASK:
                model.deleteTask((ReadOnlyTask) first);
                model.addTask((Task) second);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_EDIT_TASK));
                toPush.setSecond(second);
                toPush.setThird(first);
                session.update(CommandTypeUtil.TYPE_REDO, toPush, null);
                break;

            case CommandTypeUtil.TYPE_CLEAR: //problematic
                ReadOnlyTaskManager firstTaskManager = (ReadOnlyTaskManager) first;
                model.resetData(firstTaskManager);
                TaskManager third = new TaskManager();
                third.copyData(firstTaskManager);
                toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_CLEAR));
                toPush.setSecond(second);
                toPush.setThird(third);
                session.update(CommandTypeUtil.TYPE_UNDO, toPush, null);
                break;

            default:
                throw new CommandException(MESSAGE_ERROR);
            }

            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}

