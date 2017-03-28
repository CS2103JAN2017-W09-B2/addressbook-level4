package seedu.typed.model.task;

import seedu.typed.model.tag.UniqueTagList;
//@@author A0139379M
public class Event implements ReadOnlyEvent {

    private Name name;
    private DateTime startDate;
    private DateTime endDate;
    private UniqueTagList tags;
    private boolean isRecurring;
    private String details;

    public Event(Name name, DateTime startDate, DateTime endDate, boolean isRecurring,
            String details, UniqueTagList tags) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.isRecurring = isRecurring;
        this.details = details;
    }

    public Name getName() {
        return name;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public UniqueTagList getTags() {
        return tags;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public String getDetails() {
        return details;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent// instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public String toString() {
        return this.getAsText();
    }

}
//@@author A0139379M