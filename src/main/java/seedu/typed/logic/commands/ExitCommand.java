package seedu.typed.logic.commands;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    //@@author A0141094M
    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String COMMAND_WORD_QUIT = "quit";
    public static final String COMMAND_WORD_LOGOUT = "logout";
    public static final String COMMAND_WORD_BYE = "bye";
    //@@author

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Manager as requested ...";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        session.updateUndoRedoStacks(COMMAND_WORD_EXIT, -1, null);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
