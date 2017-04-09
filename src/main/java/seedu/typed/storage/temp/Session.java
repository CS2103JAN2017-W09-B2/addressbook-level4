package seedu.typed.storage.temp;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.Triple;
import seedu.typed.logic.commands.util.HistoryUtil;
import seedu.typed.logic.commands.util.UndoRedoUtil;

//@@author A0143853A
/**
 * Keeps track of commands to undo and redo in a session.
 * Keeps track of history of commands entered
 *
 */

public class Session {
    private HistoryUtil history;
    private Stack<Triple<String, Integer, Object>> undoStack;
    private Stack<Triple<String, Integer, Object>> redoStack;

    public Session() {
        this.history = new HistoryUtil();
        this.undoStack = new Stack<Triple<String, Integer, Object>>();
        this.redoStack = new Stack<Triple<String, Integer, Object>>();
    }

    public void updateAllCommandsHistory(String command) {
        history.addCommand(command);
    }

    public void updateValidCommandsHistory(String command) {
        history.addValidCommand(command);
    }

    public ArrayList<String> getAllCommandsHistory() {
        return history.getAllCommandsHistory();
    }

    public ArrayList<String> getValidCommandsHistory() {
        return history.getValidCommandsHistory();
    }

    public void listAllCommandsHistory() {
        history.listAllCommands();
    }

    public void listValidCommandsHistory() {
        history.listValidCommands();
    }
    public void clearHistory() {
        history.clear();
    }

    public Stack<Triple<String, Integer, Object>> getUndoStack() {
        return undoStack;
    }

    public Stack<Triple<String, Integer, Object>> getRedoStack() {
        return redoStack;
    }

    public void updateUndoRedoStacks(String command, Integer index, Object toChange) {
        UndoRedoUtil.update(undoStack, redoStack, command, index, toChange);
    }

    public Optional<Triple<String, Integer, Object>> popUndoStack() {
        return UndoRedoUtil.pop(undoStack);
    }

    public Optional<Triple<String, Integer, Object>> popRedoStack() {
        return UndoRedoUtil.pop(redoStack);
    }
}
