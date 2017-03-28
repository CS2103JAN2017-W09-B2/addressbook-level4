package seedu.typed.logic.commands.util;

import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.TripleUtil;

//@@author A0143853A
/**
 * Helps to manage Undo and Redo stacks in the Session class.
 *
 */
public class UndoRedoUtil {

    public static Optional<TripleUtil<String, Integer, Object>> pop(Stack<TripleUtil<String, Integer, Object>> stack) {
        if (!stack.empty()) {
            return Optional.of(stack.pop());
        } else {
            return Optional.empty();
        }
    }

    public static void clear(Stack<TripleUtil<String, Integer, Object>> stack) {
        stack.clear();
    }

    public static boolean isEmpty(Stack<TripleUtil<String, Integer, Object>> stack) {
        return stack.empty();
    }

    @SuppressWarnings("unchecked")
    public static void update(Stack<TripleUtil<String, Integer, Object>> undoStack,
                              Stack<TripleUtil<String, Integer, Object>> redoStack,
                              String command, Integer index, Object toChange) {

        TripleUtil<String, Integer, Object> toPush = new TripleUtil<String, Integer, Object>(command,
                                                                                             index,
                                                                                             toChange);
        switch(command) {

        case CommandTypeUtil.TYPE_UNDO:
            toPush = (TripleUtil<String, Integer, Object>) toChange;
            redoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_REDO:
            toPush = (TripleUtil<String, Integer, Object>) toChange;
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
            undoStack.push(toPush);
            break;

        case CommandTypeUtil.TYPE_CLEAR:
            redoStack.clear();
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

   // TODO write function to check if object is instanceof TripleUtil
}
