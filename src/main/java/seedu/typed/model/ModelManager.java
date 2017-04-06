package seedu.typed.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.typed.commons.core.ComponentManager;
import seedu.typed.commons.core.LogsCenter;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.commons.util.Pair;
import seedu.typed.commons.util.StringUtil;
import seedu.typed.logic.commands.util.Type;
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

    //private Expression currentExpression;
    private Expression defaultExpression = new Negation(new CompletedQualifer());
    //private Expression doneExpression = new PredicateExpression(new CompletedQualifer());


    // =========== ModelManager Constructors =======================
    // =============================================================
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
        updateFilteredListToShowDefault();
        //this.currentExpression = defaultExpression;
    }

    public ModelManager() throws IllegalValueException {
        this(new TaskManager(), new UserPrefs());
        updateFilteredListToShowDefault();
    }


    // =========== TaskManager Getters =============================
    // =============================================================
    //Test
    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    public int getNumberCompletedTasks() {
        return taskManager.getNumberCompletedTasks();
    }

    @Override
    public int getNumberUncompletedTasks() {
        return taskManager.getNumberUncompletedTasks();
    }

    @Override
    public int getTotalTasks() {
        return getNumberCompletedTasks() + getNumberUncompletedTasks();
    }

    @Override
    public int getNumberEvents() {
        return taskManager.getNumberEvents();
    }

    @Override
    public int getNumberDeadlines() {
        return taskManager.getNumberDeadlines();
    }
    @Override
    public int getNumberFloatingTasks() {
        return taskManager.getNumberFloatingTasks();
    }

    @Override
    public int getNumberUncompletedEvents() {
        return taskManager.getNumberUncompletedEvents();
    }

    @Override
    public int getNumberUncompletedDeadlines() {
        return taskManager.getNumberUncompletedDeadlines();
    }

    @Override
    public int getNumberUncompletedFloatingTasks() {
        return taskManager.getNumberUncompletedFloatingTasks();
    }
    //@@author A0143853A

    @Override
    public int getNumberOverdue() {
        return taskManager.getNumberOverdue();
    }

    @Override
    public int getIndexOfTask(Task task) throws TaskNotFoundException {
        return taskManager.getIndexOf(task);
    }
    //@@author

    //@@author A0143853A
    @Override
    public Task getTaskAt(int index) {
        return taskManager.getTaskAt(index);
    }
    //@@author

    // =========== ModelManager Add Tasks ==========================
    // =============================================================

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    //@@author A0143853A
    @Override
    public synchronized void addTask(int index, Task task) throws DuplicateTaskException {
        taskManager.addTask(index, task);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTasksForUndo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException {
        for (int curr = 0; curr < list.size(); curr++) {
            Pair<Integer, Task> indexAndTask = list.get(curr);
            int index = indexAndTask.getFirst();
            Task task = indexAndTask.getSecond();
            taskManager.addTask(index, task);
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    // =========== ModelManager Delete Tasks =======================
    // =============================================================

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    //@@author A0143853A
    @Override
    public synchronized void deleteTaskAt(int index) {
        taskManager.removeTaskAt(index);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    //@@author A0143853A
    @Override
    public synchronized void deleteTasksAndStoreTasksAndIndices(int startIndex, int endIndex,
            ArrayList<Pair<Integer, Task>> list) throws TaskNotFoundException {
        int num = endIndex - startIndex + 1;
        for (int i = 0; i < num; i++) {
            int taskManagerIndex = filteredTasks.getSourceIndex(startIndex);
            Task taskToDelete = taskManager.getTaskAt(taskManagerIndex);
            Pair<Integer, Task> toAdd = new Pair<Integer, Task>(taskManagerIndex, taskToDelete);
            list.add(0, toAdd);
            taskManager.removeTaskAt(taskManagerIndex);
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void deleteTasksForRedo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException {
        for (int curr = 0; curr < list.size(); curr++) {
            Pair<Integer, Task> indexAndTask = list.get(curr);
            int index = indexAndTask.getFirst();
            taskManager.removeTaskAt(index);
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    // =========== ModelManager Update Tasks =======================
    // =============================================================

    //@@author A0139379M
    @Override
    public synchronized void completeTaskAt(int filteredTaskListIndex)
            throws DuplicateTaskException {
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.completeTaskAt(taskManagerIndex);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void completeTasks(int startIndex, int endIndex)
            throws DuplicateTaskException {
        int num = endIndex - startIndex + 1;
        for (int i = 0; i < num; i++) {
            int taskManagerIndex = filteredTasks.getSourceIndex(startIndex);
            taskManager.completeTaskAt(taskManagerIndex);
            updateFilteredListToShowDefault();
            indicateTaskManagerChanged();
        }
    }
    //@@author

    //@@author A0143853A
    @Override
    public synchronized void completeTasksAndStoreIndices(int startIndex, int endIndex,
            ArrayList<Integer> list) throws DuplicateTaskException {
        for (int curr = startIndex; curr <= endIndex; curr++) {
            int taskManagerIndex = filteredTasks.getSourceIndex(curr);
            addTaskIndexToListIfUncompleted(taskManagerIndex, list);
            taskManager.completeTaskAt(taskManagerIndex);
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    private void addTaskIndexToListIfUncompleted(int taskIndex, ArrayList<Integer> list) {
        if (!taskManager.getTaskAt(taskIndex).getIsCompleted()) {
            list.add(taskIndex);
        }
    }

    @Override
    public synchronized void uncompleteTaskAtForUndo(int taskManagerIndex)
            throws DuplicateTaskException {
        taskManager.uncompleteTaskAt(taskManagerIndex);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void uncompleteTasksAtForUndo(ArrayList<Integer> list)
            throws DuplicateTaskException {
        for (int curr = 0; curr < list.size(); curr++) {
            taskManager.uncompleteTaskAt(list.get(curr));
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void completeTasksAtForRedo(ArrayList<Integer> list)
            throws DuplicateTaskException {
        for (int curr = 0; curr < list.size(); curr++) {
            taskManager.completeTaskAt(list.get(curr));
        }
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void completeTaskAtForRedo(int taskManagerIndex)
            throws DuplicateTaskException {
        taskManager.completeTaskAt(taskManagerIndex);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    //@@author A0143853A
    @Override
    public void updateTaskForUndoRedo(int index, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException {
        assert editedTask != null;

        taskManager.updateTask(index, editedTask);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    // =========== ModelManager Util Methods =======================
    // =============================================================
    @Override
    public void resetData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        // updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }

    //@@author A0143853A
    @Override
    public void copyData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.copyData(newData);
        // updateFilteredListToShowDefault();
        indicateTaskManagerChanged();
    }
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(this.taskManager));
    }

    // =========== ModelManager Display ============================
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
    public void updateFilteredListToShowDefault() {
        updateFilteredTaskList(defaultExpression);
    }

    //@@author A0141094M
    @Override
    public void updateFilteredTaskList(Set<String> keywords, Set<String> tagKeywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)),
                new PredicateExpression(new TagQualifier(tagKeywords)));
    }

    private void updateFilteredTaskList(Expression expression, Expression tagExpression) {
        filteredTasks.setPredicate(p -> (expression.satisfies(p) || tagExpression.satisfies(p)));
    }
    //@@author

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(String type) {
        switch (type) {
        case "deadline":
            updateFilteredListToShowDeadline();
            break;
        case "duration":
            updateFilteredListToShowDuration();
            break;
        case "done":
            updateFilteredListToShowDone();
            break;
        case "undone":
            updateFilteredListToShowUndone();
            break;
        case "untimed":
            updateFilteredListToShowUntimed();
            break;
        case "all":
            updateFilteredListToShowAll();
            break;
        default:
            updateFilteredListToShowDefault();
        }
    }

    @Override
    public void updateFilteredTaskList(Type type) {
        switch (type) {
        // NOT DONE
        case DEADLINE:
            updateFilteredListToShowDeadline();
            break;
            // NOT DONE
        case DURATION:
            updateFilteredListToShowDuration();
            break;
        case DONE:
            updateFilteredListToShowDone();
            break;
        case UNDONE:
            updateFilteredListToShowUndone();
            break;
            // NOT DONE
        case UNTIMED:
            updateFilteredListToShowUntimed();
            break;
        case ALL:
            updateFilteredListToShowAll();
            break;
        default:
            updateFilteredListToShowDefault();
        }

    }

    //@@author A0141094M

    @Override
    public void updateFilteredListToShowDeadline() {
        // todo
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToShowDuration() {
        // todo
        this.taskManager.printData();
    }

    @Override
    public void updateFilteredListToShowDone() {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifer()));
    }

    @Override
    public void updateFilteredListToShowUndone() {
        updateFilteredTaskList(new Negation(new CompletedQualifer()));
    }

    @Override
    public void updateFilteredListToShowUntimed() {
        //todo
        filteredTasks.setPredicate(null);
    }
    //@@author

    // =========== Inner classes/interfaces used for filtering =====
    // =============================================================

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
            System.out.println(task.getIsCompleted());
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }
    //@@author A01393793M
    private class Negation implements Expression {
        private final Qualifier qualifier;

        Negation(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return !qualifier.run(task);
        }
    }
    //@@author
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
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream().filter(keyword -> StringUtil
                    .isFuzzyKeywordSearchIgnoreCase(task.getTags(), keyword)).findAny().isPresent();
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }
    //@@author

    //@@author A0139379M
    /**
     * Returns true for tasks that are completed
     * @author YIM CHIA HUI
     *
     */
    private class CompletedQualifer implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getIsCompleted();
        }
    }
    //@@author

}
