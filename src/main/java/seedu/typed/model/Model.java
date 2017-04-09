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

    // =========== TaskManager Getters =============================
    // =============================================================
    /** Returns the TaskManager. */
    ReadOnlyTaskManager getTaskManager();

    // ================ ModelManager Getters =======================
    // =============================================================

    //@@author A0139379M
    /* Several getters to get the number of different types of tasks
     * The method names are self-explanatory.
     */
    int getNumberCompletedTasks();

    int getNumberUncompletedTasks();

    int getTotalTasks();

    int getNumberEvents();

    int getNumberDeadlines();

    int getNumberFloatingTasks();

    int getNumberUncompletedEvents();

    int getNumberUncompletedDeadlines();

    int getNumberUncompletedFloatingTasks();

    /** Get only completed and overdue tasks */
    int getNumberOverdue();
    //@@author

    //@@author A0143853A
    Task getTaskAt(int index);

    int getIndexOfTask(Task task) throws TaskNotFoundException;
    //@@author

    // =========== ModelManager Add Tasks ==========================
    // =============================================================
    /** Adds the given task. */
    void addTask(Task task) throws DuplicateTaskException;

    //@@author A0143853A
    /** Adds the given task at the specified index. */
    void addTask(int index, Task task) throws DuplicateTaskException;


    void addTasksForUndo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException;
    //@@author

    // =========== ModelManager Delete Tasks =======================
    // =============================================================

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    void deleteTasks(int startIndex, int endIndex)
            throws TaskNotFoundException, IllegalValueException;

    void deleteTaskAt(int index);
    //@@author A0143853A
    void deleteTasksForRedo(ArrayList<Pair<Integer, Task>> list)
            throws DuplicateTaskException;
    //@@author
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
    /**
     * Mark a range of tasks as completed from startIndex to endIndex 
     *
     * @param startIndex start index as seen on the filtered task list
     * @param endIndex  end index as seen on the filtered task list
     * @throws DuplicateTaskException
     * @throws IllegalValueException
     */
    void completeTasks(int startIndex, int endIndex)
            throws DuplicateTaskException, IllegalValueException;

    /**
     * Mark the task at this index as completed 
     *
     * @param filteredTaskListIndex
     * @throws DuplicateTaskException
     * @throws IllegalValueException
     */
    void completeTaskAt(int filteredTaskListIndex)
            throws DuplicateTaskException, IllegalValueException;

    //@@author
    void uncompleteTaskAtForUndo(int taskManagerIndex)
            throws DuplicateTaskException, IllegalValueException;

    void completeTaskAtForRedo(int taskManagerIndex)
            throws DuplicateTaskException, IllegalValueException;

    void completeTasksAndStoreIndices(int startIndex, int endIndex, ArrayList<Integer> list)
            throws DuplicateTaskException, IllegalValueException;

    void uncompleteTasksAtForUndo(ArrayList<Integer> list)
            throws DuplicateTaskException, IllegalValueException;

    void completeTasksAtForRedo(ArrayList<Integer> list)
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
    //@@author A0139379M
    void updateFilteredListToShowDeadline();

    void updateFilteredListToShowDuration();

    void updateFilteredListToShowDone();

    void updateFilteredListToShowUndone();

    void updateFilteredListToShowUntimed();

    void updateFilteredListToShowDefault();

    /**
     * Filter the task list based on the type given
     *
     * @param type refers to the type of tasks (deadlines, events, completed etc)
     */
    void updateFilteredTaskList(Type type);
    //@@author

    //@@author A0141094M
    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     *
     * @param keywords filter based on these keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    /**
     * Updates the filter of filtered task list to filter by the given keywords
     * or tag keywords
     * @param keywords keywords to match tasks'names
     * @param tagKeywords match tags'names
     */
    void updateFilteredTaskList(Set<String> keywords, Set<String> tagKeywords);
    //@@author

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();


    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
}
