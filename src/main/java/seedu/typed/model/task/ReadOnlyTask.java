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

    //@author A0141094M
    Date getFrom();

    Date getTo();

    Notes getNotes();
    //@@author

    //@@author A0139392X
    boolean haveDuration();
    //@@author

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
    
    LocalDateTime getDateAdded();

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
                //@@author A0141094M
                && other.getNotes().getValue().equals(this.getNotes().getValue())
                && other.getFrom().getValue().equals(this.getFrom().getValue())
                && other.getTo().getValue().equals(this.getTo().getValue())
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
        .append(" Date: ").append(getDate().toString())
        .append(" From: ").append(getFrom().toString())
        .append(" To: ").append(getTo().toString())
        //@@author
        .append(" Completed: ").append(getIsCompleted())
        .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
