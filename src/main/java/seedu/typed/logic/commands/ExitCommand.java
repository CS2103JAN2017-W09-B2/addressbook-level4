package seedu.typed.logic.commands;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.ExitAppRequestEvent;
import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    //@@author A0141094M
    public static final String EXIT_COMMAND_WORD = "exit";
    public static final String QUIT_COMMAND_WORD = "quit";
    public static final String LOGOUT_COMMAND_WORD = "logout";
    public static final String BYE_COMMAND_WORD = "logout";
    //@@author

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Manager as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_EXIT, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
