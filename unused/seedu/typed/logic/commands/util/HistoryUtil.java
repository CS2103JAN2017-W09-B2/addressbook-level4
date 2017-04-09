package seedu.typed.logic.commands.util;

import java.util.ArrayList;

//@@author A0143853A
/**
 * Provides a data structure for "history" in Session class.
 *
 */
public class HistoryUtil {

    private ArrayList<String> allCommandsHistory;
    private ArrayList<String> validCommandsHistory;

    public HistoryUtil() {
        this.allCommandsHistory = new ArrayList<String>();
        this.validCommandsHistory = new ArrayList<String>();
    }

    public void addCommand(String command) {
        this.allCommandsHistory.add(command);
    }

    public void addValidCommand(String command) {
        this.validCommandsHistory.add(command);
    }

    public void listAllCommands() {
        for (int index = 1; index <= this.allCommandsHistory.size(); index++) {
            System.out.println(index + ") " + this.allCommandsHistory.get(index - 1));
        }
    }

    public void listValidCommands() {
        for (int index = 1; index <= this.validCommandsHistory.size(); index++) {
            System.out.println(index + ") " + this.validCommandsHistory.get(index - 1));
        }
    }

    public void clearAllCommandsHistory() {
        this.allCommandsHistory.clear();
    }

    public void clearValidCommandsHistory() {
        this.validCommandsHistory.clear();
    }

    public ArrayList<String> getAllCommandsHistory() {
        return this.allCommandsHistory;
    }

    public ArrayList<String> getValidCommandsHistory() {
        return this.validCommandsHistory;
    }

    public void clear() {
        this.clearAllCommandsHistory();
        this.clearValidCommandsHistory();
    }
}
