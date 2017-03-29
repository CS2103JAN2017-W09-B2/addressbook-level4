package seedu.typed.Schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;

import seedu.typed.model.task.DateTime;
//@@author A0139379M
public class DayInMonthTETest {

    // Boundary Cases for Last Tuesday
    // Last Tuesday of March 2017
    private DateTime testDate_280317 = DateTime.getDateTime(2017, Month.MARCH, 28, 0, 0);
    // Last Tuesday of April 2017
    private DateTime testDate_250417 = DateTime.getDateTime(2017, Month.APRIL, 25, 0, 0);
    // Last Tuesday of April 2018
    private DateTime testDate_240418 = DateTime.getDateTime(2018, Month.APRIL, 24, 0, 0);
    // Second Last Tuesday of March 2017
    private DateTime testDate_210317 = DateTime.getDateTime(2017, Month.MARCH, 21, 0, 0);

    // Boundary Cases for First Monday
    // First Monday of March 2017
    private DateTime testDate_060317 = DateTime.getDateTime(2017, Month.MARCH, 06, 0, 0);
    // First Monday of April 2017
    private DateTime testDate_030417 = DateTime.getDateTime(2017, Month.APRIL, 03, 0, 0);
    // First Monday of April 2018
    private DateTime testDate_020418 = DateTime.getDateTime(2018, Month.APRIL, 02, 0, 0);
    // Second Monday of April 20172017
    private DateTime testDate_100417 = DateTime.getDateTime(2017, Month.APRIL, 10, 0, 0);


    // Some Edge Cases for Last Monday
    // Last Monday of April 2018, in particular that April has 5 Mondays
    // In particular, it should also match the 5th week Monday
    private DateTime testDate_300418 = DateTime.getDateTime(2018, Month.APRIL, 30, 0, 0);

    private DayInMonthTE everyFirstMonday = new DayInMonthTE(1, 1);
    private DayInMonthTE everyLastTuesday = new DayInMonthTE(-1, 2);
    private DayInMonthTE everyLastMonday = new DayInMonthTE(-1, 1);
    private DayInMonthTE everyFifthMonday = new DayInMonthTE(5, 1);

    @Test
    public void includes_300418_everyFifthMonday_true() {
        assertTrue(everyFifthMonday.includes(testDate_300418));
    }
    @Test
    public void includes_300418_everyLastMonday_true() {
        assertTrue(everyLastMonday.includes(testDate_300418));
    }
    @Test
    public void includes_280317_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate_280317));
    }
    @Test
    public void includes_250417_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate_250417));
    }
    @Test
    public void includes_240418_everyLastTuesday_true() {
        assertTrue(everyLastTuesday.includes(testDate_240418));
    }
    @Test
    public void includes_210317_everyLastTuesday_false() {
        assertFalse(everyLastTuesday.includes(testDate_210317));
    }

    @Test
    public void includes_060317_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate_060317));
    }
    @Test
    public void includes_030417_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate_030417));
    }
    @Test
    public void includes_020418_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate_020418));
    }
    @Test
    public void includes_100417_everyFirstMonday_true() {
        assertFalse(everyFirstMonday.includes(testDate_100417));
    }
}
