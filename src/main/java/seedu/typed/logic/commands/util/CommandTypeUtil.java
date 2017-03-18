package seedu.typed.logic.commands.util;

public class CommandTypeUtil {

    public static final String TYPE_ADD_TASK = "add Task";
    public static final String TYPE_DELETE_TASK = "delete Task";
    public static final String TYPE_EDIT_TASK = "edit Task";
    public static final String TYPE_UNDO = "undo";
    public static final String TYPE_REDO = "redo";
    public static final String TYPE_CLEAR = "clear";
    public static final String TYPE_EXIT = "exit";
    public static final String TYPE_FIND_TASK = "find Task";
    public static final String TYPE_HELP = "help";
    public static final String TYPE_HISTORY = "history";
    public static final String TYPE_LIST_TASK = "list Task";
    public static final String TYPE_SELECT_TASK = "select Task";
    public static final String TYPE_SORT_TASK = "sort Task";

    public static String opposite(String commandType) {

        switch(commandType) {

        case TYPE_ADD_TASK:
            return TYPE_DELETE_TASK;

        case TYPE_DELETE_TASK:
            return TYPE_ADD_TASK;

        case TYPE_EDIT_TASK:
            return TYPE_EDIT_TASK;

        case TYPE_UNDO:
            return TYPE_REDO;

        case TYPE_REDO:
            return TYPE_UNDO;

        case TYPE_CLEAR:
            return TYPE_CLEAR;

        case TYPE_EXIT:
            return TYPE_EXIT;

        case TYPE_FIND_TASK:
            return TYPE_FIND_TASK;

        case TYPE_HELP:
            return TYPE_HELP;

        case TYPE_HISTORY:
            return TYPE_HISTORY;

        case TYPE_LIST_TASK:
            return TYPE_LIST_TASK;

        case TYPE_SELECT_TASK:
            return TYPE_SELECT_TASK;

        default:
            return commandType;
        }
    }


}
