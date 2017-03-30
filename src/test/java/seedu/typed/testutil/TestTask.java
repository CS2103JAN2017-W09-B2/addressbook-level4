package seedu.typed.testutil;

import java.time.LocalDateTime;

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
    private Date date;
    private LocalDateTime dateAdded;
    //@@author A0141094M
    private Notes notes;
    private Date from;
    private Date to;
    //@@author
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
        this.date = taskToCopy.getDate();
        //@@author A0141094M
        this.notes = taskToCopy.getNotes();
        this.from = taskToCopy.getFrom();
        this.to = taskToCopy.getTo();
        //@@author
        this.tags = taskToCopy.getTags();
        this.dateAdded = taskToCopy.getDateAdded();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //@@author A0141094M
    public void setNotes(Notes notes) {
        this.notes = notes;
    }

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

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    //@@author A0141094M
    @Override
    public Notes getNotes() {
        return notes;
    }

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

    //@@author A0139392X
    @Override
    public boolean haveDuration() {
        return !from.isEmpty();
    }
    //@@author

    //@@author A0141094M
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().getValue() + " ");
        sb.append("by " + this.getDate().getValue() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
    //@@author

}
