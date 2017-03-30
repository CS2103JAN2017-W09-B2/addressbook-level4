package seedu.typed.schedule;

import java.util.ArrayList;

import seedu.typed.model.task.DateTime;

//@@author A0139379M
/**
 * IntersectionTE returns the logical intersection of 
 * two or more TimeExpressions
 * Example: IntersectionTE(lastMonday, Jul) would refer to
 * the last Monday in July
 * @author YIM CHIA HUI
 *
 */
public class IntersectionTE extends TimeExpression {
    private ArrayList<TimeExpression> elements;

    public IntersectionTE(TimeExpression te1, TimeExpression te2) {
        this.elements = new ArrayList<>();
        this.elements.add(te2);
        this.elements.add(te1);
    }

    public IntersectionTE(TimeExpression... te) {
        this.elements = new ArrayList<>();
        for (int i = 0; i < te.length; i++) {
            this.elements.add(te[i]);
        }
    }

    public boolean includes(DateTime date) {
        for (TimeExpression te : elements) {
            if (!te.includes(date)) {
                return false;
            }
        }
        return true;
    }
}
