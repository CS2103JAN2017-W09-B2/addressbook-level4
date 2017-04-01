package seedu.typed.schedule;

import seedu.typed.model.task.DateTime;

public class ScheduleElement implements TimeExpression {

    private final DateTime date; // deadlines, duedates...
    private final DateTime startDate; // start time of the event
    private final DateTime endDate; // end time of the event
    private final TimeExpression te; // representation of the recurrence
    
    /**
     * Representation of a deadline in our TaskManager
     * @param date
     */
    private ScheduleElement(DateTime date) {
        this.date = date;
        this.startDate = null;
        this.endDate = null;
        this.te = null;
    }
    /**
     * Representation of an event in our TaskManager
     * @param startDate
     * @param endDate
     */
    private ScheduleElement(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = null;
        this.te = null;
    }
    /**
     * A special method which parses a String that matches the
     * regex for adding tasks with deadlines/duration
     * @param dateInput
     * @return 
     * @return 
     */
    public static ScheduleElement parseDateString(String dateInput) {
        String[] inputs = dateInput.trim().split("\\s+");
        System.out.println("parseDateShit");
        for (int i = 0; i<inputs.length; i++) {
            System.out.println(inputs[i]);
        }
        if ("By".equals(inputs[0])) {
            return new ScheduleElement(DateTime.parseDateString(inputs[1]));
        } else {
            return new ScheduleElement(DateTime.parseDateString(inputs[1]), DateTime.parseDateString(inputs[3]));
        }
    }

    public DateTime getDate() {
        return date;
    }
    public DateTime getStartDate() {
        return startDate;
    }
    public DateTime getEndDate() {
        return endDate;
    }
    public TimeExpression getTe() {
        return te;
    }
    public boolean isEvent() {
        return date == null;
    }
    public boolean isDeadLine() {
        return date != null;
    }
    @Override
    public boolean includes(DateTime date) {
        return te.includes(date);
    }
    
    public static ScheduleElement makeEvent(DateTime startDate, DateTime endDate) {
        return new ScheduleElement(startDate, endDate);
    }
    
    public static ScheduleElement makeDeadline(DateTime date) {
        return new ScheduleElement(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleElement // instanceof handles nulls
                        && this.date == ((ScheduleElement) other).getDate()
                        && this.startDate == ((ScheduleElement) other).getStartDate()
                        && this.endDate == ((ScheduleElement) other).getEndDate()
                        && this.te == ((ScheduleElement) other).getTe());// state check
    }
    
    @Override
    public String toString() {
        if (date == null) {
            return " From: " + this.startDate + " To: " + this.endDate;
        } else {
            return " By " + this.date;
        }
    }
    
}
