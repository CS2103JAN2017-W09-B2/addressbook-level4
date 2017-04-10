package guitests;

import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.RedoCommand;
import seedu.typed.model.task.DateTime;
import seedu.typed.testutil.TaskBuilder;
import seedu.typed.testutil.TestTask;
import seedu.typed.testutil.TestUtil;

//@@author A0143853A
public class RedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void redo_undoneAddCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);

        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("undo");

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_undoneDeleteCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] expectedList = TestUtil.removeTaskFromList(currentList, 1);

        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_undoneEditCommand_success()
            throws IllegalValueException {
        TestTask[] expectedList = td.getTypicalTasks();
        String detailsToEdit = "Meet Bobby by 03/19/2017 1pm #husband";
        int taskManagerIndex = 1;
        DateTime testDate = DateTime.getDateTime(2017, Month.MARCH, 19, 13, 0);
        TestTask editedTask = new TaskBuilder().withName("Meet Bobby").withDeadline(testDate)
                .withTags("husband").build();
        expectedList[taskManagerIndex - 1] = editedTask;

        commandBox.runCommand("edit " + taskManagerIndex + " " + detailsToEdit);
        commandBox.runCommand("undo");

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_undoneClearCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] expectedList = {};

        commandBox.runCommand("clear");
        commandBox.runCommand("undo");

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_multipleUndoneCommands_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] expectedList = {};
        TestTask taskToAdd = td.hoon;

        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 1");
        commandBox.runCommand("clear");

        commandBox.runCommand("undo 3");

        assertMultipleRedosSuccess(3, expectedList);
    }

    @Test
    public void redo_multipleUndoneCommandsPartially_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] expectedList = {};
        TestTask taskToAdd = td.hoon;

        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("clear");

        commandBox.runCommand("undo all");

        assertRedoPartialSuccess(3, expectedList);
    }


    @Test
    public void redo_allUndoneCommands_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] expectedList = {};
        TestTask taskToAdd = td.hoon;

        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete 1");
        commandBox.runCommand("clear");

        commandBox.runCommand("undo all");

        assertRedoAllSuccess(expectedList);
    }

    @Test
    public void redo_noUndoneCommand_failure()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] expectedList = td.getTypicalTasks();

        assertRedoFailure(expectedList);
    }

    @Test
    public void redo_mutableCommand_failure()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);

        commandBox.runCommand(taskToAdd.getAddCommand());

        assertRedoFailure(expectedList);
    }

    private void assertRedoSuccess(TestTask[] expectedList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertRedoPartialSuccess(int n, TestTask[] expectedList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("redo " + n);
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(String.format(RedoCommand.MESSAGE_PARTIAL_SUCCESS, n - 1));
    }

    private void assertMultipleRedosSuccess(int n, TestTask[] expectedList)
            throws IllegalArgumentException, IllegalValueException {
        assert n > 0;

        commandBox.runCommand("redo " + n);
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(String.format(RedoCommand.MESSAGE_MULTIPLE_SUCCESS, n));
    }

    private void assertRedoAllSuccess(TestTask[] expectedList)
            throws IllegalArgumentException, IllegalValueException {

        commandBox.runCommand("redo all");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(RedoCommand.MESSAGE_ALL_SUCCESS);
    }

    private void assertRedoFailure(TestTask[] tasksList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(tasksList));
        assertResultMessage(RedoCommand.MESSAGE_NO_COMMAND_TO_REDO);
    }
}

