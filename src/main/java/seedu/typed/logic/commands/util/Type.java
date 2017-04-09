//@@author A0141094M

/**
 *
 */
package seedu.typed.logic.commands.util;

/**
 * @author Peixuan
 *
 */
public enum Type {
    ALL("all"),
    DEADLINE("deadline"),
    EVENT("event"),
    DONE("done"),
    UNDONE("undone"),
    FLOATING("floating");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
