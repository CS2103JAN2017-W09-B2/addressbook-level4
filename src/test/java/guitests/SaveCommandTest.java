//@@author A0139392X
package guitests;

import org.junit.Test;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;

public class SaveCommandTest extends TaskManagerGuiTest {
    @Test
    public void save() throws IllegalArgumentException, IllegalValueException {
        String saveToDesktop = System.getProperty("user.home") + "/Desktop/saveTest";
        // invalid command
        commandBox.runCommand("save");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_SAVE_USAGE));

        // save to Desktop
        commandBox.runCommand("save " + saveToDesktop + ".xml");
        assertResultMessage(String.format(Messages.MESSAGE_SAVE_SUCCESS,
                saveToDesktop + ".xml"));

        // save wrong extension
        commandBox.runCommand("save " + saveToDesktop + ".jjj");
        assertResultMessage(String.format(Messages.MESSAGE_SAVE_SUCCESS,
                saveToDesktop + ".xml"));
    }
}
//@@author
