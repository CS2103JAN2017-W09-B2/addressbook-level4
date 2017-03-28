package commandunittests;

import java.util.Set;

import seedu.typed.commons.core.UnmodifiableObservableList;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.ModelManager;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;
//@@author A0139379M
public class ModelStub extends ModelManager {

    private TaskManager taskManager;

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        // TODO Auto-generated method stub

    }

    public ModelStub() throws IllegalValueException {
        this.taskManager = new TaskManager();
    }

    public void addTestTask() {
        try {
            this.taskManager.addTask(new TaskBuilder().setName("Meet Joe")
                    .setDate("12/12/2017")
                    .build());
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws DuplicateTaskException {
        // TODO Auto-generated method stub

    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateFilteredListToShowAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        // TODO Auto-generated method stub

    }

}
//@@author A0139379M