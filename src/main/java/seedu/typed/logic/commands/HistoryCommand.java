package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;

//@@author A0143853A
/**
 * Lists all past successful commands executed in the task manager in the current session to the user.
 */
public class HistoryCommand extends Command {

    //@@author A0141094M
    public static final String HISTORY_COMMAND_WORD = "history";
    public static final String HIST_COMMAND_WORD = "hist";
    public static final String LOG_COMMAND_WORD = "log";
    //@@author

    public static final String MESSAGE_SUCCESS = "Listed history of past commands in current session";


    public HistoryCommand() {
    }

    @Override
    public CommandResult execute() {
        session.listValidCommandsHistory();
        //code to show history on GUI
        session.updateUndoRedoStacks(CommandTypeUtil.TYPE_HISTORY, -1, null);
        session.updateValidCommandsHistory(commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
