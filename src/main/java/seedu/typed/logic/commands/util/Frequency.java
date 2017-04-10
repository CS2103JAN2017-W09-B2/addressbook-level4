//@@author A0141094M

/**
 *
 */
package seedu.typed.logic.commands.util;

/**
 * @author Peixuan
 *
 */
public enum Frequency {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    private String frequency;

    Frequency(String frequency) {
        this.frequency = frequency;
    }

    public String frequency() {
        return this.frequency;
    }
}
