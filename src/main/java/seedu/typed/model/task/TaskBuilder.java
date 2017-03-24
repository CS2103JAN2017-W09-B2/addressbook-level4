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
    private Date date;
    private UniqueTagList tags;
    private boolean isCompleted;

    public TaskBuilder() {
        this.tags = new UniqueTagList();
    }

    public TaskBuilder(ReadOnlyTask task) throws IllegalValueException {
        this.name = new Name(task.getName().getValue());
        this.date = new Date(task.getDate().getValue());
        this.tags = task.getTags();
        this.isCompleted = task.getIsCompleted();
    }

    public TaskBuilder setName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public TaskBuilder setName(Name name) throws IllegalValueException {
        assert name != null;

        this.name = new Name(name.getValue());
        return this;
    }

    public TaskBuilder setDate(String date) throws IllegalValueException {
        this.date = new Date(date);
        return this;
    }

    public TaskBuilder setDate(Date date) throws IllegalValueException {
        assert date != null;

        this.date = new Date(date.getValue());
        return this;
    }

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
        return new Task(name, date, tags, isCompleted);
    }

}
