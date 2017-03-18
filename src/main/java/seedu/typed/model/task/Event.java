package seedu.typed.model.task;

import seedu.typed.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent{
    
    private Name name;
    private DateTime startDate;
    private DateTime endDate;
    private UniqueTagList tags;
    private boolean isRecurring;
    private String description;
    
    public Event(Name name, DateTime startDate, DateTime endDate, UniqueTagList tags, boolean isRecurring,
            String description) {
        super();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.isRecurring = isRecurring;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent// instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyEvent) other));
    }

}
