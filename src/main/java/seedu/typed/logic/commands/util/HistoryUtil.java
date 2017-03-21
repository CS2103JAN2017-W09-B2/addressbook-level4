package seedu.typed.logic.commands.util;

import java.util.ArrayList;

import seedu.typed.commons.util.TripleUtil;

public class HistoryUtil {

    public static final String TEXT_EXIT = "Exited Task Manager";
    public static final String TEXT_FIND_TASK = "Found task(s) with the keywords: ";
    public static final String TEXT_LIST_TASK = "Viewed all existing tasks";
    public static final String TEXT_HISTORY = "Viewed history of past commands in current session";
    public static final String TEXT_SELECT_TASK = "Selected Task: ";
    public static final String TEXT_HELP = "Requested for help";
    public static final String TEXT_CLEAR = "Cleared Task Manager";
    public static final String TEXT_ADD_TASK = "Added Task: ";
    public static final String TEXT_DELETE_TASK = "Deleted Task: ";
    public static final String TEXT_EDIT_TASK = "Edited Task: %1$s to Task: %2$s";
    public static final String TEXT_UNDO_ADD_TASK = "Undone addition of Task: ";
    public static final String TEXT_UNDO_DELETE_TASK = "Undone deletion of Task: ";
    public static final String TEXT_UNDO_EDIT_TASK = "Undone edit of Task: ";
    public static final String TEXT_UNDO_CLEAR = "Undone clearing of Task Manager";
    public static final String TEXT_REDO_ADD_TASK = "Redone addition of Task: ";
    public static final String TEXT_REDO_DELETE_TASK = "Redone deletion of Task: ";
    public static final String TEXT_REDO_EDIT_TASK = "Redone edit of Task: ";
    public static final String TEXT_REDO_CLEAR = "Redone clearing of Task Manager";
    public static final String TEXT_INVALID_COMMAND = "Keyed in an invalid command";

    @SuppressWarnings("unchecked")
    public static void update(ArrayList<String> history, String command,
                              Object firstObj, Object secondObj) {

        String toAdd;

        switch(command) {

        case CommandTypeUtil.TYPE_CLEAR:
            toAdd = TEXT_CLEAR;
            break;

        case CommandTypeUtil.TYPE_EXIT:
            toAdd = TEXT_EXIT;
            break;

        case CommandTypeUtil.TYPE_FIND_TASK:
            toAdd = TEXT_FIND_TASK + (String) firstObj;
            break;

        case CommandTypeUtil.TYPE_HELP:
            toAdd = TEXT_HELP;
            break;

        case CommandTypeUtil.TYPE_LIST_TASK:
            toAdd = TEXT_LIST_TASK;
            break;

        case CommandTypeUtil.TYPE_SELECT_TASK:
            toAdd = TEXT_SELECT_TASK + firstObj;
            break;

        case CommandTypeUtil.TYPE_HISTORY:
            toAdd = TEXT_HISTORY;
            break;

        case CommandTypeUtil.TYPE_UNDO:
            toAdd = generateUndoHistoryString((TripleUtil<String, Object, Object>) firstObj);
            break;

        case CommandTypeUtil.TYPE_REDO:
            toAdd = generateRedoHistoryString((TripleUtil<String, Object, Object>) firstObj);
            break;

        case CommandTypeUtil.TYPE_ADD_TASK:
            toAdd = TEXT_ADD_TASK + firstObj;
            break;

        case CommandTypeUtil.TYPE_DELETE_TASK:
            toAdd = TEXT_DELETE_TASK + firstObj;
            break;

        case CommandTypeUtil.TYPE_EDIT_TASK:
            toAdd = String.format(TEXT_EDIT_TASK, firstObj, secondObj);
            break;

        default:
            toAdd = TEXT_INVALID_COMMAND;
            break;
        }

        history.add(toAdd);
    }

    private static String generateRedoHistoryString(TripleUtil<String, Object, Object> state) {

        String command = state.getFirst();
        Object first = state.getSecond();
        Object second = state.getThird();

        switch(command) {

        case CommandTypeUtil.TYPE_ADD_TASK:
            return TEXT_REDO_DELETE_TASK + first;

        case CommandTypeUtil.TYPE_DELETE_TASK:
            return TEXT_REDO_ADD_TASK + first;

        case CommandTypeUtil.TYPE_EDIT_TASK:
            return TEXT_REDO_EDIT_TASK + second;

        case CommandTypeUtil.TYPE_CLEAR:
            return TEXT_REDO_CLEAR;

        default:
            return "";
        }
    }

    public static String generateUndoHistoryString(TripleUtil<String, Object, Object> state) {

        String command = state.getFirst();
        Object first = state.getSecond();

        switch(command) {

        case CommandTypeUtil.TYPE_ADD_TASK:
            return TEXT_UNDO_ADD_TASK + first;

        case CommandTypeUtil.TYPE_DELETE_TASK:
            return TEXT_UNDO_DELETE_TASK + first;

        case CommandTypeUtil.TYPE_EDIT_TASK:
            return TEXT_UNDO_EDIT_TASK + first;

        case CommandTypeUtil.TYPE_CLEAR:
            return TEXT_UNDO_CLEAR;

        default:
            return "";
        }
    }

    public static void clear(ArrayList<String> history) {
        history.clear();
    }
}