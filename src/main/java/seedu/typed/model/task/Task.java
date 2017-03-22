package seedu.typed.model.task;


import seedu.typed.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;

    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, UniqueTagList tags, boolean isCompleted) {
        // commented this out!! allow date tags be null
        //assert !CollectionUtil.isAnyNull(name, date, tags);
        assert name != null;

        this.name = name;
        this.date = date;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        this.isCompleted = isCompleted;
        // changes
        // in the arg list
    }
    /**
     * Alternative Constructor with isCompleted false as default
     * @param name
     * @param date
     * @param tags
     */
    public Task(Name name, Date date, UniqueTagList tags) {
        // commented this out, allow date tags to be null
        //assert !CollectionUtil.isAnyNull(name, date, tags);
        assert name != null;

        this.name = name;
        this.date = date;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = false;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags(), source.getIsCompleted());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDate(Date date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

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

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDate(replacement.getDate());
        this.setTags(replacement.getTags());
        this.setIsCompleted(replacement.getIsCompleted());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
