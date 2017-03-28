package seedu.typed.model.task;


import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Notes notes;
    private Date date;
    private Date from;
    private Date to;

    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Notes notes, Date date, Date from, Date to, UniqueTagList tags, boolean isCompleted) {
        // commented this out!! allow date tags be null
        //assert !CollectionUtil.isAnyNull(name, date, tags);
        assert name != null;

        this.name = name;
        this.notes = notes;
        this.date = date;
        this.from = from;
        this.to = to;
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
    public Task(Name name, Notes notes, Date date, Date from, Date to, UniqueTagList tags) {
        // commented this out, allow date tags to be null
        //assert !CollectionUtil.isAnyNull(name, date, tags);
        assert name != null;

        this.name = name;
        this.notes = notes;
        this.date = date;
        this.from = from;
        this.to = to;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = false;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getNotes(), source.getDate(),
                source.getFrom(), source.getTo(), source.getTags(), source.getIsCompleted());
    }

    public void setName(Name name) throws IllegalValueException {
        assert name != null;
        this.name = new Name(name.getValue());
    }

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0141094M
    public void setNotes(Notes notes) throws IllegalValueException {
        assert notes != null;
        this.notes = new Notes(notes.getValue());
    }

    @Override
    public Notes getNotes() {
        return notes;
    }
    //@@author

    public void setDate(Date date) throws IllegalValueException {
        assert date != null;
        this.date = new Date(date.getValue());
    }

    @Override
    public Date getDate() {
        return date;
    }

    //@@author A0141094M
    public void setFrom(Date from) throws IllegalValueException {
        assert from != null;
        this.from = new Date(from.getValue());
    }

    @Override
    public Date getFrom() {
        return from;
    }

    public void setTo(Date to) throws IllegalValueException {
        assert to != null;
        this.to = new Date(to.getValue());
    }

    @Override
    public Date getTo() {
        return to;
    }
    //@@author

    //@@author A0139392X
    @Override
    public boolean haveDuration() {
        return (!this.getFrom().isEmpty());
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
        this.setDate(replacement.getDate());
        this.setFrom(replacement.getFrom());
        this.setTo(replacement.getTo());
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
