package commandunittests;

import java.time.Month;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.ModelManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
//@@author A0139379M
public class ModelStub extends ModelManager {

    private TaskManager taskManager;

    public ModelStub() throws IllegalValueException {
        this.taskManager = new TaskManager();
    }

    public void addTestTask() {
        try {
            this.taskManager.addTask(new TaskBuilder().setName("Meet Joe")
                    .setDeadline(DateTime.getDateTime(2017, Month.DECEMBER, 12, 0, 0))
                    .build());
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

}
