package seedu.typed.testutil;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Name;
import seedu.typed.model.task.Notes;
import seedu.typed.schedule.ScheduleElement;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag : tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDeadline(DateTime date) {
        this.task.setSE(ScheduleElement.makeDeadline(date));
        return this;
    }

    public TaskBuilder withEvent(DateTime startDate, DateTime endDate) {
        this.task.setSE(ScheduleElement.makeEvent(startDate, endDate));
        return this;
    }

    //@@author A0141094M
    public TaskBuilder withNotes(String notes) throws IllegalValueException {
        this.task.setNotes(new Notes(notes));
        return this;
    }
    //@@author

    public TestTask build() {
        return this.task;
    }

}
