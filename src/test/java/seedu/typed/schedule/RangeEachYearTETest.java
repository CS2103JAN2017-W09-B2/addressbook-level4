package seedu.typed.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
public class RangeEachYearTETest {

    // Generic Test Dates
    private DateTime aprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
    private DateTime christmasDay = DateTime.getDateTime(2017, Month.DECEMBER, 25, 0, 0);
    private DateTime goodFridayDay = DateTime.getDateTime(2017, Month.APRIL, 18, 0, 0);
    private DateTime happyNewYear = DateTime.getDateTime(2017, Month.JANUARY, 1, 0, 0);

    // Generic RangeEachYearTE
    private RangeEachYearTE marchToJune = new RangeEachYearTE(3, 6);
    private RangeEachYearTE julyToDec = new RangeEachYearTE(7, 12);
    private RangeEachYearTE jan = new RangeEachYearTE(1);

    // Boundary Cases for goodFridayDay specific
    private RangeEachYearTE april18toOct25 = new RangeEachYearTE(4, 10, 18, 25);
    private RangeEachYearTE jan21toApril17 = new RangeEachYearTE(1, 4, 21, 17);
    private RangeEachYearTE april19toOct25 = new RangeEachYearTE(4, 10, 19, 25);

    private DateTime beforeApril18 = DateTime.getDateTime(2017, Month.APRIL, 17, 0, 0);
    private DateTime afterApril18 = DateTime.getDateTime(2017, Month.APRIL, 19, 0, 0);
    private DateTime afterOct25 = DateTime.getDateTime(2017, Month.OCTOBER, 26, 0, 0);
    private DateTime monthAfterOct25 = DateTime.getDateTime(2017, Month.NOVEMBER, 25, 0, 0);
    private DateTime onOct25 = DateTime.getDateTime(2017, Month.OCTOBER, 25, 0, 0);
    private DateTime beforeApril = DateTime.getDateTime(2017, Month.MARCH, 10, 0, 0);

    private DateTime testDate18042017 = DateTime.getDateTime(2017, Month.APRIL, 18, 0, 0);
    private DateTime testDate18042018 = DateTime.getDateTime(2018, Month.APRIL, 18, 0, 0);
    private DateTime testDate20042017 = DateTime.getDateTime(2017, Month.APRIL, 20, 0, 0);

    // Test format: includes_<DATE>In<TimeExpression>_OUTCOME
    @Test
    public void includes_christmasDayInjulyToDec_true() {
        assertTrue(julyToDec.includes(christmasDay));
    }
    @Test
    public void includes_aprilFoolDayInmarchToJune_true() {
        assertTrue(marchToJune.includes(aprilFoolDay));
    }
    @Test
    public void includes_christmasDayInmarchToJune_false() {
        assertFalse(marchToJune.includes(christmasDay));
    }
    @Test
    public void includes_goodFridayDayInJan_false() {
        assertFalse(jan.includes(goodFridayDay));
    }
    @Test
    public void includes_happyNewYearInJan_true() {
        assertTrue(jan.includes(happyNewYear));
    }
    // Test for Boundary cases
    @Test
    public void includes_aprilFoolDayInApril18toOct25_true() {
        assertTrue(april18toOct25.includes(goodFridayDay));
    }
    @Test
    public void includes_aprilFoolDayInJan21toApril17_false() {
        assertFalse(jan21toApril17.includes(goodFridayDay));
    }
    @Test
    public void includes_aprilFoolDayInApril19toOct25_false() {
        assertFalse(april19toOct25.includes(goodFridayDay));
    }

    // nextDeadlineOccurrence testing in this format
    // nextDeadlineOccurrence_CONDITIONS_OUTCOME
    // Testing using fixed Recurring event april18toOct25
    @Test
    public void nextDeadlineOccurrence_beforeOccurrence_OccurrenceStartDate() {
        assertTrue(april18toOct25.nextOccurrence(beforeApril).equals(testDate18042017));
    }
    @Test
    public void nextDeadlineOccurrence_AfterOccurrence_OccurrenceStartDateNextYear() {
        assertTrue(april18toOct25.nextOccurrence(afterOct25).equals(testDate18042018));
    }
    @Test
    public void nextDeadlineOccurrence_AfterOccurrenceNextMonth_OccurrenceStartDateNextYear() {
        assertTrue(april18toOct25.nextOccurrence(monthAfterOct25).equals(testDate18042018));
    }
    @Test
    public void nextDeadlineOccurrence_WithinOccurrenceBeforeStartDay_OccurrenceStartDate() {
        assertTrue(april18toOct25.nextOccurrence(beforeApril18).equals(testDate18042017));
    }
    @Test
    public void nextDeadlineOccurrence_WithinOccurrenceAfterStartDay_OccurrenceStartDateNextYear() {
        assertTrue(april18toOct25.nextOccurrence(afterApril18).equals(testDate20042017));
    }
    @Test
    public void nextDeadlineOccurrence_WithinOccurrenceEndDay_OccurrenceStartDateNextYear() {
        assertTrue(april18toOct25.nextOccurrence(onOct25).equals(testDate18042018));
    }
}
