package seedu.typed.model.util;

import java.time.Month;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.ReadOnlyTaskManager;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            int taskNumber = 6;
            Task[] tasks = new Task[6];
            String[] names = new String[] {"Meet Alex Yeoh", "Meet Bernice Yu", "Meet Charlotte Oliveiro",
                "Meet David Li", "Meet Irfan Ibrahim", "Meet Roy Balakrishnan"};
            DateTime[] dates = new DateTime[] {
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0),
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0),
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0),
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0),
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0),
                DateTime.getDateTime(2017, Month.JANUARY, 20, 0, 0)};
            //@@author A0141094M
            String[] notes = new String[] {"", "", "", "", "", ""};
            //@@author
            String[] tags = new String[] {"friends", "colleagues", "neighbours", "family", "classmates",
            "colleagues"};
            for (int i = 0; i < taskNumber; i++) {
                tasks[i] = new TaskBuilder()
                        .setName(names[i])
                        //@@author A0141094M
                        .setNotes(notes[i])
                        .setDeadline(dates[i])
                        //@@author
                        .addTags(tags[i])
                        .build();
            }
            return tasks;
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTM = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTM.addTask(sampleTask);
            }
            return sampleTM;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
