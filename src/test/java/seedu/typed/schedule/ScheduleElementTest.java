package seedu.typed.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.task.DateTime;
//@@author A0139379M
/**
 * Unit Test for ScheduleElement
 * which indirectly verifies the validity of Recurrence class
 * @author YIM CHIA HUI
 *
 */
public class ScheduleElementTest {

    private DateTime firstApril = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
    private DateTime firstApril2pm = DateTime.getDateTime(2017, Month.APRIL, 1, 14, 0);
    private DateTime firstApril4pm = DateTime.getDateTime(2017, Month.APRIL, 1, 16, 0);
    private DateTime secondApril2pm = DateTime.getDateTime(2017, Month.APRIL, 2, 14, 0);
    private DateTime secondApril4pm = DateTime.getDateTime(2017, Month.APRIL, 2, 16, 0);
    private DateTime secondApril = DateTime.getDateTime(2017, Month.APRIL, 2, 0, 0);
    private DateTime thirdApril = DateTime.getDateTime(2017, Month.APRIL, 3, 0, 0);
    private DateTime eightApril = DateTime.getDateTime(2017, Month.APRIL, 8, 0, 0);
    private DateTime nineApril = DateTime.getDateTime(2017, Month.APRIL, 9, 0, 0);
    private DateTime firstMay = DateTime.getDateTime(2017, Month.MAY, 1, 0, 0);
    private DateTime secondMay = DateTime.getDateTime(2017, Month.MAY, 2, 0, 0);
    private DateTime firstApril2018 = DateTime.getDateTime(2018, Month.APRIL, 1, 0, 0);
    private DateTime secondApril2018 = DateTime.getDateTime(2018, Month.APRIL, 2, 0, 0);

    private ScheduleElement testFloating;

    private ScheduleElement testDeadline;
    private ScheduleElement testDeadlineRecurDaily;
    private ScheduleElement testDeadlineRecurWeekly;
    private ScheduleElement testDeadlineRecurMonthly;
    private ScheduleElement testDeadlineRecurYearly;

    private ScheduleElement testEvent;
    private ScheduleElement testEventRecurDaily;
    private ScheduleElement testEventRecurWeekly;
    private ScheduleElement testEventRecurMonthly;
    private ScheduleElement testEventRecurYearly;

    private ScheduleElement testEveryMonday;
    private ScheduleElement testEverySaturday;

    @Before
    public void setUp() {
        try {
            testFloating = new ScheduleElement();

            testDeadline = new ScheduleElement(firstApril);
            testDeadlineRecurDaily = new ScheduleElement(firstApril, "day");
            testDeadlineRecurWeekly = new ScheduleElement(firstApril, "week");
            testDeadlineRecurMonthly = new ScheduleElement(firstApril, "month");
            testDeadlineRecurYearly = new ScheduleElement(firstApril, "year");

            testEvent = new ScheduleElement(firstApril, secondApril);
            testEventRecurDaily = new ScheduleElement(firstApril2pm, firstApril4pm, "day");
            testEventRecurWeekly = new ScheduleElement(firstApril, secondApril, "week");
            testEventRecurMonthly = new ScheduleElement(firstApril, secondApril, "month");
            testEventRecurYearly = new ScheduleElement(firstApril, secondApril, "year");

            testEveryMonday = new ScheduleElement("monday");
            testEverySaturday = new ScheduleElement("saturday");

        } catch (IllegalValueException e) {
            // no exception is expected
            e.printStackTrace();
        }
    }

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

    /**
     * Floating Task Testing
     */

    @Test
    public void isRecurring_floating_false() {
        assertFalse(testFloating.isRecurring());
    }

    @Test
    public void isFloating_floating_true() {
        assertTrue(testFloating.isFloating());
    }

    @Test
    public void isDeadline_floating_false() {
        assertFalse(testFloating.isDeadline());
    }

    @Test
    public void isEvent_floating_false() {
        assertFalse(testFloating.isEvent());
    }

    @Test
    public void nextOccurrence_floating_null() {
        assertEquals(testFloating.nextOccurrence(DateTime.getToday()), null);
    }

    @Test
    public void includes_floating_false() {
        assertFalse(testFloating.includes(firstApril));
    }

    @Test
    public void updateDate_floating_null() {
        assertEquals(testFloating.updateDate(), null);
    }
    /**
     * Deadlines Testing : non-recurring deadlines, recurring daily, weekly, monthly and yearly
     */

    @Test
    public void isRecurring_testDeadline_false() {
        assertFalse(testDeadline.isRecurring());
    }
    @Test
    public void isRecurring_testDeadlineRecurDaily_true() {
        assertTrue(testDeadlineRecurDaily.isRecurring());
    }

    @Test
    public void isFloating_testDeadline_false() {
        assertFalse(testDeadline.isFloating());
    }

    @Test
    public void isDeadline_testDeadline_true() {
        assertTrue(testDeadline.isDeadline());
    }

    @Test
    public void isDeadline_testDeadlineRecurDaily_true() {
        assertTrue(testDeadlineRecurDaily.isDeadline());
    }

    @Test
    public void isEvent_testDeadline_false() {
        assertFalse(testDeadline.isEvent());
    }


    @Test
    public void nextOccurrence_testDeadline_null() {
        assertEquals(testDeadline.nextOccurrence(DateTime.getToday()), null);
    }

    @Test
    public void nextOccurrence_testDeadlineRecurDaily_secondApril() {
        assertTrue(testDeadlineRecurDaily.nextOccurrence(firstApril).equals(secondApril));
    }

    @Test
    public void nextOccurrence_testDeadlineRecurWeekly_eightApril() {
        assertTrue(testDeadlineRecurWeekly.nextOccurrence(firstApril).equals(eightApril));
    }

    @Test
    public void nextOccurrence_testDeadlineRecurMonthly_firstMay() {
        assertTrue(testDeadlineRecurMonthly.nextOccurrence(firstApril).equals(firstMay));
    }

    @Test
    public void nextOccurrence_testDeadlineRecurYearly_firstApril2018() {
        assertTrue(testDeadlineRecurYearly.nextOccurrence(firstApril).equals(firstApril2018));
    }

    /**
     * includes should includes the dates that fall within the recurrence rule
     */
    @Test
    public void includes_testDeadline_false() {
        assertFalse(testDeadline.includes(firstApril));
    }

    @Test
    public void includes_testDeadlineRecurDaily_true() {
        assertTrue(testDeadlineRecurDaily.includes(firstApril));
        assertTrue(testDeadlineRecurDaily.includes(secondApril));
        assertTrue(testDeadlineRecurDaily.includes(eightApril));
    }

    @Test
    public void includes_testDeadlineRecurWeekly_true() {
        assertTrue(testDeadlineRecurWeekly.includes(firstApril));
        assertFalse(testDeadlineRecurWeekly.includes(secondApril));
        assertTrue(testDeadlineRecurWeekly.includes(eightApril));
    }

    @Test
    public void includes_testDeadlineRecurMonthly_true() {
        assertTrue(testDeadlineRecurMonthly.includes(firstApril));
        assertFalse(testDeadlineRecurMonthly.includes(secondApril));
        assertFalse(testDeadlineRecurMonthly.includes(eightApril));
        assertTrue(testDeadlineRecurMonthly.includes(firstMay));
    }

    @Test
    public void includes_testDeadlineRecurYearly_true() {
        assertTrue(testDeadlineRecurYearly.includes(firstApril));
        assertFalse(testDeadlineRecurYearly.includes(secondApril));
        assertFalse(testDeadlineRecurYearly.includes(eightApril));
        assertTrue(testDeadlineRecurYearly.includes(firstApril2018));
    }

    /**
     * updateDate should return the next occurrence of the recurring deadlines
     */

    @Test
    public void updateDate_testDeadlineRecurDaily_secondApril() {
        assertTrue(testDeadlineRecurDaily.updateDate().getDate().equals(secondApril));
    }

    @Test
    public void updateDate_testDeadlineRecurWeekly_eightApril() {
        assertTrue(testDeadlineRecurWeekly.updateDate().getDate().equals(eightApril));
    }

    @Test
    public void updateDate_testDeadlineRecurMonthly_firstMay() {
        assertTrue(testDeadlineRecurMonthly.updateDate().getDate().equals(firstMay));
    }

    @Test
    public void updateDate_testDeadlineRecurYearly_firstApril2018() {
        assertTrue(testDeadlineRecurYearly.updateDate().getDate().equals(firstApril2018));
    }
    /**
     * Events Testing : non-recurring events, recurring daily, weekly, monthly and yearly events
     */


    /**
     * nextOccurrence should return the next occurrence of the recurrence
     */

    @Test
    public void nextOccurrence_testEvent_null() {
        assertEquals(testEvent.nextOccurrence(DateTime.getToday()), null);
    }

    @Test
    public void nextOccurrence_testEventRecurDaily_secondApril2pm() {
        DateTime endDate = testEventRecurDaily.getEndDate();
        assertTrue(testEventRecurDaily.nextOccurrence(endDate).equals(secondApril2pm));
    }

    @Test
    public void nextOccurrence_testEventRecurWeekly_eightApril() {
        DateTime endDate = testEventRecurWeekly.getEndDate();
        assertTrue(testEventRecurWeekly.nextOccurrence(endDate).equals(eightApril));
    }

    @Test
    public void nextOccurrence_testEventRecurMonthly_firstMay() {
        DateTime endDate = testEventRecurMonthly.getEndDate();
        assertTrue(testEventRecurMonthly.nextOccurrence(endDate).equals(firstMay));
    }

    @Test
    public void nextOccurrence_testEventRecurYearly_firstApril2018() {
        DateTime endDate = testEventRecurYearly.getEndDate();
        assertTrue(testEventRecurYearly.nextOccurrence(endDate).equals(firstApril2018));
    }

    /**
     * includes should includes the dates that fall within the recurrence rule
     * In particular, includes should only work for recurring tasks
     */
    @Test
    public void includes_testEvent_false() {
        // not recurring
        assertFalse(testEvent.includes(firstApril));
    }

    @Test
    public void includes_testEventRecurDaily_true() {
        assertTrue(testEventRecurDaily.includes(firstApril));
        assertTrue(testEventRecurDaily.includes(secondApril));
        assertTrue(testEventRecurDaily.includes(eightApril));
    }

    @Test
    public void includes_testEventRecurWeekly_true() {
        assertTrue(testEventRecurWeekly.includes(firstApril));
        // the event last between 1st april to 2nd april hence it should include second april
        assertTrue(testEventRecurWeekly.includes(secondApril));
        assertFalse(testEventRecurWeekly.includes(thirdApril));
        assertTrue(testEventRecurWeekly.includes(eightApril));
    }

    @Test
    public void includes_testEventRecurMonthly_true() {
        assertTrue(testEventRecurMonthly.includes(firstApril));
        assertTrue(testEventRecurMonthly.includes(secondApril));
        assertFalse(testEventRecurMonthly.includes(eightApril));
        assertTrue(testEventRecurMonthly.includes(firstMay));
    }

    @Test
    public void includes_testEventRecurYearly_true() {
        assertTrue(testEventRecurYearly.includes(firstApril));
        assertTrue(testEventRecurYearly.includes(secondApril));
        assertFalse(testEventRecurYearly.includes(eightApril));
        assertTrue(testEventRecurYearly.includes(firstApril2018));
    }

    /**
     * updateDate should return the next occurrence of the recurring deadlines
     * For events, both startDate and endDate needs to be verified
     */

    @Test
    public void updateDate_testEventRecurDaily_secondApril2pmTosecondApril4pm() {
        assertTrue(testEventRecurDaily.updateDate().getStartDate().equals(secondApril2pm));
        assertTrue(testEventRecurDaily.updateDate().getEndDate().equals(secondApril4pm));
    }

    @Test
    public void updateDate_testEventRecurWeekly_eightAprilToNineApril() {
        assertTrue(testEventRecurWeekly.updateDate().getStartDate().equals(eightApril));
        assertTrue(testEventRecurWeekly.updateDate().getEndDate().equals(nineApril));
    }

    @Test
    public void updateDate_testEventRecurMonthly_firstMayToSecondMay() {
        assertTrue(testEventRecurMonthly.updateDate().getStartDate().equals(firstMay));
        assertTrue(testEventRecurMonthly.updateDate().getEndDate().equals(secondMay));
    }

    @Test
    public void updateDate_testEventRecurYearly_firstApril2018() {
        assertTrue(testEventRecurYearly.updateDate().getStartDate().equals(firstApril2018));
        assertTrue(testEventRecurYearly.updateDate().getEndDate().equals(secondApril2018));
    }

    /**
     * As overdue method is time sensitive, it is strictly overdue if the today's date is after
     * the date (for deadline) or end date (for events). Since today always change, we just
     * test if it's overdue if today is after the date
     */

    @Test
    public void isOverdue_floating_false() {
        assertFalse(testFloating.isOverdue());
    }

    @Test
    public void isOverdue_testDeadline() {
        DateTime today = DateTime.getToday();
        assertEquals(testDeadline.isOverdue(), today.isAfter(testDeadline.getDate()));
    }

    @Test
    public void isOverdue_testEvent() {
        DateTime today = DateTime.getToday();
        assertEquals(testEvent.isOverdue(), today.isAfter(testEvent.getEndDate()));
    }

    /**
     * Testing for special add commands like add task every monday (with no date specified)
     * includes test format : includes_SCHEDULEELEMENT_DATE_OUTCOME
     */
    @Test
    public void includes_testEveryMonday_firstApril_false() {
        assertFalse(testEveryMonday.includes(firstApril));
    }
    @Test
    public void includes_testEveryMonday_thirdApril_true() {
        assertTrue(testEveryMonday.includes(thirdApril));
    }
    @Test
    public void includes_testEverySaturday_firstApril_true() {
        assertTrue(testEverySaturday.includes(firstApril));
    }
    @Test
    public void includes_testEverySaturday_secondApril_false() {
        assertFalse(testEverySaturday.includes(secondApril));
    }

    /**
     * NextOccurrence format : nextOccurrence_SCHEDULEELEMENT_DATE_OUTCOME
     * As it is very time sensitive, every time we create a "every saturday"
     * it will be based on the current time now (which is different every time)
     */
    @Test
    public void nextOccurrence_testEverySaturday_7dayslaterFromItsDate() {
        DateTime date = testEverySaturday.getDate();
        assertTrue(testEverySaturday.nextOccurrence(date).equals(date.nextDays(7)));
    }
    @Test
    public void nextOccurrence_testEveryMonday_firstApril_7dayslaterFromItsDate() {
        DateTime date = testEveryMonday.getDate();
        assertTrue(testEveryMonday.nextOccurrence(date).equals(date.nextDays(7)));
    }
}
