package seedu.typed.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.typed.commons.core.Messages;
import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.commons.util.CollectionUtil;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.commands.util.CommandTypeUtil;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.task.Date;
import seedu.typed.model.task.Name;
import seedu.typed.model.task.Notes;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.typed.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [by DATE] [#TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 buy 10 broccolis by 06/03/2017";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_EDIT_TASK_FAILURE = "Cannot edit selected Task.";

    private static final String MESSAGE_TASK_NOT_FOUND = "Task to edit not found.";

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

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

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
            int index = model.getIndexOfTask(taskToEditCopy);
            model.updateTask(filteredTaskListIndex, editedTask);
            session.updateUndoRedoStacks(CommandTypeUtil.TYPE_EDIT_TASK, index, taskToEditCopy);
            session.updateValidCommandsHistory(commandText);
            //@@author
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        } catch (IllegalValueException ive) {
            throw new CommandException(MESSAGE_EDIT_TASK_FAILURE);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Date updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getDate);
        //@@author A0141094M
        Date updatedFrom = editTaskDescriptor.getFrom().orElseGet(taskToEdit::getFrom);
        Date updatedTo = editTaskDescriptor.getTo().orElseGet(taskToEdit::getTo);
        Notes updatedNotes = editTaskDescriptor.getNotes().orElseGet(taskToEdit::getNotes);
        //@@author
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new TaskBuilder()
                .setName(updatedName)
                .setNotes(updatedNotes)
                .setDate(updatedDate)
                .setFrom(updatedFrom)
                .setTo(updatedTo)
                .setTags(updatedTags)
                .build();
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> date = Optional.empty();
        //@@author A0141094M
        private Optional<Date> from = Optional.empty();
        private Optional<Date> to = Optional.empty();
        private Optional<Notes> notes = Optional.empty();
        //@@author
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            //@@author A0141094M
            this.from = toCopy.getFrom();
            this.to = toCopy.getTo();
            this.notes = toCopy.getNotes();
            //@@author
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.date, this.from, this.to, this.notes, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;

            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDate(Optional<Date> date) {
            assert date != null;

            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

        //@@author A0141094M

        public void setFrom(Optional<Date> from) {
            assert from != null;
            this.from = from;
        }

        public Optional<Date> getFrom() {
            return from;
        }

        public void setTo(Optional<Date> to) {
            assert to != null;
            this.to = to;
        }

        public Optional<Date> getTo() {
            return to;
        }

        public void setNotes(Optional<Notes> notes) {
            assert notes != null;
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return notes;
        }

        //@@author

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;

            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
