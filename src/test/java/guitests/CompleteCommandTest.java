package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.CompleteCommand;
import seedu.typed.testutil.TestTask;

public class CompleteCommandTest extends TaskManagerGuiTest {
    // GUI not ready as parser not in place
    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    // Complete task has two outcomes
    // 1) Complete a task and it is marked as completed
    // 2) Complete a completed task and nothing changes
    // 3) Complete a nonexisting task returns exception
    // 4) Complete an invalid task number returns exception
    // 5) Complete a list of tasks and all are marked as completed
    // 6) Complete all tasks
    // 7) Complete a list of invalid tasks throw exception
    // 8) Complete a list of invalid tasks with invalid bounds throw exception
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void completeCommand_oneIndex_success()
            throws IllegalArgumentException, IllegalValueException {
        // finish one item
        int targetIndex = 1;
        expectedTasksList[0].setIsCompleted(true);
        assertCompleteSuccess(targetIndex, targetIndex);
    }

    @Test
    public void completeCommand_rangeOfIndex_success()
            throws IllegalArgumentException, IllegalValueException {
        int startIndex = 1;
        int endIndex = 3;
        for (int index = startIndex; index <= endIndex; index++) {
            expectedTasksList[index - 1].setIsCompleted(true);
        }
        assertCompleteSuccess(startIndex, endIndex);
    }

    @Test
    public void execute_invalidIndex_error() {
        int invalidIndex = 0;
        commandBox.runCommand("finish " + invalidIndex);
        assertResultMessage("Invalid command format! " + "\n" + String.format(CompleteCommand.MESSAGE_USAGE));
    }

    private void assertCompleteSuccess(int startIndex, int endIndex)
            throws IllegalArgumentException, IllegalValueException {
        StringBuilder command = new StringBuilder();
        String result = "";
        command.append("finish ").append(startIndex);
        if (startIndex != endIndex) {
            command.append(" to " + endIndex);
            result = result + (endIndex - startIndex + 1);
        } else {
            result = result + expectedTasksList[startIndex - 1].getName();
        }
        commandBox.runCommand(command.toString());
        TestTask[] filteredList = filterOutCompleted(expectedTasksList);

        // Checks the list contains all tasks without completed tasks
        assertTrue(taskListPanel.isListMatching(filteredList));
        if (startIndex == endIndex) {
            assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_TASK_SUCCESS, result));
        } else {
            int numCompleted = Integer.parseInt(result);
            assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_TASKS_SUCCESS, numCompleted));
        }
    }

    /**
     * Gets the list of uncompleted tasks
     *
     * @param tasks
     * @return TestTask[] of uncompleted tasks
     */
    private TestTask[] filterOutCompleted(TestTask[] tasks) {
        int count = 0;
        ArrayList<TestTask> filteredList = new ArrayList<>();
        for (int i = 0; i < tasks.length; i++) {
            if (!tasks[i].getIsCompleted()) {
                filteredList.add(tasks[i]);
                count++;
            }
        }
        TestTask[] filteredArray = new TestTask[count];
        for (int i = 0; i < count; i++) {
            filteredArray[i] = filteredList.get(i);
        }
        return filteredArray;
    }










}
