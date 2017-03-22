package guitests;

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

    /*private void assertCompleteSuccess(int startIndex, int endIndex, TestTask completedTask) {
        commandBox.runCommand("finish ");

        // Checks if the new card contains completed task
        TaskCardHandle editedCard = taskListPanel.navigateToTask(completedTask.getName().getValue());
        assertMatching(completedTask, editedCard);

        // Checks the list contains all previous tasks
        // assertTrue(taskListPanel.isListMatching(expectedTasksList));
        // assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS));
    }*/
}
