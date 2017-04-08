package seedu.typed.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.model.task.DateTime;

public class ScheduleElementTest {

    private DateTime christmas = DateTime.getDateTime(2017, Month.DECEMBER, 25, 0, 0);
    private DateTime firstApril = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
    private ScheduleElement testDeadline = new ScheduleElement(firstApril);
    private ScheduleElement testEvent = new ScheduleElement(firstApril, christmas);


    @Test
    public void isDeadline_deadline_true() {
        assertTrue(testDeadline.isDeadline());
    }
    @Test
    public void isDeadline_event_false() {
        assertFalse(testEvent.isDeadline());
    }
    @Test
    public void isEvent_event_true() {
        assertTrue(testEvent.isEvent());
    }
    @Test
    public void isFloating_event_false() {
        assertFalse(testEvent.isFloating());
    }
}
