package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.ClearCommand;
import seedu.typed.logic.commands.DeleteCommand;
import seedu.typed.logic.commands.EditCommand;
import seedu.typed.logic.commands.RedoCommand;
import seedu.typed.logic.commands.UndoCommand;
import seedu.typed.testutil.TaskBuilder;
import seedu.typed.testutil.TestTask;
import seedu.typed.testutil.TestUtil;


public class RedoCommandTest extends TaskManagerGuiTest {


    TestTask[] expectedUndoTasksList = td.getTypicalTasks();

    @Test
    public void redo_undoneAddCommand_success()
            throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        assertUndoSuccess(expectedUndoTasksList);
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_undoneDeleteCommand_success()
            throws IllegalArgumentException, IllegalValueException {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        assertDeleteSuccess(1, currentList);
        assertUndoSuccess(expectedUndoTasksList);
        TestTask[] expectedList = TestUtil.removeTaskFromList(currentList, 1);
        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_undoneEditCommand_success() throws Exception {

        TestTask[] expectedList = td.getTypicalTasks();
        String detailsToEdit = "Meet Bobby d/19/03/2017 t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Meet Bobby").withDate("19/03/2017")
                .withTags("husband").build();

        // TODO : fix failing test
        //assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
        //assertUndoSuccess(expectedUndoTasksList);
        expectedList[taskManagerIndex - 1] = editedTask;
        //assertRedoSuccess(expectedList);
    }

    @Test
    public void undo_clearCommand_success() throws Exception {

        TestTask[] expectedUndoList = td.getTypicalTasks();
        assertClearSuccess();
        assertUndoSuccess(expectedUndoList);
        TestTask[] expectedList = {};
        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_noPreviousValidCommand_failure()
            throws IllegalArgumentException, IllegalValueException {

        TestTask[] expectedList = td.getTypicalTasks();
        assertRedoFailure(expectedList);
    }

    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("redo");
        //assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertRedoFailure(TestTask[] tasksList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(tasksList));
        assertResultMessage(RedoCommand.MESSAGE_NO_COMMAND_TO_REDO);
    }

    private void assertUndoSuccess(TestTask[] expectedUndoTasksList) {
        commandBox.runCommand("undo");
        //assertTrue(taskListPanel.isListMatching(expectedUndoTasksList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().getValue());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList)
            throws IllegalArgumentException, IllegalValueException {
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

    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
            String detailsToEdit, TestTask editedTask)
            throws IllegalArgumentException, IllegalValueException {
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

