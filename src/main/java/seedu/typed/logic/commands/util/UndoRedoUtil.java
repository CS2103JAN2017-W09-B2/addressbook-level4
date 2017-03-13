package seedu.typed.logic.commands.util;

import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.TripleUtil;

public class UndoRedoUtil {

    public static Optional<TripleUtil<String, Object, Object>> pop(Stack<TripleUtil<String, Object, Object>> stack) {
        if (!stack.empty()) {
            return Optional.of(stack.pop());
        } else {
            return Optional.empty();
        }
    }

    public static void clear(Stack<TripleUtil<String, Object, Object>> stack) {
        stack.clear();
    }

    public static boolean isEmpty(Stack<TripleUtil<String, Object, Object>> stack) {
        return stack.empty();
    }

    @SuppressWarnings("unchecked")
    public static void update(Stack<TripleUtil<String, Object, Object>> undoStack,
                              Stack<TripleUtil<String, Object, Object>> redoStack,
                              String command, Object first, Object second) {

        TripleUtil<String, Object, Object> toPush = new TripleUtil<String, Object, Object>(command,
                                                                                           first,
                                                                                           second);
        switch(command) {

        case "undo Task":
            toPush = (TripleUtil<String, Object, Object>) first;
            redoStack.push(toPush);
            break;

        case "redo Task":
            toPush = (TripleUtil<String, Object, Object>) first;
            undoStack.push(toPush);
            break;

        case "add Task":
            redoStack.clear();
            toPush.setFirst("delete Task");
            undoStack.push(toPush);
            break;

        case "delete Task":
            redoStack.clear();
            toPush.setFirst("add Task");
            undoStack.push(toPush);
            break;

        case "edit Task":
            redoStack.clear();
            toPush.setSecond(second);
            toPush.setThird(first);
            undoStack.push(toPush);
            break;

        case "clear":
            redoStack.clear();
            undoStack.clear();
            break;

        case "find Task":
            break;

        case "help":
            break;

        case "history":
            break;

        case "list Task":
            break;

        case "select Task":
            break;

        case "exit":
            break;

        default:
            break;
        }
    }
}
