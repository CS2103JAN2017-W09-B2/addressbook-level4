package seedu.typed.logic.commands;

/**
 * Lists all past successful commands executed in the task manager in the current session to the user.
 */
public class HistoryCommand extends Command {

    //@@author A0141094M-unused
    public static final String COMMAND_WORD_HISTORY = "history";
    public static final String COMMAND_WORD_HIST = "hist";
    public static final String COMMAND_WORD_LOG = "log";
    //@@author

    //@@author A0143853A-unused

    public static final String MESSAGE_SUCCESS = "Listed history of past commands in current session";


    public HistoryCommand() {
    }

    @Override
    public CommandResult execute() {
        session.listValidCommandsHistory();
        //code to show history on GUI
        session.updateUndoRedoStacks(COMMAND_WORD_HISTORY, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
