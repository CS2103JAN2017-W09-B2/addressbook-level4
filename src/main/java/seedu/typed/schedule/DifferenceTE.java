package seedu.typed.schedule;

import seedu.typed.model.task.DateTime;

//@@author A0139379M
/**
 * DifferenceTE represents the logical difference
 * of exactly 2 TimeExpressions.
 * Example:Difference(lastMonday, Holidays) would match dates
 * that are last monday but not holidays(defined to match all the holidays possible)
 * @author YIM CHIA HUI
 */
public class DifferenceTE implements TimeExpression {
    private TimeExpression included;
    private TimeExpression excluded;

    public DifferenceTE(TimeExpression included, TimeExpression excluded) {
        this.included = included;
        this.excluded = excluded;
    }
    @Override
    public boolean includes(DateTime date) {
        return included.includes(date) && !excluded.includes(date);
    }
    @Override
    public DateTime nextDeadlineOccurrence(DateTime date) {
        boolean notFound = true;
        DateTime nextOccurrence = included.nextDeadlineOccurrence(date);
        while (notFound) {
            // if nextOccurrence fulfils time expression and is after given date
            if (includes(nextOccurrence) && nextOccurrence.isAfter(date)) {
                break;
            }
            nextOccurrence = included.nextDeadlineOccurrence(nextOccurrence);
        }
        return nextOccurrence;
    }
    //@@author
    @Override
    public TimeExpression getDuplicate() {
        return new DifferenceTE(included.getDuplicate(),
                                excluded.getDuplicate());
    }
    //@@author A0143853A

    //@@author
}
