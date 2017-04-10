package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager.
 */

public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task name should not contain `#` or `+`, "
            + "should not be blank "
            + "and should contain at least one alphanumeric.";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Punct}&&[^\\+\\#]]*[\\p{Alnum}]+[\\p{Graph} "
            + "&&[^\\+\\#]]*";

    private final String value;

    //@@author A0141094M
    /**
     * Validates a given task name.
     * @param {@code name} non-null String
     * @throws IllegalValueException if {@code name} is invalid
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmed = name.trim();
        if (!isValidName(trimmed)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        value = name;
    }

    /**
    * Checks if {@code test} is a valid task name that does not contain `#` or `+`.
    * @param {@code test} non-null String
    * @return true if {@code test} is a valid task name
    */
    public static boolean isValidName(String test) {
        assert test != null;
        return test.matches(NAME_VALIDATION_REGEX);
    }
    //@@author

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                        && this.value.equals(((Name) other).getValue())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return this.value;
    }

}
