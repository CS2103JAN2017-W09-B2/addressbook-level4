//@@author A0141094M-unused

package seedu.typed.logic.parser;

import java.util.Optional;

/**
 * Validates format of parsed tag.
 */

public class TagValidator extends ArgumentValidator {

    public TagValidator(Optional<String> tag) {
        this.validationRegex = "\\p{Alnum}+";
        this.messageConstraints = "Tag names should be alphanumeric.";
        this.arg = tag;
    }

}
//@@author
