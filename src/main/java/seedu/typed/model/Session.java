package seedu.typed.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.logic.commands.util.HistoryUtil;
import seedu.typed.logic.commands.util.UndoRedoUtil;


/**
 * Keeps track of commands to undo and redo in a session.
 * @author Le Yuan
 *
 */

public class Session {
    private ArrayList<String> history;
    private Stack<TripleUtil<String, Object, Object>> undoStack;
    private Stack<TripleUtil<String, Object, Object>> redoStack;

    public Session() {
        this.history = new ArrayList<String>();
        this.undoStack = new Stack<TripleUtil<String, Object, Object>>();
        this.redoStack = new Stack<TripleUtil<String, Object, Object>>();
    }

    public Stack<TripleUtil<String, Object, Object>> getUndoStack() {
        return this.undoStack;
    }

    public Stack<TripleUtil<String, Object, Object>> getRedoStack() {
        return this.redoStack;
    }

    public void update(String command, Object first, Object second) {
        UndoRedoUtil.update(this.undoStack, this.redoStack, command, first, second);
        HistoryUtil.update(this.history, command, first, second);
    }

    public ArrayList<String> getHistory() {
        return this.history;
    }

    public Optional<TripleUtil<String, Object, Object>> popUndoStack() {
        return UndoRedoUtil.pop(this.undoStack);
    }

    public Optional<TripleUtil<String, Object, Object>> popRedoStack() {
        return UndoRedoUtil.pop(this.redoStack);
    }

    public void clearHistory() {
        HistoryUtil.clear(this.history);
    }
}
