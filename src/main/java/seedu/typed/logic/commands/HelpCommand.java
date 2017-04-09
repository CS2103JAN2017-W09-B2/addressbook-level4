package seedu.typed.logic.commands;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    //@@author A0141094M
    public static final String COMMAND_WORD_HELP = "help";
    public static final String COMMAND_WORD_MAN = "man";
    public static final String COMMAND_WORD_SOS = "sos";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORD_HELP + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD_HELP;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        session.updateUndoRedoStacks(COMMAND_WORD_HELP, -1, null);
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
