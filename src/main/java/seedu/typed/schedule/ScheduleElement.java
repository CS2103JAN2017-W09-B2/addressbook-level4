package seedu.typed.schedule;

import seedu.typed.model.task.DateTime;

public class ScheduleElement implements TimeExpression {

    DateTime date; // deadlines, duedates...
    DateTime startDate; // start time of the event
    DateTime endDate; // end time of the event
    TimeExpression te; // representation of the recurrence

    public DateTime getDate() {
        return date;
    }
    public void setDate(DateTime date) {
        this.date = date;
    }
    public DateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
    public DateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    public TimeExpression getTe() {
        return te;
    }
    public void setTe(TimeExpression te) {
        this.te = te;
    }
    @Override
    public boolean includes(DateTime date) {
        // TODO Auto-generated method stub
        return false;
    }



}
