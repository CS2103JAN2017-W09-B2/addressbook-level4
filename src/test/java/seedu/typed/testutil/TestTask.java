package seedu.typed.testutil;

import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.Date;
import seedu.typed.model.task.Name;
import seedu.typed.model.task.Notes;
import seedu.typed.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Notes notes;
    private Date date;
    private Date from;
    private Date to;
    private UniqueTagList tags;
    private boolean isCompleted;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.notes = taskToCopy.getNotes();
        this.date = taskToCopy.getDate();
        this.from = taskToCopy.getFrom();
        this.to = taskToCopy.getTo();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    //@@author A0141094M
    public void setNotes(Notes notes) {
        this.notes = notes;
    }
    //@@author

    public void setDate(Date date) {
        this.date = date;
    }

    //@@author A0141094M
    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }
    //@@author

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Name getName() {
        return name;
    }

    //@@author A0141094M
    @Override
    public Notes getNotes() {
        return notes;
    }
    //@@author

    @Override
    public Date getDate() {
        return date;
    }

    //@@author A0141094M
    @Override
    public Date getFrom() {
        return from;
    }

    @Override
    public Date getTo() {
        return to;
    }
    //@@author

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public boolean getIsCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public boolean haveDuration() {
        if (from != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().getValue() + " ");
        sb.append("by " + this.getDate().getValue() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }

}
