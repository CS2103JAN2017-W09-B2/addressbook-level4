package seedu.typed.commons.core;

import seedu.typed.logic.commands.ExportCommand;
import seedu.typed.logic.commands.ImportCommand;
import seedu.typed.logic.commands.SaveCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_EMPTY_COMMAND = "You entered nothing! Type something to get started...";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid!";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_EXPORT_SUCCESS = "Typed exported to %1$s";
    public static final String MESSAGE_EXPORT_SAVE_ERROR = "Unable to export to the location.";
    public static final String MESSAGE_EXPORT_DUPLICATE = "There already exists a file with the same filename.\n"
            + "Please use a different filename.";
    public static final String MESSAGE_EXPORT_USAGE = ExportCommand.COMMAND_WORD_EXPORT
            + ": Exports the task manager to the location specified, or as a new name.\n"
            + "Example: " + ExportCommand.COMMAND_WORD_EXPORT + " C:/Users/(username)/Desktop/typed.xml";
    public static final String MESSAGE_IMPORT_USAGE = ImportCommand.COMMAND_WORD_IMPORT
            + ": Imports the given task manager.\n"
            + "Example: " + ImportCommand.COMMAND_WORD_IMPORT
            + " C:/Users/(username)/Desktop/typed.xml";
    public static final String MESSAGE_IMPORT_SUCCESS = "Task manager imported! \n"
            + "Source: %1$s";
    public static final String MESSAGE_SAVE_USAGE = SaveCommand.COMMAND_WORD_SAVE
            + ": Changes the storage location"
            + " and saves at the new location.\n"
            + "Example: " + SaveCommand.COMMAND_WORD_SAVE
            + " C:/Users/(username)/Desktop/typed.xml";
    public static final String MESSAGE_SAVE_SUCCESS = "Storage location changed! \n"
            + "New location: %1$s";
    public static final String MESSAGE_SAVE_ERROR = "Unable to save to new location.";


}
