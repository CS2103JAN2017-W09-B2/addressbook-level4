package seedu.typed.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.typed.commons.core.ComponentManager;
import seedu.typed.commons.core.LogsCenter;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.commons.util.StringUtil;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyTask> completedTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     * @throws IllegalValueException
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) throws IllegalValueException {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        completedTasks = new FilteredList<>(this.taskManager.getCompletedTasks());
    }

    public ModelManager() throws IllegalValueException {
        this(new TaskManager(), new UserPrefs());
    }

    //@@author A0143853A
    @Override
    public int getIndexOfTask(Task task) throws TaskNotFoundException {
        return taskManager.getIndexOf(task);
    }

    //@@author A0143853A
    @Override
    public Task getTaskAt(int index) {
        return taskManager.getTaskAt(index);
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(this.taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    //@@author A0143853A
    @Override
    public synchronized void addTask(int index, Task task) throws DuplicateTaskException {
        taskManager.addTask(index, task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void completeTask(Task task) throws DuplicateTaskException, TaskNotFoundException {
        taskManager.completeTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTaskForUndoRedo(int index, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException {
        assert editedTask != null;

        taskManager.updateTask(index, editedTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    // Completed Task List accessors

    @Override
    public void updateCompletedTasksToShowAll() {
        completedTasks.setPredicate(null);
    }


    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil
                            .isFuzzyKeywordSearchIgnoreCase(task.getName().getValue(), keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0141094M
    @Override
    public void updateFilteredListToShowDeadline() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowDuration() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowDone() {
        completedTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowUndone() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowUntimed() {
        filteredTasks.setPredicate(null);
    }

}
