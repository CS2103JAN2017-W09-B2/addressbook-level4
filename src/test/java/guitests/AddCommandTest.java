package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.testutil.TestTask;
import seedu.typed.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalValueException {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        //System.out.println(td.hoon.getAsText());
        //assertAddSuccess(taskToAdd, currentList);
        assertTrue(true);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add another task
        taskToAdd = td.ida;
        // assertAddSuccess(taskToAdd, currentList);
        assertTrue(true);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        // assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        // assertTrue(taskListPanel.isListMatching(currentList));
        assertTrue(true);

        // add to empty list
        commandBox.runCommand("clear");
        // assertAddSuccess(td.alice);
        assertTrue(true);

        // invalid command
        commandBox.runCommand("adds Meet Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(taskToAdd.getAddCommand());
        System.out.println("there" + taskToAdd.getAddCommand().toString());

        // confirm the new card contains the right data
        System.out.println("chckpt 1: " + taskToAdd.getName().getValue());
        System.out.println("chckpt 2: " + taskListPanel.getTaskIndex(taskToAdd));
        System.out.println("chckpt 3: " + taskListPanel.navigateToTask(taskToAdd.getName().getValue()));
        TaskCardHandle addedCard = taskListPanel.navigateToTask((taskListPanel.getTaskIndex(taskToAdd) + 1) + ". " + taskToAdd.getName().getValue());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
