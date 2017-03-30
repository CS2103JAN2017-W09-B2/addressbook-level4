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
    private DateTime testDate060317 = DateTime.getDateTime(2017, Month.MARCH, 06, 0, 0);
    // First Monday of April 2017
    private DateTime testDate030417 = DateTime.getDateTime(2017, Month.APRIL, 03, 0, 0);
    // First Monday of April 2018
    private DateTime testDate020418 = DateTime.getDateTime(2018, Month.APRIL, 02, 0, 0);
    // Second Monday of April 20172017
    private DateTime testDate100417 = DateTime.getDateTime(2017, Month.APRIL, 10, 0, 0);


    // Some Edge Cases for Last Monday
    // Last Monday of April 2018, in particular that April has 5 Mondays
    // In particular, it should also match the 5th week Monday
    private DateTime testDate300418 = DateTime.getDateTime(2018, Month.APRIL, 30, 0, 0);

    private DayInMonthTE everyFirstMonday = new DayInMonthTE(1, 1);
    private DayInMonthTE everyLastTuesday = new DayInMonthTE(-1, 2);
    private DayInMonthTE everyLastMonday = new DayInMonthTE(-1, 1);
    private DayInMonthTE everyFifthMonday = new DayInMonthTE(5, 1);

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
    public void includes_060317_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate060317));
    }
    @Test
    public void includes_030417_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate030417));
    }
    @Test
    public void includes_020418_everyFirstMonday_true() {
        assertTrue(everyFirstMonday.includes(testDate020418));
    }
    @Test
    public void includes_100417_everyFirstMonday_true() {
        assertFalse(everyFirstMonday.includes(testDate100417));
    }
}
