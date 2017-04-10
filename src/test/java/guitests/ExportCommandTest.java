//@@author A0139392X
package guitests;

import java.io.File;

import org.junit.Test;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;

public class ExportCommandTest extends TaskManagerGuiTest {
    private String userHome = System.getProperty("user.home");
    @Test
    public void export() throws IllegalArgumentException, IllegalValueException {
        // exporting to Desktop
        String toTestDesktop = userHome + "/Desktop/typed.xml";
        File f1 = new File(toTestDesktop);
        if (!f1.exists()) {
            commandBox.runCommand("export " + toTestDesktop);
            assertResultMessage(String.format(Messages.MESSAGE_EXPORT_SUCCESS,
                    toTestDesktop));
            assertFileCreated(f1);
        } else {
            assertDuplicate(toTestDesktop);
        }

        // wrong extension
        String wrongExtension = userHome + "/Desktop/wrongExtension";
        File f2 = new File(wrongExtension + ".xml");
        if (!f2.exists()) {
            assertExportSuccess(wrongExtension);
            assertFileCreated(f2);
        } else {
            assertDuplicate(wrongExtension + ".jjj");
        }

        // no extension
        String noExtension = userHome + "/Desktop/noExtension";
        File f3 = new File(noExtension + ".xml");
        if (!f3.exists()) {
            assertExportSuccess(noExtension);
            assertFileCreated(f3);
        } else {
            assertDuplicate(noExtension);
        }

        // invalid command format
        commandBox.runCommand("export ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_EXPORT_USAGE));
    }

    private void assertExportSuccess(String str) {
        commandBox.runCommand("export " + str);
        assertResultMessage(String.format(Messages.MESSAGE_EXPORT_SUCCESS,
                str + ".xml"));
    }

    private void assertDuplicate(String str) {
        commandBox.runCommand("export " + str);
        assertResultMessage(Messages.MESSAGE_EXPORT_DUPLICATE);
    }

    private void assertFileCreated(File f1) {
        assert (f1.exists());
    }
}
//@@author
