//@@author A0139392X
package seedu.typed.logic.commands;

import java.io.IOException;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.exceptions.CommandException;

/*
 * Changes storage location.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the storage location"
            + " and saves at the new location.\n"
            + "Example: " + COMMAND_WORD
            + " C:\\Users\\(username)\\Desktop\\typed.xml";

    public static final String MESSAGE_SUCCESS = "Storage location changed! \n" + "New location: %1$s";
    public static final String MESSAGE_ERROR = "Unable to save to new location.";

    private final String location;

    public SaveCommand(String location) {
        this.location = location;
    }

    /*
     * Executes the store command.
     *
     * Checks if the specified input location by the user is valid. If it's valid, store to the location. Further
     * changes to the task manager will be stored on that location. If the specified location is not valid, reset
     * the location to the previous location (ie. default location).
     */
    @Override
    public CommandResult execute() throws CommandException {
        String defaultLocation = config.getTaskManagerFilePath();
        String tempLocation = this.location;

        if (FileUtil.isValidLocation(tempLocation)) {
            try {
                config.setTaskManagerFilePath(tempLocation);
                storage.updateTaskManagerFilePath(tempLocation, model.getTaskManager());
                return new CommandResult(String.format(MESSAGE_SUCCESS, tempLocation));
            } catch (IOException e) {
                config.setTaskManagerFilePath(defaultLocation);
                storage.setTaskManagerFilePath(defaultLocation);
                return new CommandResult(MESSAGE_ERROR);
            }
        } else {
            return new CommandResult(MESSAGE_ERROR);
        }
    }

}
//@@author
