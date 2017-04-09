package seedu.typed.schedule;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * TimeExpression is an expression that represents
 * a recurrence rule. It is very powerful and is
 * supported with set expressions to come up with more
 * expressive recurrences.
 *
 * @author: YIM CHIA HUI
 **/
public interface TimeExpression {

    /**
     * Checks if date falls within TimeExpression
     * i.e if 12/12/2017 falls within every Monday
     * or second tuesday of every month etc
     *
     * @params date
     * @return True if TimeExpression includes date
     **/
    public boolean includes(DateTime date);

    /**
     * Returns the next DateTime
     * where it falls within the TimeExpression
     *
     * @param date
     * @return the next occurring DateTime object where
     * includes return true
     */
    public DateTime nextOccurrence(DateTime date);

}
