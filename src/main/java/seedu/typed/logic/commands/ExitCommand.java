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
        session.update(CommandTypeUtil.TYPE_EXIT, null, null);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
