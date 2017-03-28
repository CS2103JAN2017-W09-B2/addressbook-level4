package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager. Guarantees: immutable;
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task name should only contain alphanumeric characters and spaces,\n"
            + "and should not be blank";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given name.
     * @param date
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.value = name;
    }

    /**
    *
    * @param test
    * @return true if a given string is a valid task name.
    */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                        && this.value.equals(((Name) other).getValue())); // state
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
