//@@author A0141094M

package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's notes in the task manager. Guarantees: immutable;
 */
public class Notes {

    public static final String MESSAGE_NOTES_CONSTRAINTS =
            "Task notes should only contain alphanumeric characters and spaces";
    public static final String NOTES_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    /**
     * Validates given notes.
     * @param notes
     * @throws IllegalValueException
     *             if given notes string is invalid.
     */
    public Notes(String notes) throws IllegalValueException {
        if (notes == null) {
            this.value = "";
        } else {
            String trimmedNotes = notes.trim();
            if (!trimmedNotes.isEmpty() && !isValidNotes(trimmedNotes)) {
                throw new IllegalValueException(MESSAGE_NOTES_CONSTRAINTS);
            }
            this.value = trimmedNotes;
        }
    }

    /**
    *
    * @param test
    * @return true if a given notes is a valid task notes.
    */
    public static boolean isValidNotes(String test) {
        return test.matches(NOTES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                        && this.value.equals(((Notes) other).getValue())); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return this.value;
    }

}
