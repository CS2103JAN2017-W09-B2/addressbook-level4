package seedu.typed.logic.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.exceptions.CommandException;

//@@author A0139392X
/*
 * If only given filename, save the file to the same directory.
 * If given a path, save the file to that path.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_SUCCESS = "File saved to %1$s";

    public static final String MESSAGE_SAVE_ERROR = "Unable to save to the location.";

    public static final String MESSAGE_FILENAME_INVALID = "Filename invalid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the task manager to the location specified, or as a new name.\n"
            + "Parameters: fileName | NEW_STORAGE_LOCATION\n"
            + "Example: " + COMMAND_WORD + "Desktop/typed.xml";

    private final String fileName;

    public SaveCommand(String fileName) {
        this.fileName = fileName;
    }

    /*
     * Executes the save command.
     *
     * If the newly specified destination is valid, save to the location.
     */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        // String tempLocation = currentFileDirectory + "/" + this.fileName + ".xml";
        File file = new File(this.fileName);
        File toCopyFrom = new File(config.getTaskManagerFilePath());

        if (file.isDirectory()) {
            try {
                // if file does not exists, create file
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileInputStream is = new FileInputStream(toCopyFrom);
                FileOutputStream os = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                is.close();
                os.close();

                return new CommandResult(MESSAGE_SUCCESS);

            } catch (IOException e) {
                throw new CommandException(MESSAGE_SAVE_ERROR);
            }
        } else { // input name is not directory
            if (FileUtil.isValidName(this.fileName)) {
                try {
                    String currentFileDirectory = FileUtil.getFullDirectoryPath();
                    System.out.println("Directory: " + currentFileDirectory);

                    File fileToCreate = new File(currentFileDirectory + "/" + this.fileName);

                    String contents = FileUtil.readFromFile(toCopyFrom);

                    FileUtil.writeToFile(fileToCreate, contents);

                    return new CommandResult(String.format(MESSAGE_SUCCESS, currentFileDirectory + "/" + this.fileName));
                } catch (IOException ioe) {
                    throw new CommandException(MESSAGE_SAVE_ERROR);
                }
            } else {
                throw new CommandException(MESSAGE_FILENAME_INVALID);
            }
        }
    }
}
