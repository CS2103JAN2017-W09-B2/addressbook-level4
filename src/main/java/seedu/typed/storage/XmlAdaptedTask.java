package seedu.typed.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.schedule.ScheduleElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dateTime;
    //@@author A0141094M
    @XmlElement(required = true)
    private String notes;
    //@@author
    @XmlElement(required = true)
    private String rule;

    @XmlElement(required = true)
    private boolean isCompleted;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
     * required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().getValue();
        dateTime = source.getSE().toString();
        notes = source.getNotes().getValue();
        isCompleted = source.getIsCompleted();
        rule = source.getSE().getRule();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        ScheduleElement se = new ScheduleElement();
        se = se.parseDateString(dateTime, rule);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new TaskBuilder()
                .setName(this.name)
                .setSE(se)
                .setNotes(this.notes)
                .isCompleted(this.isCompleted)
                .setTags(tags)
                .build();
    }
}
