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
    DURATION("duration"),
    DONE("done"),
    UNDONE("undone"),
    UNTIMED("untimed");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
