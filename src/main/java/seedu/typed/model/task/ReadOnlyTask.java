package seedu.typed.model.task;

import java.util.Optional;

import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.schedule.ScheduleElement;

/**
 * A read-only immutable interface for a Task in the TaskManager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Optional<ScheduleElement> getSE();

    Notes getNotes();
    //@@author

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    boolean getIsCompleted();

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
                && other.getSE().equals(this.getSE())
                //@@author
                && other.getTags().equals(this.getTags())
                && (other.getIsCompleted() == this.getIsCompleted()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ").append(getName())
        //@@author A0141094M
        .append(" Notes: ").append(getNotes().toString())
        .append(getSE().map(ScheduleElement::toString).orElse(" "))
        //@@author
        .append(" Completed: ").append(getIsCompleted())
        .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
