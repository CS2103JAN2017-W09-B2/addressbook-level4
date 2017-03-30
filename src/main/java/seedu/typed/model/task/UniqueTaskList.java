package seedu.typed.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.exceptions.DuplicateDataException;
import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls. Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    //@@author A0143853A
    public int indexOf(Task task) throws TaskNotFoundException {
        int index = internalList.indexOf(task);
        if (index == -1) {
            throw new TaskNotFoundException();
        } else {
            return index;
        }
    }
    //@@author

    //@@author A0143853A
    public Task getTaskAt(int index) {
        return internalList.get(index);
    }
    //@@author

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    //Adds task to the end of the list
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;

        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    //@@author A0143853A
    public void add(int index, Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;

        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(index, toAdd);
    }
    //@@author

    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedTask}.
     * @throws IllegalValueException
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws IllegalValueException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }
        Task edited = new TaskBuilder(editedTask).build();
        //taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the
        // updated task.
        // The right way is to implement observable properties in the task
        // class.
        // Then, taskcard should then bind its text labels to those observable
        // properties.
        internalList.set(index, edited);
    }
    public void updateTask(int index, Task editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        //taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the
        // updated task.
        // The right way is to implement observable properties in the task
        // class.
        // Then, taskcard should then bind its text labels to those observable
        // properties.
        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws DuplicateTaskException, IllegalValueException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new TaskBuilder(task).build());
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Task> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    @SuppressWarnings("serial")
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    // TODO: fill in blanks
    @SuppressWarnings("serial")
    public static class TaskNotFoundException extends Exception {
    }

    public int size() {
        return this.internalList.size();
    }

}
