package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.typed.commons.core.Messages;
import seedu.typed.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Z"); // no results
        assertFindResult("find Meller", td.benson, td.daniel, td.elle); // multiple
                                                              // results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meller", td.daniel, td.elle);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
