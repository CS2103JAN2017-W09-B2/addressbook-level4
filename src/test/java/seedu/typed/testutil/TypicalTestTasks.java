package seedu.typed.testutil;

import java.time.Month;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public DateTime testDate1 = DateTime.getDateTime(2018, Month.JANUARY, 1, 0, 0);
    public DateTime testDate2 = DateTime.getDateTime(2018, Month.JANUARY, 2, 0, 0);
    public DateTime testDate3 = DateTime.getDateTime(2018, Month.JANUARY, 3, 0, 0);

    public DateTime testDate4 = DateTime.getDateTime(2018, Month.JANUARY, 4, 0, 0);
    public DateTime testDate5 = DateTime.getDateTime(2018, Month.JANUARY, 5, 0, 0);
    public DateTime testDate6 = DateTime.getDateTime(2018, Month.JANUARY, 6, 0, 0);

    public DateTime testDate7 = DateTime.getDateTime(2018, Month.JANUARY, 7, 0, 0);
    public DateTime testDate8 = DateTime.getDateTime(2018, Month.JANUARY, 8, 0, 0);
    public DateTime testDate9 = DateTime.getDateTime(2018, Month.JANUARY, 9, 0, 0);

    public TypicalTestTasks() {
        try {
            //@@author A0141094M
            alice = new TaskBuilder().withName("Meet Alice Pauline").withDeadline(testDate1)
                    .withNotes("").withTags("friends").build();
            benson = new TaskBuilder().withName("Meet Benson Meier").withDeadline(testDate2)
                    .withNotes("").withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Meet Carl Kurz").withDeadline(testDate3)
                    .withNotes("").build();
            daniel = new TaskBuilder().withName("Meet Daniel Meier").withDeadline(testDate4)
                    .withNotes("").build();
            elle = new TaskBuilder().withName("Meet Elle Meyer").withDeadline(testDate5)
                    .withNotes("").build();
            fiona = new TaskBuilder().withName("Meet Fiona Kunz").withDeadline(testDate6)
                    .withNotes("").build();
            george = new TaskBuilder().withName("Meet George Best").withDeadline(testDate7)
                    .withNotes("").build();

            // Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withDeadline(testDate8)
                    .withNotes("").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withDeadline(testDate9)
                    .withNotes("").build();
            //@@author
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                //@@author A0141094M
                Task toAdd = new Task(task.getName(), task.getNotes(), task.getSE(),
                        task.getTags(), false);
                //@@author
                tm.addTask(toAdd);

            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TestTask[] getTypicalTasksReverse() {
        return new TestTask[] { george, fiona, elle, daniel, carl, benson, alice };
    }

    public TestTask[] getEmpty() {
        return new TestTask[] {};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }

}
