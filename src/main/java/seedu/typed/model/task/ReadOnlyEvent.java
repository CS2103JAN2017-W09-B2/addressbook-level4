package seedu.typed.model.task;

import seedu.typed.model.tag.UniqueTagList;
//@@author A0139379M
/**
 * A read-only immutable interface for a Event in the TaskManager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 * @author YIM CHIA HUI
 */
public interface ReadOnlyEvent {


    Name getName();
    DateTime getStartDate();
    DateTime getEndDate();

    boolean isRecurring();

    String getDetails();


    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state
                // checks here
                // onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
        .append(getName())
        .append(" Start Date: ")
        .append(getStartDate())
        .append(" End Date: ")
        .append(getEndDate()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
