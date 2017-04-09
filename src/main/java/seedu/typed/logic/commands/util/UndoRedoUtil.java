package seedu.typed.logic.commands.util;

import java.util.Optional;
import java.util.Stack;

import seedu.typed.commons.util.Triple;
import seedu.typed.logic.commands.AddCommand;
import seedu.typed.logic.commands.ClearCommand;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.logic.commands.DeleteCommand;
import seedu.typed.logic.commands.EditCommand;
import seedu.typed.logic.commands.ExitCommand;
import seedu.typed.logic.commands.FindCommand;
import seedu.typed.logic.commands.HelpCommand;
import seedu.typed.logic.commands.ListCommand;
import seedu.typed.logic.commands.RedoCommand;
import seedu.typed.logic.commands.UndoCommand;

//@@author A0143853A
/**
 * Helps to manage Undo and Redo stacks in the Session class.
 *
 */
public class UndoRedoUtil {

    public static Optional<Triple<String, Integer, Object>> pop(Stack<Triple<String, Integer, Object>> stack) {
        if (!stack.empty()) {
            return Optional.of(stack.pop());
        } else {
            return Optional.empty();
        }
    }

    public static void clear(Stack<Triple<String, Integer, Object>> stack) {
        stack.clear();
    }

    public static boolean isEmpty(Stack<Triple<String, Integer, Object>> stack) {
        return stack.empty();
    }

    @SuppressWarnings("unchecked")
    public static void update(Stack<Triple<String, Integer, Object>> undoStack,
                              Stack<Triple<String, Integer, Object>> redoStack,
                              String command, Integer index, Object toChange) {

        Triple<String, Integer, Object> toPush = new Triple<String, Integer, Object>(command,
                                                                                     index,
                                                                                     toChange);
        switch(command) {

        case UndoCommand.COMMAND_WORD_UNDO:
            toPush = (Triple<String, Integer, Object>) toChange;
            redoStack.push(toPush);
            break;

        case RedoCommand.COMMAND_WORD_REDO:
            toPush = (Triple<String, Integer, Object>) toChange;
            undoStack.push(toPush);
            break;

        case AddCommand.COMMAND_WORD_ADD:
            redoStack.clear();
            toPush.setFirst(DeleteCommand.COMMAND_WORD_DELETE);
            undoStack.push(toPush);
            break;

        case DeleteCommand.COMMAND_WORD_DELETE:
            redoStack.clear();
            toPush.setFirst(AddCommand.COMMAND_WORD_ADD);
            undoStack.push(toPush);
            break;

        case EditCommand.COMMAND_WORD_EDIT:
            redoStack.clear();
            undoStack.push(toPush);
            break;

        case ClearCommand.COMMAND_WORD_CLEAR:
            redoStack.clear();
            toPush.setFirst(ClearCommand.COMMAND_WORD_UNCLEAR);
            undoStack.push(toPush);
            break;

        case CompleteCommand.COMMAND_WORD_COMPLETE:
            redoStack.clear();
            toPush.setFirst(CompleteCommand.COMMAND_WORD_UNCOMPLETE);
            undoStack.push(toPush);
            break;

        case FindCommand.COMMAND_WORD_FIND:
            break;

        case HelpCommand.COMMAND_WORD_HELP:
            break;

        case ListCommand.COMMAND_WORD_LIST:
            break;

        case ExitCommand.COMMAND_WORD_EXIT:
            break;

        default:
            break;
        }
    }

}
