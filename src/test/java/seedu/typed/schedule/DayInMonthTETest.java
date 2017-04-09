package seedu.typed.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
public class DayInMonthTETest {

    // Boundary Cases for Last Tuesday
    // Last Tuesday of March 2017
    private DateTime testDate280317 = DateTime.getDateTime(2017, Month.MARCH, 28, 0, 0);
    // Last Tuesday of April 2017
    private DateTime testDate250417 = DateTime.getDateTime(2017, Month.APRIL, 25, 0, 0);
    // Last Tuesday of April 2018
    private DateTime testDate240418 = DateTime.getDateTime(2018, Month.APRIL, 24, 0, 0);
    // Second Last Tuesday of March 2017
    private DateTime testDate210317 = DateTime.getDateTime(2017, Month.MARCH, 21, 0, 0);

    // Boundary Cases for First Monday
    // First Monday of March 2017
    private DateTime firstMondayOfMarch = DateTime.getDateTime(2017, Month.MARCH, 06, 0, 0);
    // Second Monday of March 2017
    private DateTime secondMondayOfMarch = DateTime.getDateTime(2017, Month.MARCH, 13, 0, 0);
    // First Monday of April 2017
    private DateTime firstMondayOfApril = DateTime.getDateTime(2017, Month.APRIL, 03, 0, 0);
    // First Monday of April 2018
    private DateTime testDate020418 = DateTime.getDateTime(2018, Month.APRIL, 02, 0, 0);
    // Second Monday of April 20172017
    private DateTime testDate100417 = DateTime.getDateTime(2017, Month.APRIL, 10, 0, 0);
    // First Monday of Jan 2018
    private DateTime firstMondayOfJan2018 = DateTime.getDateTime(2018, Month.JANUARY, 01, 0, 0);
    // First Monday of Dec 2017
    private DateTime firstMondayOfDec2017 = DateTime.getDateTime(2017, Month.DECEMBER, 04, 0, 0);
    // First Tuesday of March 2017
    private DateTime firstTuesdayOfMarch = DateTime.getDateTime(2017, Month.MARCH, 07, 0, 0);
    // Second Tuesday of March 2017
    private DateTime secondTuesdayOfMarch = DateTime.getDateTime(2017, Month.MARCH, 14, 0, 0);
    // First Wednesday of April 2017
    private DateTime firstWednesdayOfApril = DateTime.getDateTime(2017, Month.APRIL, 05, 0, 0);
    // Second Sunday of March 2017
    private DateTime secondSundayOfMarch = DateTime.getDateTime(2017, Month.MARCH, 12, 0, 0);
    // First Sunday of April 2017
    private DateTime firstSundayOfApril = DateTime.getDateTime(2017, Month.APRIL, 02, 0, 0);

    // Some Edge Cases for Last Monday
    // Last Monday of April 2018, in particular that April has 5 Mondays
    // In particular, it should also match the 5th week Monday
    private DateTime testDate300418 = DateTime.getDateTime(2018, Month.APRIL, 30, 0, 0);

    private DayInMonthTE everyFirstMonday = new DayInMonthTE(1, 1);
    private DayInMonthTE everyLastTuesday = new DayInMonthTE(-1, 2);
    private DayInMonthTE everyLastMonday = new DayInMonthTE(-1, 1);
    private DayInMonthTE everyFifthMonday = new DayInMonthTE(5, 1);
    private DayInMonthTE everyFirstTuesday = new DayInMonthTE(1, 2);
    private DayInMonthTE everySecondMonday = new DayInMonthTE(2, 1);
    private DayInMonthTE everySecondTuesday = new DayInMonthTE(2, 2);
    private DayInMonthTE everyFirstWednesday = new DayInMonthTE(1, 3);
    private DayInMonthTE everyFirstSunday = new DayInMonthTE(1, 7);


    private TimeExpression firstWeek = DayInMonthTE.week(1);
    private TimeExpression everyMonday = DayInMonthTE.weekly(1);
    private TimeExpression everyTuesday = DayInMonthTE.weekly(2);
    private TimeExpression firstMondayEveryMonth = DayInMonthTE.monthly(1, 1);

    // Testing involves forming a particular TimeExpression like everyFifthMonday and the input 300418
    // includes method is very important to work for all cases as every time expression will depends
    // on it to get the correct recurrence
    // =========== Comprehensive Testing for includes ==============
    // =============================================================
    @Test
    public void includes_300418_everyFifthMonday_true() {
        assertTrue(everyFifthMonday.includes(testDate300418));
    }
    @Test
    public void includes_300418_everyLastMonday_true() {
        assertTrue(everyLastMonday.includes(testDate300418));
    }
    @Test
    public void includes_280317_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate280317));
    }
    @Test
    public void includes_250417_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate250417));
    }
    @Test
    public void includes_240418_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate240418));
    }
    @Test
    public void includes_210317_everyLastTuesday_false() {
        assertFalse(everyLastTuesday.includes(testDate210317));
    }
    @Test
    public void includes_firstMondayOfMarch_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(firstMondayOfMarch));
    }
    @Test
    public void includes_030417_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(firstMondayOfApril));
    }
    @Test
    public void includes_020418_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate020418));
    }
    @Test
    public void includes_100417_everyFirstMonday_true() {
        assertFalse(everyFirstMonday.includes(testDate100417));
    }


    // =========== Testing nextDeadlineOccurrence(DateTime) ========
    // =============================================================
    // Another very important method to test is nextDeadlineOccurrence
    // we need to ensure that the DateTime returned is the correct and accurate
    // representation of the next occurrence given the particular recurrence rule

    // Testing methods are named in this format
    // nextDeadlineOccurrence_<DATE>In<RECURRENCE>_OUTCOME
    // where next occurrence will be after DATE
    // Testing various conditions like next occurrence is next year, next month, same month, different weeks etc

    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEveryFirstMonday_firstMondayOfApril() {
        assertTrue(everyFirstMonday.nextOccurrence(firstMondayOfMarch).equals(firstMondayOfApril));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfDec2017InEveryFirstMonday_firstMondayOfJan2018() {
        assertTrue(everyFirstMonday.nextOccurrence(firstMondayOfDec2017).equals(firstMondayOfJan2018));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEveryFirstTuesday_firstTuesdayOfMarch() {
        assertTrue(everyFirstTuesday.nextOccurrence(firstMondayOfMarch).equals(firstTuesdayOfMarch));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEverySecondMonday_secondMondayOfMarch() {
        assertTrue(everySecondMonday.nextOccurrence(firstMondayOfMarch).equals(secondMondayOfMarch));
    }
    @Test
    public void nextDeadlineOccurrence_firstTuesdayOfMarchInEverySecondMonday_secondMondayOfMarch() {
        assertTrue(everySecondMonday.nextOccurrence(firstTuesdayOfMarch).equals(secondMondayOfMarch));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEverySecondTuesday_secondTuesdayOfMarch() {
        assertTrue(everySecondTuesday.nextOccurrence(firstMondayOfMarch).equals(secondTuesdayOfMarch));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEveryFirstWednesday_firstWednesdayOfApril() {
        assertTrue(everyFirstWednesday.nextOccurrence(firstMondayOfMarch).equals(firstWednesdayOfApril));
    }
    @Test
    public void nextDeadlineOccurrence_secondSundayOfMarchInEveryFirstSunday_firstSundayOfApril() {
        assertTrue(everyFirstSunday.nextOccurrence(secondSundayOfMarch).equals(firstSundayOfApril));
    }
    @Test
    public void nextDeadlineOccurrence_firstMondayOfMarchInEveryFirstSunday_firstSundayOfApril() {
        assertTrue(everyFirstSunday.nextOccurrence(firstMondayOfMarch).equals(firstSundayOfApril));
    }


    // =========== Testing various recurrence constructs ===========
    // =============================================================
    // Recurrence constructs are shortcuts to give us TimeExpression that
    // recurs according to common recurring rules like weekly, or the whole week
    // To test this, we will use include to verify that the date falls within the recurrence

    // week tests
    @Test
    public void week_firstMondayOfMarchInFirstWeek_true() {
        assertTrue(firstWeek.includes(firstMondayOfMarch));
    }
    @Test
    public void week_firstTuesdayOfMarchInFirstWeek_true() {
        assertTrue(firstWeek.includes(firstTuesdayOfMarch));
    }
    @Test
    public void week_secondTuesdayOfMarchInFirstWeek_false() {
        assertFalse(firstWeek.includes(secondTuesdayOfMarch));
    }

    // weekly tests
    @Test
    public void weekly_secondMondayOfMarchInEveryMonday_true() {
        assertTrue(everyMonday.includes(secondMondayOfMarch));
    }
    @Test
    public void weekly_firstMondayOfAprilInEveryMonday_true() {
        assertTrue(everyMonday.includes(firstMondayOfApril));
    }
    @Test
    public void weekly_firstTuesdayOfMarchInEveryMonday_false() {
        assertFalse(everyMonday.includes(firstTuesdayOfMarch));
    }
    @Test
    public void weekly_firstMondayOfJan2018InEveryMonday_true() {
        assertTrue(everyMonday.includes(firstMondayOfJan2018));
    }
    @Test
    public void weekly_firstTuesdayOfMarchInEveryTuesday_true() {
        assertTrue(everyTuesday.includes(firstTuesdayOfMarch));
    }

    // monthly tests
    // In particular, recur a given date every month
    @Test
    public void monthly_firstMonday_recursEveryFirstMonday() {
        assertTrue(firstMondayEveryMonth.includes(firstMondayOfApril));
        assertTrue(firstMondayEveryMonth.includes(firstMondayOfMarch));
        assertTrue(firstMondayEveryMonth.includes(firstMondayOfJan2018));
        assertFalse(firstMondayEveryMonth.includes(firstTuesdayOfMarch));
    }

    @Test
    public void isLeapYear_2000_true() {
        assertTrue(DayInMonthTE.isLeapYear(2000));
    }
    @Test
    public void isLeapYear_1900_true() {
        assertFalse(DayInMonthTE.isLeapYear(1900));
    }
}
