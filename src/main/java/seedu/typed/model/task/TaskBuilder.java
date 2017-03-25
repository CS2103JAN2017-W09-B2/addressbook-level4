package seedu.typed.model.task;

import java.util.HashSet;
import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.tag.UniqueTagList.DuplicateTagException;


/**
 * TaskBuilder helps to build a Task object by being flexible in
 * what attributes a Task object will initialise with. In particular,
 * only a name is compulsory whereas other attributes are optional.
 * @param ReadOnlyTask An existing task to modify
 * @return Task
 * @author YIM CHIA HUI
 *
 */

public class TaskBuilder {

    private Name name;
    private Notes notes;
    private Date date;
    private Date from;
    private Date to;
    private UniqueTagList tags;
    private boolean isCompleted;

    public TaskBuilder() {
        this.tags = new UniqueTagList();
    }

    public TaskBuilder(ReadOnlyTask task) throws IllegalValueException {
        this.name = new Name(task.getName().getValue());
        this.notes = new Notes(task.getNotes().getValue());
        this.date = new Date(task.getDate().getValue());
        this.from = new Date(task.getFrom().getValue());
        this.to= new Date(task.getTo().getValue());
        this.tags = task.getTags();
        this.isCompleted = task.getIsCompleted();
    }

    public TaskBuilder setName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public TaskBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    //@@author A0141094M
    public TaskBuilder setNotes(String notes) throws IllegalValueException {
        this.notes = new Notes(notes);
        return this;
    }

    public TaskBuilder setNotes(Notes notes) {
        this.notes = notes;
        return this;
    }
    //@@author

    public TaskBuilder setDate(String date) throws IllegalValueException {
        this.date = new Date(date);
        return this;
    }

    public TaskBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    //@@author A0141094M
    public TaskBuilder setFrom(String from) throws IllegalValueException {
        this.from = new Date(from);
        return this;
    }

    public TaskBuilder setFrom(Date from) {
        this.from = from;
        return this;
    }

    public TaskBuilder setTo(String to) throws IllegalValueException {
        this.to = new Date(to);
        return this;
    }

    public TaskBuilder setTo(Date to) {
        this.to = to;
        return this;
    }
    //@@author A0141094M

    public TaskBuilder isCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public TaskBuilder addTags(String tag) throws DuplicateTagException, IllegalValueException {
        this.tags.add(new Tag(tag));
        return this;
    }

    public TaskBuilder setTags(UniqueTagList tags) {
        this.tags = tags;
        return this;
    }

    public TaskBuilder setTags(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.tags = new UniqueTagList(tagSet);
        return this;
    }

    public Task build() {
        return new Task(name, notes, date, from, to, tags, isCompleted);
    }

}
