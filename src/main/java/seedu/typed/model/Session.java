package seedu.typed.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.TripleUtil;
import seedu.typed.model.task.Task;

/**
 * Keeps track of commands to undo and redo in a session.
 * @author Le Yuan
 *
 */

public class Session {
    private ArrayList<String> history;
    private Stack<TripleUtil<String, Task, Task>> undoStack;
    private Stack<TripleUtil<String, Task, Task>> redoStack;

    public Session() {
        this.history = new ArrayList<String>();
        this.undoStack = new Stack<TripleUtil<String, Task, Task>>();
        this.redoStack = new Stack<TripleUtil<String, Task, Task>>();
    }

    public Optional<TripleUtil<String, Task, Task>> popUndoStack() {
        if (!undoStack.empty()) {
            return Optional.of(undoStack.pop());
        } else {
            return Optional.empty();
        }
    }

    public Optional<TripleUtil<String, Task, Task>> popRedoStack() {
        if (!redoStack.empty()) {
            return Optional.of(redoStack.pop());
        } else {
            return Optional.empty();
        }
    }

    public void pushUndoStack(TripleUtil<String, Task, Task> toPush) {
        this.undoStack.push(toPush);
    }

    public void pushRedoStack(TripleUtil<String, Task, Task> toPush) {
        this.redoStack.push(toPush);
    }

    public void clearRedoStack() {
        this.redoStack = new Stack<TripleUtil<String, Task, Task>>();
    }

    public void clearUndoStack() {
        this.undoStack = new Stack<TripleUtil<String, Task, Task>>();
    }
    public Stack<TripleUtil<String, Task, Task>> getUndoStack() {
        return this.undoStack;
    }

    public Stack<TripleUtil<String, Task, Task>> getRedoStack() {
        return this.redoStack;
    }

    public ArrayList<String> getHistory() {
        return this.history;
    }

    public void addHistory(String action) {
        this.history.add(action);
    }

    public void clearHistory() {
        this.history.clear();
    }
}
