package seedu.typed.storage.temp;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.util.HistoryUtil;
import seedu.typed.logic.commands.util.UndoRedoUtil;


/**
 * Keeps track of commands to undo and redo in a session.
 * Keeps track of history of commands entered
 * @author Le Yuan
 *
 */

public class Session {
    private HistoryUtil history;
    private Stack<TripleUtil<String, Object, Object>> undoStack;
    private Stack<TripleUtil<String, Object, Object>> redoStack;

    public Session() {
        this.history = new HistoryUtil();
        this.undoStack = new Stack<TripleUtil<String, Object, Object>>();
        this.redoStack = new Stack<TripleUtil<String, Object, Object>>();
    }

    public Stack<TripleUtil<String, Object, Object>> getUndoStack() {
        return this.undoStack;
    }

    public Stack<TripleUtil<String, Object, Object>> getRedoStack() {
        return this.redoStack;
    }

    public void updateUndoRedoStacks(String command, Object first, Object second) {
        UndoRedoUtil.update(this.undoStack, this.redoStack, command, first, second);
    }

    public void updateAllCommandsHistory(String command) {
        this.history.addCommand(command);
    }

    public void updateValidCommandsHistory(String command) {
        this.history.addValidCommand(command);
    }

    public ArrayList<String> getAllCommandsHistory() {
        return this.history.getAllCommandsHistory();
    }

    public ArrayList<String> getValidCommandsHistory() {
        return this.history.getValidCommandsHistory();
    }

    public void listAllCommandsHistory() {
        this.history.listAllCommands();
    }

    public void listValidCommandsHistory() {
        this.history.listValidCommands();
    }

    public Optional<TripleUtil<String, Object, Object>> popUndoStack() {
        return UndoRedoUtil.pop(this.undoStack);
    }

    public Optional<TripleUtil<String, Object, Object>> popRedoStack() {
        return UndoRedoUtil.pop(this.redoStack);
    }

    public void clearHistory() {
        this.history.clear();
    }
}
