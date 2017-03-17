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

        case CommandTypeUtil.TYPE_UNDO:
            toPush = (TripleUtil<String, Object, Object>) first;
            redoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_REDO:
            toPush = (TripleUtil<String, Object, Object>) first;
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_ADD_TASK:
            redoStack.clear();
            toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_ADD_TASK));
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_DELETE_TASK:
            redoStack.clear();
            toPush.setFirst(CommandTypeUtil.opposite(CommandTypeUtil.TYPE_DELETE_TASK));
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_EDIT_TASK:
            redoStack.clear();
            toPush.setSecond(second);
            toPush.setThird(first);
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_CLEAR:
            redoStack.clear();
            toPush.setSecond(second);
            toPush.setThird(first);
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_FIND_TASK:
            break;

        case CommandTypeUtil.TYPE_HELP:
            break;

        case CommandTypeUtil.TYPE_HISTORY:
            break;

        case CommandTypeUtil.TYPE_LIST_TASK:
            break;

        case CommandTypeUtil.TYPE_SELECT_TASK:
            break;

        case CommandTypeUtil.TYPE_EXIT:
            break;

        default:
            break;
        }
    }
}
