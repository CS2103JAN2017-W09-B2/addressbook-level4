package seedu.typed.logic.commands;

import seedu.typed.logic.commands.util.CommandTypeUtil;

/**
 * @author Le Yuan
 * Lists all past successful commands executed in the task manager in the current session to the user.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_SUCCESS = "Listed history of past commands in current session";


    public HistoryCommand() {
    }

    @Override
    public CommandResult execute() {
        this.session.listValidCommandsHistory();
        //code to show history on GUI
        this.session.updateUndoRedoStacks(CommandTypeUtil.TYPE_HISTORY, null, null);
        this.session.updateValidCommandsHistory(this.commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
