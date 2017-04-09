//@@author A0141094M

package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's notes in the task manager.
 * Guarantees: immutable;
 */

public class Notes {

    private static final String EMPTY_STRING = "";
    private static final String MESSAGE_NOTES_CONSTRAINTS = "Task notes should not contain `#` or `+`";
    private static final String NOTES_VALIDATION_REGEX = "[\\p{Punct}&&[^\\+\\#]]*[\\p{Alnum}]+[\\p{Graph} "
            + "&&[^\\+\\#]]*";

    private String value;

    /**
     * Sets default value of a task's notes to be empty.
     */
    public Notes() {
        value = EMPTY_STRING;
    }

    /**
     * Validates a given task notes.
     * @param {@code notes} possibly null or empty String
     * @throws IllegalValueException if {@code notes} is invalid
     */
    public Notes(String notes) throws IllegalValueException {
        value = EMPTY_STRING;
        if (notes == null || isInvalidNotes(notes.trim())) {
            throw new IllegalValueException(MESSAGE_NOTES_CONSTRAINTS);
        }
        value = notes.trim();
    }

    /**
    * Checks if {@code test} is a valid task notes that does not contain `#` or `+`.
    * @param {@code test} non-null String
    * @return true if {@code test} is a valid task notes
    */
    public static boolean isInvalidNotes(String test) {
        assert test != null;
        return !test.isEmpty() && !test.matches(NOTES_VALIDATION_REGEX);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                        && this.value.equals(((Notes) other).getValue())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
