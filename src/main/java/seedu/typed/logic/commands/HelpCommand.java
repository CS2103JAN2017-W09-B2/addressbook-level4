package seedu.typed.logic.commands;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.ShowHelpRequestEvent;
import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_HELP, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
