package seedu.typed.model;

import java.util.ArrayList;
import java.util.Set;

import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.Pair;
import seedu.typed.logic.commands.util.Type;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {


    // =========== ModelManager Constructors =======================
    // =============================================================
    // =========== TaskManager Getters =============================
    // =============================================================
    /** Returns the TaskManager. */
    ReadOnlyTaskManager getTaskManager();

    int getNumberCompletedTasks();

    int getNumberUncompletedTasks();

    int getTotalTasks();

    int getNumberEvents();

    int getNumberDeadlines();

    int getNumberFloatingTasks();

    //@@author A0143853A
    Task getTaskAt(int index);
    //@@author

    //@@author A0143853A
    int getIndexOfTask(Task task) throws TaskNotFoundException;
    //@@author

    // =========== ModelManager Add Tasks ==========================
    // =============================================================
    /** Adds the given task. */
    void addTask(Task task) throws DuplicateTaskException;

    //@@author A0143853A
    /** Adds the given task at the specified index. */
    void addTask(int index, Task task) throws DuplicateTaskException;
    //@@author
    // =========== ModelManager Delete Tasks =======================
    // =============================================================
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;
    // =========== ModelManager Update Tasks =======================
    // =============================================================
    /**
     * Updates the task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException;

    //@@author A0143853A
    /** TODO Change comments
     * Updates the task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             task list.
     */
    void updateTaskForUndoRedo(int index, ReadOnlyTask editedTask)
            throws DuplicateTaskException, IllegalValueException;
    //@@author

    //@@author A0139379M
    void completeTasks(int startIndex, int endIndex)
            throws DuplicateTaskException, IllegalValueException;
    //@@author
    void completeTaskAt(int filteredTaskListIndex)
            throws DuplicateTaskException, IllegalValueException;
    // =========== ModelManager Util Methods =======================
    // =============================================================
    /**
     * Clears existing backing model and replaces with the provided new data.
     * @throws IllegalValueException
     */
    void resetData(ReadOnlyTaskManager newData) throws IllegalValueException;

    //@@author A0143853A
    /** Copies tasks over into current TaskManager. **/
    void copyData(ReadOnlyTaskManager newData) throws IllegalValueException;
    //@@author

    // =========== ModelManager Display ============================
    // =============================================================
    //@@author A0141094M
    void updateFilteredListToShowDeadline();

    void updateFilteredListToShowDuration();

    void updateFilteredListToShowDone();

    void updateFilteredListToShowUndone();

    void updateFilteredListToShowUntimed();
    //@@author

    void updateFilteredListToShowDefault();

    void updateFilteredTaskList(Type type);

    void updateFilteredTaskList(String type);
    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);
    //@@author A0141094M
    void updateFilteredTaskList(Set<String> keywords, Set<String> tagKeywords);
    //@@author

    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    //@@author A0143853A
    void uncompleteTaskAtForUndo(int taskManagerIndex)
            throws DuplicateTaskException, IllegalValueException;

    void completeTaskAtForRedo(int taskManagerIndex)
            throws DuplicateTaskException, IllegalValueException;

    void completeTasksAndStoreIndices(int startIndex, int endIndex, ArrayList<Integer> list)
            throws DuplicateTaskException,IllegalValueException;

    void deleteTasks(int startIndex, int endIndex)
            throws TaskNotFoundException, IllegalValueException;

    void deleteTaskAt(int index);

    void uncompleteTasksAtForUndo(ArrayList<Integer> list)
            throws DuplicateTaskException, IllegalValueException;

    void completeTasksAtForRedo(ArrayList<Integer> list)
            throws DuplicateTaskException, IllegalValueException;

    void addTasksForUndo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException;

    void deleteTasksForRedo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException;

    int getNumberUncompletedEvents();

    int getNumberUncompletedDeadlines();

    int getNumberUncompletedFloatingTasks();

    //@@author

    //@@author A0139392X
    int getNumberOverdue();
    //@@author
}
