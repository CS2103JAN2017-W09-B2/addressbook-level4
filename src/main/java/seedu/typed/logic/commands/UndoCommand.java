package seedu.typed.logic.commands;

import java.util.Optional;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;

/**
 * Undoes an add/delete/edit command on the task manager.
 * @author Le Yuan
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous add/delete/edit command"
            + "in the current session."
            + "Parameters: none" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undone previous command";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no command to be undone";
    public static final String MESSAGE_ERROR = "Cannot undo previous command";


    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        Optional<TripleUtil<String, Object, Object>> optionalCmd = session.popUndoStack();

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

            case "add Task":
                model.addTask((Task) first);
                toPush.setFirst("delete Task");
                toPush.setSecond(first);
                session.update("undo Task", (Object) toPush, null);
                break;

            case "delete Task":
                model.deleteTask((ReadOnlyTask) first);
                toPush.setFirst("add Task");
                toPush.setSecond(first);
                session.update("undo Task", (Object) toPush, null);
                break;

            case "edit Task":
                model.deleteTask((ReadOnlyTask) first);
                model.addTask((Task) second);
                toPush.setFirst("edit Task");
                toPush.setSecond(second);
                toPush.setThird(first);
                session.update("undo Task", (Object) toPush, null);
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
