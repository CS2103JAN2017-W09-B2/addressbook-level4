package seedu.typed.logic.commands;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.ui.JumpToListRequestEvent;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.typed.schedule.ScheduleElement;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    //@@author A0141094M
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_CREATE = "create";
    public static final String COMMAND_WORD_DO = "do";
    public static final String COMMAND_WORD_NEW = "new";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORD_ADD + ": Adds a task to Typed. "
            + "Parameters: NAME [by DATE] [on DATE] [from DATE [TIME] to DATE [TIME]] [#TAG]...\n"
            + "Example: " + COMMAND_WORD_ADD
            + " buy 5 broccolis by tomorrow #survival #grocery ";

    public static final String MESSAGE_SUCCESS = "%1$s added!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists on Typed!";

    private final Task toAdd;

    //@@author A0141094M
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String notes, LocalDateTime date, LocalDateTime from,
            LocalDateTime to, Set<String> tags, String rule) throws IllegalValueException {
        ScheduleElement se;
        if (date == null && from != null && to != null) {
            se = new ScheduleElement(new DateTime(from), new DateTime(to), rule);
        } else if (date != null && from == null && to == null) {
            se = new ScheduleElement(new DateTime(date), rule);
        } else if (rule != null) {
            se = ScheduleElement.makeDeadline(rule);
        } else {
            se = new ScheduleElement();
        }
        this.toAdd = new TaskBuilder()
                         .setName(name)
                         .setNotes(notes)
                         .setSE(se)
                         .setTags(tags)
                         .build();
    }

    public AddCommand(String name, String notes, LocalDateTime date, LocalDateTime from,
            LocalDateTime to, Set<String> tags) throws IllegalValueException {
        ScheduleElement se;
        if (date == null && from != null && to != null) {
            se = new ScheduleElement(new DateTime(from), new DateTime(to));
        } else if (date != null && from == null && to == null) {
            se = new ScheduleElement(new DateTime(date));
        } else {
            se = new ScheduleElement();
        }
        this.toAdd = new TaskBuilder()
                         .setName(name)
                         .setNotes(notes)
                         .setSE(se)
                         .setTags(tags)
                         .build();
    }
    //@@author

    //@@author A0143853A
    /**
     * Executes the add command by adding the task to be added
     * and scrolling to the index of the newly added task.
     */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert session != null;

        try {
            model.addTask(toAdd);

            int taskManagerIndex = model.getIndexOfTask(toAdd);
            session.updateUndoRedoStacks(COMMAND_WORD_ADD, taskManagerIndex, toAdd);

            int filteredListIndex = model.getFilteredTaskList().indexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredListIndex));

            String name = toAdd.getName().toString();
            return new CommandResult(String.format(MESSAGE_SUCCESS, name));
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            assert false: "Task added must be found!";

            String name = toAdd.getName().toString();
            throw new CommandException(String.format(MESSAGE_SUCCESS, name));
        }
    }
    //@@author

}
