package seedu.typed.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.typed.commons.core.ComponentManager;
import seedu.typed.commons.core.LogsCenter;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.commons.events.storage.DataSavingExceptionEvent;
import seedu.typed.commons.exceptions.DataConversionException;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Typed methods ==============================

    @Override
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public void setTaskManagerFilePath(String filepath) {
        taskManagerStorage.setTaskManagerFilePath(filepath);
    }

    @Override
    public void updateTaskManagerFilePath(String filepath, ReadOnlyTaskManager typed) throws IOException {
        taskManagerStorage.setTaskManagerFilePath(filepath);
        taskManagerStorage.saveTaskManager(typed);
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(taskManager, filePath);
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
