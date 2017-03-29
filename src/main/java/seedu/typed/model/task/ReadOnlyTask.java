package seedu.typed.model.task;

import java.time.LocalDateTime;

import seedu.typed.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the TaskManager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    Name getName();

    Date getDate();
    
    LocalDateTime getDateAdded();
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
                && other.getDateAdded().equals(this.getDateAdded())
                && other.getDate().getValue().equals(this.getDate().getValue())
                && other.getTags().equals(this.getTags())
                && (other.getIsCompleted() == this.getIsCompleted()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ").append(getName())
        .append(" Date: ").append(getDate().toString())
        .append(" Completed: ").append(getIsCompleted())
        .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
