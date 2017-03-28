package seedu.typed.logic.commands;

import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager."
            + "Parameters: NAME [by DATE]  [#TAG]...\n"
            + "Example: " + COMMAND_WORD + " buy 5 broccolis by tomorrow #survival #grocery ";

    //public static final String MESSAGE_SUCCESS = "%1$s added";
    public static final String MESSAGE_SUCCESS = "Task Added!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String date, Set<String> tags) throws IllegalValueException {
        this.toAdd = new TaskBuilder().setName(name)
                                      .setDate(date)
                                      .setTags(tags)
                                      .build();
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert session != null;

        try {
            //@@author A0143853A
            model.addTask(0, toAdd);
            session.updateUndoRedoStacks(CommandTypeUtil.TYPE_ADD_TASK, 0, toAdd);
            session.updateValidCommandsHistory(commandText);
            //@@author
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

}
