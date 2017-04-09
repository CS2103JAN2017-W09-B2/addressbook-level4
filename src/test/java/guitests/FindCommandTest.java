package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    //@@author A0141094M
    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Z"); // no results
        assertFindResult("find Meller", td.benson, td.daniel, td.elle); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meller", td.daniel, td.elle);
    }
    //@@author

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
