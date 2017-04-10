package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.UndoCommand;
import seedu.typed.testutil.TestTask;

//@@author A0143853A
public class UndoCommandTest extends TaskManagerGuiTest {


    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void undo_addCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess();
    }

    @Test
    public void undo_deleteCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("delete 1");
        assertUndoSuccess();
    }

    @Test
    public void undo_editCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        String detailsToEdit = "Meet Bobby by 03/19/2017 #husband";
        commandBox.runCommand("edit 1 " + detailsToEdit);
        assertUndoSuccess();
    }

    @Test
    public void undo_clearCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertUndoSuccess();
    }

    @Test
    public void undo_multipleCommands_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 2");
        commandBox.runCommand("clear");
        assertMultipleUndosSuccess(3);
    }

    @Test
    public void undo_allCommands_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 2");
        commandBox.runCommand("clear");
        assertUndoAllSuccess();
    }

    @Test
    public void undo_multipleCommandsPartially_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 2");

        assertUndoPartialSuccess(3);
    }


    @Test
    public void undo_noPreviousValidCommand_failure()
            throws IllegalArgumentException, IllegalValueException {
        assertUndoFailure();
    }


    private void assertUndoSuccess()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

    private void assertMultipleUndosSuccess(int n)
            throws IllegalArgumentException, IllegalValueException {
        assert n > 0;

        commandBox.runCommand("undo " + n);

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_MULTIPLE_SUCCESS, n));
    }

    private void assertUndoAllSuccess()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo all");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_ALL_SUCCESS);
    }

    private void assertUndoPartialSuccess(int n)
        throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo " + n);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_PARTIAL_SUCCESS, n - 1));
    }

    private void assertUndoFailure()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }
}
