/**
 *
 */
package seedu.typed.logic.commands.util;

/**
 * @author Peixuan
 *
 */
public enum Category {
    ALL("all"),
    TASKS("tasks"),
    EVENTS("events"),
    DONE("done"),
    UNDONE("undone"),
    OVER("over"),
    UPCOMING("upcoming"),
    DUE("due");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
