//@@author A0139392X
package seedu.typed.logic.commands;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import seedu.typed.commons.exceptions.DataConversionException;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.storage.XmlTaskManagerStorage;

/*
 * Imports the task manager and updates the storage.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports the given task manager.\n"
            + "Example: " + COMMAND_WORD
            + " Desktop/typed.xml";

    public static final String MESSAGE_SUCCESS = "Task manager imported! \n" + "Source: %1$s";
    public static final String MESSAGE_ERROR = "Did you specify the location correctly?\n + "
            + "Typed is unable to import from the path given";

    private final String location;

    public ImportCommand(String location) {
        this.location = location;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(this.location);

            ReadOnlyTaskManager toImport = xmlTaskManagerStorage.readTaskManager(this.location).get();

            model.resetData(toImport);

            return new CommandResult(String.format(MESSAGE_SUCCESS, this.location));

        } catch (DataConversionException de) {
            return new CommandResult(MESSAGE_ERROR);
        } catch (FileNotFoundException fnf) {
            return new CommandResult(MESSAGE_ERROR);
        } catch (IllegalValueException ive) {
            return new CommandResult(MESSAGE_ERROR);
        } catch (NoSuchElementException nse) {
            return new CommandResult(MESSAGE_ERROR);
        }
    }

}
//@@author
