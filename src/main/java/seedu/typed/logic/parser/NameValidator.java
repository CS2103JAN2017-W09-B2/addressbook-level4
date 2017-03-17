package seedu.typed.logic.parser;

import java.util.Optional;

/**
 * Validates format of parsed name.
 * @author Peixuan
 *
 */

public class NameValidator extends ArgumentValidator {

    public NameValidator(Optional<String> name) {
        this.validationRegex = "\\s*\\S+\\s*";
        //this.validationRegex = "[\\p{Alnum}]+";
        //this.messageConstraints = "A task name should only contain alphanumeric characters.";
        this.messageConstraints = "A task name should not be blank.";
        this.arg = name;
    }

}


