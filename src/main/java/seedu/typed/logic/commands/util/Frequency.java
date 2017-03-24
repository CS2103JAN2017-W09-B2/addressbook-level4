/**
 *
 */
package seedu.typed.logic.commands.util;

/**
 * @author Peixuan
 *
 */
public enum Frequency {
    DAY("done"),
    WEEK("all"),
    MONTH("tasks"),
    YEAR("events");

    private String frequency;

    Frequency(String frequency) {
        this.frequency = frequency;
    }

    public String frequency() {
        return this.frequency;
    }
}
