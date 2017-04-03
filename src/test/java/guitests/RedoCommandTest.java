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
        String detailsToEdit = "Meet Bobby by 03/19/2017 #husband";
        int taskManagerIndex = 1;
        DateTime testDate = DateTime.getDateTime(2017, Month.MARCH, 19, 0, 0);
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

        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");

        assertMultipleRedosSuccess(3, expectedList);
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

    private void assertMultipleRedosSuccess(int n, TestTask[] expectedList)
            throws IllegalArgumentException, IllegalValueException {
        assert n > 0;

        for (int count = 0; count < n; count++) {
            commandBox.runCommand("redo");
            assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        }

        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertRedoFailure(TestTask[] tasksList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(tasksList));
        assertResultMessage(RedoCommand.MESSAGE_NO_COMMAND_TO_REDO);
    }
}

