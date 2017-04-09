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

    /**
     * Constructor for DifferenceTE
     *
     * @param included refers to the TimeExpression to be included
     * @param excluded refers to the TimeExpression to be excluded
     */
    public DifferenceTE(TimeExpression included, TimeExpression excluded) {
        this.included = included;
        this.excluded = excluded;
    }

    @Override
    public boolean includes(DateTime date) {
        return included.includes(date) && !excluded.includes(date);
    }

    @Override
    public DateTime nextOccurrence(DateTime date) {
        // we will keep looping through the nextOccurrence of included
        // time expression until it fulfils the conditions
        boolean notFound = true;
        DateTime nextOccurrence = included.nextOccurrence(date);
        while (notFound) {
            // if nextOccurrence fulfils time expression and is after given date
            if (includes(nextOccurrence) && nextOccurrence.isAfter(date)) {
                break;
            }
            nextOccurrence = included.nextOccurrence(nextOccurrence);
        }
        return nextOccurrence;
    }
}
