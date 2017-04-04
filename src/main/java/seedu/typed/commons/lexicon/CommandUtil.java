//@@author A0141094M
package seedu.typed.commons.lexicon;

import java.util.ArrayList;

/**
 * Utility class to maintain the list of commands.
 *
 */
public class CommandUtil {

    static String COMMAND_ADD = "add";
    static String COMMAND_DELETE = "delete";
    static String COMMAND_EDIT = "edit";
    static String COMMAND_EXIT = "exit";
    static String COMMAND_FIND = "find";
    static String COMMAND_FINISH = "finish";
    static String COMMAND_HELP = "help";
    static String COMMAND_HISTORY = "history";
    static String COMMAND_LIST = "list";
    static String COMMAND_LOAD = "load";
    static String COMMAND_REDO = "redo";
    static String COMMAND_SAVE = "save";
    static String COMMAND_UNDO = "undo";


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
