package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.schedule.ScheduleElement;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Notes notes;
    private ScheduleElement se;

    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Notes notes, DateTime date, DateTime startDate,
            DateTime endDate, UniqueTagList tags, boolean isCompleted) {
        assert !CollectionUtil.isAnyNull(name, tags);
        assert name != null;
        if (startDate != null && endDate != null) {
            // both startDate, endDate not null => event
            this.se = ScheduleElement.makeEvent(startDate, endDate);
        } else if (date != null) {
            // date not null => deadline
            this.se = ScheduleElement.makeDeadline(date);
        } else {
            // all nulls => floating task
            this.se = ScheduleElement.makeFloating();
        }
        this.name = name;
        this.notes = notes;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
    }

    public Task(Name name, Notes notes, ScheduleElement se, UniqueTagList tags, boolean isCompleted) {
        this.name = name;
        this.notes = notes;
        this.se = se;
        this.tags = tags;
        this.isCompleted = isCompleted;
    }
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getNotes(), source.getSE(), source.getTags(), source.getIsCompleted());
    }

    public void setSE(ScheduleElement se) {
        this.se = se;
    }

    @Override
    public ScheduleElement getSE() {
        return se;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0141094M
    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    @Override
    public Notes getNotes() {
        return notes;
    }
    //@@author

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Updates this task with the details of {@code replacement}.
     * @throws IllegalValueException
     */
    public void resetData(ReadOnlyTask replacement) throws IllegalValueException {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setNotes(replacement.getNotes());
        this.setSE(replacement.getSE());
        this.setTags(replacement.getTags());
        this.setIsCompleted(replacement.getIsCompleted());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0141094M
    @Override
    public boolean isEvent() {
        return se.isEvent();
    }

    @Override
    public boolean isDeadline() {
        return se.isDeadline();
    }
    @Override
    public boolean isFloating() {
        return se.isFloating();
    }
    //@@author
    @Override
    public boolean isRecurring() {
        return se.isRecurring();
    }

    public ScheduleElement updateDate() {
        return this.se.updateDate();
    }
}
