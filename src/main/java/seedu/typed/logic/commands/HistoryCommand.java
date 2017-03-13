package seedu.typed.logic.commands;

import java.util.ArrayList;

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
        ArrayList<String> historyArrayList = session.getHistory();
        for (int i = 1; i <= historyArrayList.size(); i++) {
            System.out.println(i + ") " + historyArrayList.get(i - 1));
        }
        //code to show history on GUI
        session.update("history", null, null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
