package seedu.typed.logic.commands;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.schedule.ScheduleElement;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    //@@author A0141094M
    public static final String ADD_COMMAND_WORD = "add";
    public static final String CREATE_COMMAND_WORD = "create";
    public static final String DO_COMMAND_WORD = "do";
    public static final String NEW_COMMAND_WORD = "new";
    //@@author

    public static final String MESSAGE_USAGE = ADD_COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME [by DATE] [on DATE] [from DATE to DATE] [#TAG]...\n" + "Example: " + ADD_COMMAND_WORD
            + " buy 5 broccolis by tmr #survival #grocery ";

    public static final String MESSAGE_SUCCESS = "%1$s added";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    //@@author A0141094M
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String notes, LocalDateTime date, LocalDateTime from,
            LocalDateTime to, Set<String> tags, String every) throws IllegalValueException {
        ScheduleElement se;
        if (date == null && from != null && to != null) {
            se = new ScheduleElement(new DateTime(from), new DateTime(to), every);
        } else if (date != null && from == null && to == null) {
            se = new ScheduleElement(new DateTime(date), every);
        } else if (every != null) {
            se = new ScheduleElement(every);
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
            System.out.println(date.getMinute() + " " + date.getHour());
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

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert session != null;

        try {
            //@@author A0143853A
            model.addTask(0, toAdd);
            model.getTaskManager().printData();
            session.updateUndoRedoStacks(CommandTypeUtil.TYPE_ADD_TASK, 0, toAdd);
            session.updateValidCommandsHistory(commandText);
            //@@author
            String name = toAdd.getName().toString();
            return new CommandResult(String.format(MESSAGE_SUCCESS, name));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

}
