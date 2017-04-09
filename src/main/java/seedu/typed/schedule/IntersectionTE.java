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

    @Override
    public DateTime nextOccurrence(DateTime dateTime) {
        // we just have to iterate through one of the TimeExpression next occurrence
        // as Intersection have to fulfil all of them
        // Guarantees to terminate as it would just repeat 1 year later
        DateTime current = elements.get(0).nextOccurrence(dateTime);
        while (!includes(current)) {
            current = elements.get(0).nextOccurrence(current);
        }
        return current;
    }
    //@@author

    //@@author A0143853A
    @Override
    public TimeExpression getDuplicate() {
        IntersectionTE copy = new IntersectionTE();
        for (int i = 0; i < elements.size(); i++) {
            copy.addTE(elements.get(i).getDuplicate());
        }
        return copy;
    }
    //@@author
}
