package seedu.typed.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.core.Messages;
import seedu.typed.commons.events.ui.JumpToListRequestEvent;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.Name;
import seedu.typed.model.task.Notes;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.typed.schedule.ScheduleElement;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    //@@author A0141094M
    public static final String COMMAND_WORD_EDIT = "edit";
    public static final String COMMAND_WORD_UPDATE = "update";
    public static final String COMMAND_WORD_CHANGE = "change";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORD_EDIT + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX [NAME] [by DATE] [#TAG]...\n"
            + "Example: " + COMMAND_WORD_EDIT + " 1 buy 10 broccolis by 06/03/2017";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_EDIT_TASK_FAILURE = "Cannot edit selected task!";
    public static final String MESSAGE_EDIT_DATE_FAILURE = "You have entered invalid date formats!";
    public static final String MESSAGE_NOT_EDITED = "At least one edited field must be provided!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists on Typed!";

    private static final String MESSAGE_TASK_NOT_FOUND = "Task to edit not found!";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        //@@author A0141094M
        if (listIndexOutOfBounds(lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        //@@author

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task taskToEditCopy;
        Task editedTask;

        try {
            taskToEditCopy = new TaskBuilder(taskToEdit).build();
            editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_EDIT_TASK_FAILURE);
        }

        try {
            //@@author A0143853A
            int taskManagerIndex = model.getIndexOfTask(taskToEditCopy);
            model.updateTask(filteredTaskListIndex, editedTask);
            session.updateUndoRedoStacks(COMMAND_WORD_EDIT, taskManagerIndex, taskToEditCopy);
            //@@author

            //@@author A0139392X
            EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredTaskListIndex));
            //@@author

        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        } catch (IllegalValueException ive) {
            throw new CommandException(MESSAGE_EDIT_TASK_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * @param lastShownList
     * @return
     */
    private boolean listIndexOutOfBounds(List<ReadOnlyTask> lastShownList) {
        return filteredTaskListIndex >= lastShownList.size();
    }

    //@@author A0141094M
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        assert taskToEdit != null;
        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Notes updatedNotes = editTaskDescriptor.getNotes().orElseGet(taskToEdit::getNotes);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        ScheduleElement updatedSe;

        //current implementation only supports changing deadlines and adding deadlines to floating
        if (taskToEdit.isDeadline() || taskToEdit.isFloating()) {
            updatedSe = new ScheduleElement(editTaskDescriptor.getDate().get(),
                    taskToEdit.getSE().getStartDate(), taskToEdit.getSE().getEndDate());
        } else {
            throw new IllegalValueException(MESSAGE_EDIT_TASK_FAILURE);
        }
        return new TaskBuilder()
                .setName(updatedName)
                .setNotes(updatedNotes)
                .setSE(updatedSe)
                .setTags(updatedTags)
                .isCompleted(taskToEdit.getIsCompleted())
                .build();
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<DateTime> date = Optional.empty();
        private Optional<DateTime> from = Optional.empty();
        private Optional<DateTime> to = Optional.empty();
        private Optional<Notes> notes = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            this.from = toCopy.getFrom();
            this.to = toCopy.getTo();
            this.notes = toCopy.getNotes();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.date, this.from, this.to, this.notes, this.tags);
        }

        public ScheduleElement getSE() {
            DateTime deadline = date.orElse(null);
            DateTime startDateTime = from.orElse(null);
            DateTime endDateTime = to.orElse(null);
            return new ScheduleElement(deadline, startDateTime, endDateTime);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }
        public Optional<Name> getName() {
            return name;
        }

        public void setDate(Optional<DateTime> date) {
            assert date != null;
            this.date = date;
        }
        public Optional<DateTime> getDate() {
            return date;
        }

        public void setFrom(Optional<DateTime> from) {
            assert from != null;
            this.from = from;
        }
        public Optional<DateTime> getFrom() {
            return from;
        }

        public void setTo(Optional<DateTime> to) {
            assert to != null;
            this.to = to;
        }
        public Optional<DateTime> getTo() {
            return to;
        }

        public void setNotes(Optional<Notes> notes) {
            assert notes != null;
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return notes;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
    //@@author
}
