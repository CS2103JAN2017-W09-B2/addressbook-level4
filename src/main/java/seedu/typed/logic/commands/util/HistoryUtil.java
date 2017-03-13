package seedu.typed.logic.commands.util;

import java.util.ArrayList;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.model.task.Task;

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
    public static final String TEXT_REDO_ADD_TASK = "Redone addition of Task: ";
    public static final String TEXT_REDO_DELETE_TASK = "Redone deletion of Task: ";
    public static final String TEXT_REDO_EDIT_TASK = "Redone edit of Task: ";
    public static final String TEXT_INVALID_COMMAND = "Keyed in an invalid command";

    @SuppressWarnings("unchecked")
    public static void update(ArrayList<String> history, String command,
                              Object firstObj, Object secondObj) {
        assert firstObj != null;
        String toAdd;

        switch(command) {

        case "clear":
            toAdd = TEXT_CLEAR;
            break;

        case "exit":
            toAdd = TEXT_EXIT;
            break;

        case "find Task":
            toAdd = TEXT_FIND_TASK + (String) firstObj;
            break;

        case "help":
            toAdd = TEXT_HELP;
            break;

        case "list Task":
            toAdd = TEXT_LIST_TASK;
            break;

        case "select Task":
            toAdd = TEXT_SELECT_TASK + (Task) firstObj;
            break;

        case "history":
            toAdd = TEXT_HISTORY;
            break;

        case "undo Task":
            toAdd = generateUndoHistoryString((TripleUtil<String, Object, Object>) firstObj);
            break;

        case "redo Task":
            toAdd = generateRedoHistoryString((TripleUtil<String, Object, Object>) firstObj);
            break;

        case "add Task":
            toAdd = TEXT_ADD_TASK + (Task) firstObj;
            break;

        case "delete Task":
            toAdd = TEXT_DELETE_TASK + (Task) firstObj;
            break;

        case "edit Task":
            toAdd = String.format(TEXT_EDIT_TASK, (Task) firstObj, (Task) secondObj);
            break;

        default:
            toAdd = TEXT_INVALID_COMMAND;
            break;
        }

        history.add(toAdd);
    }

    private static String generateRedoHistoryString(TripleUtil<String, Object, Object> state) {

        String command = state.getFirst();
        Task firstTask = (Task) state.getSecond();
        Task secondTask = (Task) state.getThird();

        switch(command) {

        case "add Task":
            return TEXT_REDO_DELETE_TASK + firstTask;

        case "delete Task":
            return TEXT_REDO_ADD_TASK + firstTask;

        case "edit Task":
            return TEXT_REDO_EDIT_TASK + secondTask;

        default:
            return "";
        }
    }

    public static String generateUndoHistoryString(TripleUtil<String, Object, Object> state) {

        String command = state.getFirst();
        Task firstTask = (Task) state.getSecond();

        switch(command) {

        case "add Task":
            return TEXT_UNDO_ADD_TASK + firstTask;

        case "delete Task":
            return TEXT_UNDO_DELETE_TASK + firstTask;

        case "edit Task":
            return TEXT_UNDO_EDIT_TASK + firstTask;

        default:
            return "";
        }
    }

    public static void clear(ArrayList<String> history) {
        history.clear();
    }
}
