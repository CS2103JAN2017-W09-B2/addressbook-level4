package seedu.typed.logic.commands;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.ExitAppRequestEvent;
import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Manager as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_EXIT, null, null);
        this.session.updateValidCommandsHistory(this.commandText);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
