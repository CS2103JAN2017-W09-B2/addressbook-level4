package seedu.typed.model.task;

import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.schedule.ScheduleElement;

/**
 * A read-only immutable interface for a Task in the TaskManager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    ScheduleElement getSE();
    Notes getNotes();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    boolean getIsCompleted();

    boolean isEvent();

    boolean isDeadline();

    boolean isFloating();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().getValue().equals(this.getName().getValue()) // state
                // checks here
                // onwards
                && isScheduleElementSame(other.getSE())
                && other.getTags().equals(this.getTags())
                && (other.getIsCompleted() == this.getIsCompleted()));
    }

    //@@author A0141094M
    default boolean isScheduleElementSame(ScheduleElement otherSE) {
        return this.getSE().toString().equals(otherSE.toString());
    }
    //@@author

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ").append(getName())
        .append(" Notes: ").append(getNotes().toString())
        .append(getSE().toString())
        .append(" Completed: ").append(getIsCompleted())
        .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
