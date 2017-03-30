package seedu.typed.Schedule;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * TimeExpression is an expression that specifies 
 * a recurring date. All TimeExpression has an individual
 * instance method to work out whether a day lies in the
 * expression. 
 * @author: YIM CHIA HUI
 **/
public abstract class TimeExpression {

    /**
     * Checks if date falls within TimeExpression
     * i.e if 12/12/2017 falls within every Monday
     * or second tuesday of every month etc
     * @params DateTime date
     * @return True if TimeExpression includes date
     **/
    public abstract boolean includes(DateTime date);

}
