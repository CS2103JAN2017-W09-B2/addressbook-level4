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
        String detailsToEdit = "Meet Bobby by 19/03/2017 #husband";
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

        for (int count = 0; count < n; count++) {
            commandBox.runCommand("undo");
            assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        }

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    private void assertUndoFailure()
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }
}
