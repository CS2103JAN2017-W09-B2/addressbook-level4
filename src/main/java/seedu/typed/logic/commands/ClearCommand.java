package seedu.typed.logic.commands;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {
    //@@author A0141094M
    public static final String COMMAND_WORD_CLEAR = "clear";
    public static final String COMMAND_WORD_EMPTY = "empty";
    //@@author
    //@@author A0143853A
    public static final String COMMAND_WORD_UNCLEAR = "unclear";
    public static final String MESSAGE_USAGE = COMMAND_WORD_CLEAR + ": Clears all tasks on Typed. "
            + "Parameters: NONE\n"
            + "Example: " + COMMAND_WORD_CLEAR;

    public static final String MESSAGE_SUCCESS = "Typed has been cleared!";
    public static final String MESSAGE_FAILURE = "Typed cannot be cleared!";


    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        try {
            TaskManager oldTaskManager = new TaskManager(model.getTaskManager());
            model.resetData(new TaskManager());
            session.updateUndoRedoStacks(COMMAND_WORD_CLEAR, -1, oldTaskManager);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IllegalValueException ive) {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }
    //@@author
}
