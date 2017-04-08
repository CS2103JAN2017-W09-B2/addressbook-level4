//@@author A0141094M
package seedu.typed.commons.lexicon;

import java.util.ArrayList;

/**
 * Utility class to maintain the list of commands.
 *
 */
public class CommandUtil {

    public static final String COMMAND_ADD = "add";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_EDIT = "edit";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_FIND = "find";
    public static final String COMMAND_FINISH = "finish";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_HISTORY = "history";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_LOAD = "load";
    public static final String COMMAND_REDO = "redo";
    public static final String COMMAND_SAVE = "save";
    public static final String COMMAND_UNDO = "undo";


    public static ArrayList<String> getAllCommandWords() {
        ArrayList<String> commandWords = new ArrayList<String>();
        commandWords.add(COMMAND_ADD);
        commandWords.add(COMMAND_EDIT);
        commandWords.add(COMMAND_DELETE);
        commandWords.add(COMMAND_FINISH);
        commandWords.add(COMMAND_UNDO);
        commandWords.add(COMMAND_REDO);
        commandWords.add(COMMAND_HISTORY);
        commandWords.add(COMMAND_HELP);
        commandWords.add(COMMAND_LOAD);
        commandWords.add(COMMAND_SAVE);
        commandWords.add(COMMAND_EXIT);
        commandWords.add(COMMAND_FIND);
        commandWords.add(COMMAND_LIST);
        return commandWords;
    }

    public static boolean isCommandWord(String word) {
        return getAllCommandWords().contains(word);
    }

}
//@@author
