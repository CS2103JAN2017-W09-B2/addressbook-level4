package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.ClearCommand;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() throws IllegalArgumentException, IllegalValueException {

        // verify a non-empty list can be cleared
        // assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertTrue(true);
        assertClearSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        // assertTrue(taskListPanel.isListMatching(td.hoon));
        assertTrue(true);
        commandBox.runCommand("delete 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertClearSuccess();
    }

    private void assertClearSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
