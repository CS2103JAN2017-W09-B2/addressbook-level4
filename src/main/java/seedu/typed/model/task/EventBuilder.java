package seedu.typed.model.task;

import java.util.HashSet;
import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.tag.UniqueTagList.DuplicateTagException;

//@@author A0139379M
/**
 * EventBuilder faciliates the builder of Event objects
 * By default, tags and description are empty and isRecurring is false
 * name, startDate and endDate are required
 * @param ReadOnlyEvent
 * @return Event
 * @author YIM CHIA HUI
 */
public class EventBuilder {

    private Name name;
    private DateTime startDate;
    private DateTime endDate;
    private boolean isRecurring;
    private String details;
    private UniqueTagList tags;

    public EventBuilder() {
        this.isRecurring = false;
        this.details = "";
        this.tags = new UniqueTagList();
    }

    public EventBuilder(ReadOnlyEvent event) {
        this.name = event.getName();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.isRecurring = event.isRecurring();
        this.details = event.getDetails();
        this.tags = event.getTags();
    }

    public EventBuilder setName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public EventBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public EventBuilder startDate(DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public EventBuilder endDate(DateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public EventBuilder date(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

    public EventBuilder isRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
        return this;
    }

    public EventBuilder setDetails(String details) {
        this.details = details;
        return this;
    }

    public EventBuilder addTags(String tag) throws DuplicateTagException, IllegalValueException {
        this.tags.add(new Tag(tag));
        return this;
    }

    public EventBuilder setTags(UniqueTagList tags) {
        this.tags = tags;
        return this;
    }

    public EventBuilder setTags(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.tags = new UniqueTagList(tagSet);
        return this;
    }

    public Event build() {
        return new Event(this.name, this.startDate, this.endDate, this.isRecurring,
                this.details, this.tags);
    }

}
