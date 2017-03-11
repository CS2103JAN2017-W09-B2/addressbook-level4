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
        Optional<TripleUtil<String, Task, Task>> optionalCmd = session.popUndoStack();

        if (optionalCmd.equals(Optional.empty())) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }

        TripleUtil<String, Task, Task> cmd = optionalCmd.get();
        String cmdString = cmd.getFirst();
        Task firstTask = cmd.getSecond();
        Task secondTask = cmd.getThird();
        TripleUtil<String, Task, Task> toPush = new TripleUtil<String, Task, Task>("", null, null);
        try {

            switch(cmdString) {

            case "add":
                model.addTask(firstTask);
                toPush.setFirst("delete");
                toPush.setSecond(firstTask);
                session.pushRedoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            case "delete":
                model.deleteTask((ReadOnlyTask) firstTask);
                toPush.setFirst("add");
                toPush.setSecond(firstTask);
                session.pushRedoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            case "edit":
                model.deleteTask((ReadOnlyTask) firstTask);
                model.addTask(secondTask);
                toPush.setFirst("edit");
                toPush.setSecond(secondTask);
                toPush.setThird(firstTask);
                session.pushRedoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            default:
                throw new CommandException(MESSAGE_ERROR);
            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}
