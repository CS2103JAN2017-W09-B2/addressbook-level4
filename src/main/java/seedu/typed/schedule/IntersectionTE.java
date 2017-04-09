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
public class IntersectionTE implements TimeExpression {
    private ArrayList<TimeExpression> elements;

    public IntersectionTE() {
        this.elements = new ArrayList<>();
    }
    public IntersectionTE(TimeExpression te1, TimeExpression te2) {
        this.elements = new ArrayList<>();
        this.elements.add(te2);
        this.elements.add(te1);
    }

    public void addTE(TimeExpression te) {
        this.elements.add(te);
    }
    @Override
    public boolean includes(DateTime date) {
        for (TimeExpression te : elements) {
            if (!te.includes(date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * next deadline occurrence just needs to keep iterating through
     * the next deadline occurrence of one of the time expression
     * as it is the only date it fulfils
     */
    @Override
    public DateTime nextOccurrence(DateTime dateTime) {
        DateTime current = elements.get(0).nextOccurrence(dateTime);
        while (!includes(current)) {
            current = elements.get(0).nextOccurrence(current);
        }
        return current;
    }
}
