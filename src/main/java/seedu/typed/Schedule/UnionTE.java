package seedu.typed.Schedule;

import java.util.ArrayList;

import seedu.typed.model.task.DateTime;

//@@author A0139379M
/**
 * UnionTE represents the union of TimeExpressions 
 * where includes would return true for either of the 
 * TimeExpression.
 * Example UnionTE(LastMonday, LastTuesday) would return true
 * for a either a date which lands on the last Monday or Tuesday
 * @author YIM CHIA HUI
 *
 */
public class UnionTE extends TimeExpression {
    ArrayList<TimeExpression> elements;

    @Override
    public boolean includes(DateTime date) {
        for (TimeExpression te : elements) {
            if (te.includes(date)) {
                return true;
            }
        }
        return false;
    }

    public UnionTE(TimeExpression te1, TimeExpression te2) {
        this.elements = new ArrayList<>();
        this.elements.add(te1);
        this.elements.add(te2);
    }

    public UnionTE(TimeExpression... te) {
        this.elements = new ArrayList<>();
        for (int i = 0; i < te.length; i++) {
            this.elements.add(te[i]);
        }
    }
}
