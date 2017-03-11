package seedu.typed.logic.commands;

import java.util.Optional;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
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
        Optional<TripleUtil<String, Task, Task>> optionalCmd = session.popRedoStack();

        if (optionalCmd.equals(Optional.empty())) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_REDO);
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
                session.addHistory("Redone deletion of Task: " + firstTask);
                toPush.setFirst("delete");
                toPush.setSecond(firstTask);
                session.pushUndoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            case "delete":
                model.deleteTask((ReadOnlyTask) firstTask);
                session.addHistory("Redone addition of Task: " + firstTask);
                toPush.setFirst("add");
                toPush.setSecond(firstTask);
                session.pushUndoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            case "edit":
                model.deleteTask((ReadOnlyTask) firstTask);
                model.addTask(secondTask);
                session.addHistory("Redone edit of Task: " + secondTask);
                toPush.setFirst("edit");
                toPush.setSecond(secondTask);
                toPush.setThird(firstTask);
                session.pushUndoStack(toPush);
                return new CommandResult(MESSAGE_SUCCESS);

            default:
                throw new CommandException(MESSAGE_ERROR);
            }
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }
}

