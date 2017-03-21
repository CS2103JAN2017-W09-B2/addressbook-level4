package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.typed.logic.commands.ClearCommand;
import seedu.typed.logic.commands.DeleteCommand;
import seedu.typed.logic.commands.EditCommand;
import seedu.typed.logic.commands.UndoCommand;
import seedu.typed.testutil.TaskBuilder;
import seedu.typed.testutil.TestTask;
import seedu.typed.testutil.TestUtil;


public class UndoCommandTest extends TaskManagerGuiTest {


    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void undo_addCommand_success() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        assertUndoSuccess();
    }

    @Test
    public void undo_deleteCommand_success() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        assertDeleteSuccess(1, currentList);
        assertUndoSuccess();
    }

    @Test
    public void undo_editCommand_success() throws Exception {
        String detailsToEdit = "Meet Bobby d/19/03/2017 t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Meet Bobby").withDate("19/03/2017")
                .withTags("husband").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
        assertUndoSuccess();
    }

    @Test
    public void undo_clearCommand_success() throws Exception {
        assertClearSuccess();
        assertUndoSuccess();
    }

    @Test
    public void undo_noPreviousValidCommand_failure() {
        assertUndoFailure();
    }


    private void assertUndoSuccess() {
        commandBox.runCommand("undo");
        //assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

    private void assertUndoFailure() {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().getValue());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as
                                                                        // array
                                                                        // uses
                                                                        // zero
                                                                        // indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        // confirm the list now contains all previous tasks except the deleted
        // task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        // confirm the result message is correct
        assertResultMessage(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex, String detailsToEdit,
            TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().getValue());
        assertMatching(editedTask, editedCard);

        TestTask[] expectedEditedTasksList = td.getTypicalTasks();
        // confirm the list now contains all previous tasks plus the task with
        // updated details
        expectedEditedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedEditedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    private void assertClearSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }

}
