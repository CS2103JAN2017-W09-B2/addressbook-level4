/**
 *
 */
package seedu.typed.logic.commands.util;

/**
 * @author Peixuan
 *
 */
public enum Day {
    MON(1),
    TUE(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6),
    SUN(7);

    private int day;

    Day(int day) {
        this.day = day;
    }
    public int day() {
        return this.day;
    }
}
