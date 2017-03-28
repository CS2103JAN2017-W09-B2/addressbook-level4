package seedu.typed.testutil;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            //@@author A0141094M
            alice = new TaskBuilder().withName("Meet Alice Pauline").withDate("01/01/2018").withFrom("")
                    .withTo("").withNotes("").withTags("friends").build();
            benson = new TaskBuilder().withName("Meet Benson Meier").withDate("02/01/2018").withFrom("")
                    .withTo("").withNotes("").withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Meet Carl Kurz").withDate("03/01/2018").withFrom("")
                    .withTo("").withNotes("").build();
            daniel = new TaskBuilder().withName("Meet Daniel Meier").withDate("04/01/2018").withFrom("")
                    .withTo("").withNotes("").build();
            elle = new TaskBuilder().withName("Meet Elle Meyer").withDate("05/01/2018").withFrom("")
                    .withTo("").withNotes("").build();
            fiona = new TaskBuilder().withName("Meet Fiona Kunz").withDate("06/01/2018").withFrom("")
                    .withTo("").withNotes("").build();
            george = new TaskBuilder().withName("Meet George Best").withDate("07/01/2018").withFrom("")
                    .withTo("").withNotes("").build();

            // Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withDate("08/02/2018")
                    .withFrom("").withTo("").withNotes("").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withDate("09/02/2018")
                    .withFrom("").withTo("").withNotes("").build();
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
                Task toAdd = new Task(task.getName(), task.getNotes(), task.getDate(),
                        task.getFrom(), task.getTo(), task.getTags());
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
