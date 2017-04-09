//@@author A0139392X
package seedu.typed.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.exceptions.CommandException;

/*
 * If only given filename, save the file to the same directory.
 * If given a path, save the file to that path.
 */
public class ExportCommand extends Command {
    public static final String EXPORT_COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Typed exported to %1$s";

    public static final String MESSAGE_SAVE_ERROR = "Unable to export to the location.";

    public static final String MESSAGE_FILENAME_INVALID = "Filename invalid";

    public static final String MESSAGE_USAGE = EXPORT_COMMAND_WORD
            + ": Exports the task manager to the location specified, or as a new name.\n"
            + "Example: " + EXPORT_COMMAND_WORD + " C:/Users/(username)/Desktop/typed.xml";

    private final String fileName;
    private final int type;

    public ExportCommand(int type, String fileName) {
        this.fileName = fileName;
        this.type = type;
    }

    /*
     * Executes the save command.
     *
     * If the newly specified destination is valid, save to the location.
     */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        File toCopyFrom = new File(config.getTaskManagerFilePath());

        switch (this.type) {
        case 1: // if the input is a path
            try {
                String onlyName = this.fileName.substring(this.fileName.lastIndexOf("/") + 1, this.fileName.length());
                if (FileUtil.isValidName(onlyName)) {
                    File fileToCreate = new File(this.fileName);

                    // Forms the directories if the directories are missing
                    fileToCreate.getParentFile().mkdirs();

                    FileUtil.writingFile(toCopyFrom, fileToCreate);

                    return new CommandResult(String.format(MESSAGE_SUCCESS, this.fileName));
                } else {
                    throw new CommandException(MESSAGE_FILENAME_INVALID);
                }
            } catch (IOException e) {
                throw new CommandException(MESSAGE_SAVE_ERROR);
            }
        case 2: // if input is a valid fileName
            try {
                String currentFileDirectory = FileUtil.getFullDirectoryPath();

                File fileToCreate = new File(currentFileDirectory + "/" + this.fileName);

                FileUtil.transferToFile(toCopyFrom, fileToCreate);

                return new CommandResult(String.format(MESSAGE_SUCCESS, currentFileDirectory + "/" + this.fileName));
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_SAVE_ERROR);
            }
        default:
            throw new CommandException(MESSAGE_FILENAME_INVALID);
        }
    }
}
//@@author
