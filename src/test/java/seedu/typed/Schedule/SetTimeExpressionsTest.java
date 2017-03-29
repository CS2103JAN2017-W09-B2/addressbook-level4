package seedu.typed.Schedule;

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
    private DayInMonthTE everyFirstWed = new DayInMonthTE(1, 3);
    private DayInMonthTE everyLastWed = new DayInMonthTE(-1, 3);
    //private DayInMonthTE everyLastTues = new DayInMonthTE(-1, 2);
    //private DayInMonthTE everyLastThurs = new DayInMonthTE(-1, 4);
    //private DayInMonthTE everyFifthMon = new DayInMonthTE(5, 1);
    //private DayInMonthTE everySecondSat = new DayInMonthTE(2, 6);
    private DayInMonthTE everyLastSun = new DayInMonthTE(-1, 7);
    
    // Generic RangeEachYearTE Range of Months
    private RangeEachYearTE marchToJune = new RangeEachYearTE(3, 6);
    private RangeEachYearTE julyToDec = new RangeEachYearTE(7, 12);
    
    // Generic RangeEachYearTE Month Specific 
    private RangeEachYearTE jan = new RangeEachYearTE(1);
    private RangeEachYearTE feb = new RangeEachYearTE(2);
    private RangeEachYearTE mar = new RangeEachYearTE(3);
    //private RangeEachYearTE apr = new RangeEachYearTE(4);
    //private RangeEachYearTE may = new RangeEachYearTE(5);
    private RangeEachYearTE jun = new RangeEachYearTE(6);
    //private RangeEachYearTE jul = new RangeEachYearTE(7);
    //private RangeEachYearTE aug = new RangeEachYearTE(8);
    //private RangeEachYearTE sep = new RangeEachYearTE(9);
    //private RangeEachYearTE oct = new RangeEachYearTE(10);
    //private RangeEachYearTE nov = new RangeEachYearTE(11);
    //private RangeEachYearTE dec = new RangeEachYearTE(12);
    
    // Test Cases for UnionTE @ testing logical OR
    private UnionTE everyLastSunOrJulyToDec = new UnionTE(everyLastSun, julyToDec);
    //private UnionTE everyFirstWedOrEveryLastWed = new UnionTE(everyFirstWed, everyLastWed);
    
    // Unit Testing for unionTE methods
    @Test
    public void unionTEIncludes_210717_everyLastSunOrJulyToDec_true() {
        DateTime testDate = DateTime.getDateTime(2017, Month.JULY, 21, 0, 0);
        assertTrue(everyLastSunOrJulyToDec.includes(testDate));
    }
    @Test
    public void unionTEIncludes_260317_everyLastSunOrJulyToDec_true() {
        DateTime testDate = DateTime.getDateTime(2017, Month.MARCH, 26, 0, 0);
        assertTrue(this.everyLastSunOrJulyToDec.includes(testDate));
    }
    @Test
    public void unionTEIncludes_AprilFoolDay_everyLastSunOrJulyToDec_false() {
        assertFalse(this.everyLastSunOrJulyToDec.includes(aprilFoolDay));
    }
    @Test
    public void unionTEIncludes_eitherJanOrFebOrMar_true() {
        UnionTE testTe = new UnionTE(this.jan, this.feb, this.mar);
        DateTime testDate1 = DateTime.getDateTime(2017, Month.JANUARY, 26, 0, 0);
        DateTime testDate2 = DateTime.getDateTime(2017, Month.FEBRUARY, 26, 0, 0);
        DateTime testDate3 = DateTime.getDateTime(2017, Month.MARCH, 26, 0, 0);
        DateTime testDate4 = DateTime.getDateTime(2017, Month.APRIL, 26, 0, 0);
        assertTrue(testTe.includes(testDate1));
        assertTrue(testTe.includes(testDate2));
        assertTrue(testTe.includes(testDate3));
        assertFalse(testTe.includes(testDate4));
    }
    
    // Unit Testing for IntersectionTE methods
    @Test
    public void intersectionTEIncludes_everyFirstWedAndMarchToJune() {
        // This should check for every first wednesday from mar to jun
        // First Wed in March 2017
        DateTime testDate1 = DateTime.getDateTime(2017, Month.MARCH, 1, 0, 0);
        // First Wed in May 2017
        DateTime testDate2 = DateTime.getDateTime(2017, Month.MAY, 3, 0, 0);
        // Second Wed in March 2017
        DateTime testDate3 = DateTime.getDateTime(2017, Month.MARCH, 8, 0, 0);
        // Monday
        DateTime testDate4 = DateTime.getDateTime(2017, Month.APRIL, 3, 0, 0);
        IntersectionTE testTe = new IntersectionTE(this.everyFirstWed, this.marchToJune);
        assertTrue(testTe.includes(testDate1));
        assertTrue(testTe.includes(testDate2));
        assertFalse(testTe.includes(testDate3));
        assertFalse(testTe.includes(testDate4));
    }
    @Test
    public void intersectionTEIncludes_janfebmar() {
        // Testing the intersection of 3 months
        // logically a date can't fall on all 3 months so the intersection is empty
        DateTime testDate1 = DateTime.getDateTime(2017, Month.MARCH, 1, 0, 0);
        DateTime testDate2 = DateTime.getDateTime(2017, Month.JANUARY, 3, 0, 0);
        IntersectionTE testTe = new IntersectionTE(this.jan, this.feb, this.mar);
        assertFalse(testTe.includes(testDate2));
        assertFalse(testTe.includes(testDate1));
    }
    
    // Testing for DifferenceTE
    @Test
    public void differenceTEIncludes_everyLastWedExceptJun() {
        // Testing every last wednesday except on June
        DifferenceTE testTe = new DifferenceTE(this.everyLastWed, this.jun);
        // Last Wed in March 2017
        DateTime testDate1 = DateTime.getDateTime(2017, Month.MARCH, 29, 0, 0);
        // Last Wed in May 2017
        DateTime testDate2 = DateTime.getDateTime(2017, Month.MAY, 31, 0, 0);
        // Last Wed in June 2017
        DateTime testDate3 = DateTime.getDateTime(2017, Month.JUNE, 28, 0, 0);
        // Any Date in June 2017
        DateTime testDate4 = DateTime.getDateTime(2017, Month.JUNE, 1, 0, 0);
        
        assertTrue(testTe.includes(testDate1));
        assertTrue(testTe.includes(testDate2));
        assertFalse(testTe.includes(testDate3));
        assertFalse(testTe.includes(testDate4));
    }
}
