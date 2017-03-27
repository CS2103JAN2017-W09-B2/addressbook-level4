//@@author A0141094M
package seedu.typed.logic.commands;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.logic.commands.util.Type;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all undone and upcoming tasks by default, "
            + "or by the CATEGORY if specified. Valid CATEGORYs are: untimed, deadline, duration, done, undone and all."
            + "Parameters: [CATEGORY] \n"
            + "Example: " + COMMAND_WORD
            + " deadline ";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand(String type) throws IllegalValueException {
        switch (type) {
        case ("all"):
            System.out.println("i am in case 'all'");
            model.updateFilteredListToShowAll();
        case ("done"):
            model.updateFilteredListToShowDone();
        }
    }

    public ListCommand(Type type) throws IllegalValueException {
        switch (type) {
        case DEADLINE:
            model.updateFilteredListToShowDeadline();
        case DURATION:
            model.updateFilteredListToShowDuration();
        case DONE:
            model.updateFilteredListToShowDone();
        case UNDONE:
            model.updateFilteredListToShowUndone();
        case UNTIMED:
            model.updateFilteredListToShowUntimed();
        case ALL:
            System.out.println("i entered here trying to filter");
            model.updateFilteredListToShowAll();
        default:
            break;
        }
    }

    @Override
    public CommandResult execute() {
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_LIST_TASK, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
