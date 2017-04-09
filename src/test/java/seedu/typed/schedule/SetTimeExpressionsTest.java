package seedu.typed.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.model.task.DateTime;

//@@author A0139379M
/**
 * Unit Testing for Set TimeExpression
 * which includes UnionTE, IntersectionTE and DifferenceTE
 * @author YIM CHIA HUI
 *
 */
public class SetTimeExpressionsTest {

    private DateTime aprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);

    // Generic DayInMonth
    private DayInMonthTE everyFirstMon = new DayInMonthTE(1, 1);
    private DayInMonthTE secondMon = new DayInMonthTE(2, 1);
    private DayInMonthTE thirdMon = new DayInMonthTE(3, 1);
    private DayInMonthTE fourthMon = new DayInMonthTE(4, 1);
    private DayInMonthTE fifthMon = new DayInMonthTE(5, 1);
    private DayInMonthTE everyFirstWed = new DayInMonthTE(1, 3);
    private DayInMonthTE everyFirstSun = new DayInMonthTE(1, 7);
    private DayInMonthTE everyLastWed = new DayInMonthTE(-1, 3);
    private DayInMonthTE everyLastSun = new DayInMonthTE(-1, 7);

    // Generic RangeEachYearTE Range of Months
    private RangeEachYearTE marchToJune = new RangeEachYearTE(3, 6);
    private RangeEachYearTE julyToDec = new RangeEachYearTE(7, 12);

    // Generic RangeEachYearTE Month Specific
    private RangeEachYearTE jan = new RangeEachYearTE(1);
    private RangeEachYearTE feb = new RangeEachYearTE(2);
    private RangeEachYearTE mar = new RangeEachYearTE(3);
    private RangeEachYearTE jun = new RangeEachYearTE(6);

    // Test Cases for UnionTE @ testing logical OR
    private UnionTE everyLastSunOrJulyToDec = new UnionTE(everyLastSun, julyToDec);

    // Test Case for UnionTE nextDeadlineOccurrence
    private UnionTE firstWedOrFirstSun = new UnionTE(everyFirstWed, everyFirstSun);
    private UnionTE everyMonday = new UnionTE(everyFirstMon, secondMon, thirdMon, fourthMon, fifthMon);
    private DateTime firstMonInMar = DateTime.getDateTime(2017, Month.MARCH, 06, 0, 0);
    private DateTime firstThurs = DateTime.getDateTime(2017, Month.MARCH, 02, 0, 0);
    private DateTime firstSun = DateTime.getDateTime(2017, Month.MARCH, 05, 0, 0);
    private DateTime secondSun = DateTime.getDateTime(2017, Month.MARCH, 12, 0, 0);
    private DateTime firstWedInApr = DateTime.getDateTime(2017, Month.APRIL, 05, 0, 0);
    private DateTime nextFirstSun = DateTime.getDateTime(2017, Month.APRIL, 02, 0, 0);
    // First Wednesday in March 2017
    private DateTime firstWedInMar = DateTime.getDateTime(2017, Month.MARCH, 1, 0, 0);
    // First Monday in February 2017
    private DateTime firstMonInFeb = DateTime.getDateTime(2017, Month.MARCH, 1, 0, 0);
    // Last Wednesday in March 2017
    private DateTime lastWedInMar = DateTime.getDateTime(2017, Month.MARCH, 29, 0, 0);
    // First Monday in June 2017
    private DateTime firstMonInJun = DateTime.getDateTime(2017, Month.JUNE, 05, 0, 0);
    // 26 January 2017
    private DateTime jan26 = DateTime.getDateTime(2017, Month.JANUARY, 26, 0, 0);
    // 26 February 2017
    private DateTime feb26 = DateTime.getDateTime(2017, Month.FEBRUARY, 26, 0, 0);
    // 26 March 2017
    private DateTime mar26 = DateTime.getDateTime(2017, Month.MARCH, 26, 0, 0);
    // 26 April 2017
    private DateTime apr26 = DateTime.getDateTime(2017, Month.APRIL, 26, 0, 0);
    // Test cases for 5 mondays to test if everyMonday works correctly
    private DateTime apr3 = DateTime.getDateTime(2017, Month.APRIL, 3, 0, 0);
    private DateTime apr10 = DateTime.getDateTime(2017, Month.APRIL, 10, 0, 0);
    private DateTime apr17 = DateTime.getDateTime(2017, Month.APRIL, 17, 0, 0);
    private DateTime apr24 = DateTime.getDateTime(2017, Month.APRIL, 24, 0, 0);
    private DateTime may1 = DateTime.getDateTime(2017, Month.MAY, 1, 0, 0);

    private UnionTE eitherJanOrFebOrMar = new UnionTE(jan, feb, mar);

    // Test Case for DifferenceTE nextDeadlineOccurrence
    private DifferenceTE firstWedExceptFirstMon = new DifferenceTE(everyFirstWed, everyFirstMon);
    // Every last Wednesday except June
    private DifferenceTE lastWedExceptJun = new DifferenceTE(everyLastWed, jun);

    private IntersectionTE firstWedAndMarToJun = new IntersectionTE(everyFirstWed, marchToJune);

    @Test
    public void includes_AprilFoolDay_everyLastSunOrJulyToDec_false() {
        assertFalse(everyLastSunOrJulyToDec.includes(aprilFoolDay));
    }
    @Test
    public void includes_jan26_eitherJanOrFebOrMar_true() {
        assertTrue(eitherJanOrFebOrMar.includes(jan26));
    }
    @Test
    public void includes_feb26_eitherJanOrFebOrMar_true() {
        assertTrue(eitherJanOrFebOrMar.includes(feb26));
    }
    @Test
    public void includes_mar26_eitherJanOrFebOrMar_true() {
        assertTrue(eitherJanOrFebOrMar.includes(mar26));
    }
    @Test
    public void includes_apr26_eitherJanOrFebOrMar_true() {
        assertFalse(eitherJanOrFebOrMar.includes(apr26));
    }
    @Test
    public void includes_aprilFoolDay_everyMonday_true() {
        assertFalse(everyMonday.includes(aprilFoolDay));
    }
    @Test
    public void includes_apr3_everyMonday_true() {
        assertTrue(everyMonday.includes(apr3));
    }
    @Test
    public void includes_apr10_everyMonday_true() {
        assertTrue(everyMonday.includes(apr10));
    }
    @Test
    public void includes_apr17_everyMonday_true() {
        assertTrue(everyMonday.includes(apr17));
    }
    @Test
    public void includes_apr24_everyMonday_true() {
        assertTrue(everyMonday.includes(apr24));
    }
    @Test
    public void includes_may1_everyMonday_true() {
        assertTrue(everyMonday.includes(may1));
    }

    // Unit Testing for IntersectionTE methods
    @Test
    public void includes_firstWedInMar_firstWedAndMarToJun_true() {
        assertTrue(firstWedAndMarToJun.includes(firstWedInMar));
    }
    @Test
    public void includes_firstMonInFeb_firstWedAndMarToJun_false() {
        assertTrue(firstWedAndMarToJun.includes(firstMonInFeb));
    }

    // Testing for DifferenceTE
    @Test
    public void includes_lastWedInMar_everyLastWedExceptJun_true() {
        assertTrue(lastWedExceptJun.includes(lastWedInMar));
    }
    @Test
    public void includes_firstMonInJun_lastWedExceptJun_false() {
        assertFalse(lastWedExceptJun.includes(firstMonInJun));
    }

    // Testing for nextDeadlineOccurrence in UnionTE
    // need to test it is indeed the earliest next occurrence after DATE within the union of TEs
    // format of test is nextDeadlineOccurrence_DATE_UNIONTE_OUTCOME

    @Test
    public void nextDeadlineOccurrence_firstThurs_firstWedOrFirstSun_firstSun() {
        assertTrue(firstWedOrFirstSun.nextOccurrence(firstThurs).equals(firstSun));
    }
    @Test
    public void nextDeadlineOccurrence_firstMon_firstWedOrFirstSun_nextFirstSun() {
        // in this weird example, first monday is after first wed and first sunday
        // so next occurrence is next first wed
        assertTrue(firstWedOrFirstSun.nextOccurrence(firstMonInMar).equals(nextFirstSun));
    }
    @Test
    public void nextDeadlineOccurrence_secondSun_firstWedOrFirstSun_nextFirstSun() {
        // in this particular weird example, nextFirstSun actually occur first
        assertTrue(firstWedOrFirstSun.nextOccurrence(secondSun).equals(nextFirstSun));
    }

    // ================== DifferenceTE Tests =======================
    // =============================================================

    // Testing of nextDeadlineOccurrence in DifferenceTE

    @Test
    public void nextDeadlineOccurrence_firstMon_firstWedExceptFirstMon_firstWedInApr() {
        // in this particular test case, firstWed of march is before firstMon of March
        assertTrue(firstWedExceptFirstMon.nextOccurrence(firstMonInMar).equals(firstWedInApr));
    }

    // Testing of nextDeadlineOccurrence in IntersectionTE

    @Test
    public void nextDeadlineOccurrence_firstMon_firstWedAndMarToJun_firstWedInApr() {
        // in this particular case, the next occurrence of first wednesday and
        // within march to june is first wed of april
        assertTrue(firstWedAndMarToJun.nextOccurrence(firstMonInMar).equals(firstWedInApr));
    }
}
