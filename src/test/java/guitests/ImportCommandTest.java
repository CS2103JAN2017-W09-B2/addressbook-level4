//@@author A0139392X
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.testutil.TestTask;

public class ImportCommandTest extends TaskManagerGuiTest {
    @Test
    public void importCommand() throws IllegalArgumentException, IllegalValueException {
        String toImport = System.getProperty("user.home") + "/Desktop/typed.xml";
        // export and clear
        commandBox.runCommand("export " + toImport);
        commandBox.runCommand("clear");

        // import and check
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("import " + toImport);
        assertResultMessage(String.format(Messages.MESSAGE_IMPORT_SUCCESS, toImport));
        assertTrue(taskListPanel.isListMatching(currentList));

        // invalid command format
        commandBox.runCommand("import ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_IMPORT_USAGE));
    }
}
//@@author
