package seedu.typed.testutil;


import java.util.Optional;

import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.Name;
import seedu.typed.model.task.Notes;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.schedule.ScheduleElement;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private ScheduleElement se;
    //@@author A0141094M
    private Notes notes;
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
        this.se = taskToCopy.getSE().orElse(null);
        //@@author A0141094M
        this.notes = taskToCopy.getNotes();
        //@@author
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setSE(ScheduleElement se) {
        this.se = se;
    }

    //@@author A0141094M
    public void setNotes(Notes notes) {
        this.notes = notes;
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

    public Optional<ScheduleElement> getSE() {
        return Optional.ofNullable(se);
    }

    //@@author A0141094M
    public String getAddCommand() {
        Optional<ScheduleElement> optSE = this.getSE();
        String dateInput = "";
        if (optSE.isPresent()) {
            dateInput = optSE.get().toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().getValue() + " ");
        sb.append(dateInput);
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
    //@@author

}
