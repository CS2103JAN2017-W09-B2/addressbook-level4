package seedu.typed.Schedule;

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
    private RangeEachYearTE Jan = new RangeEachYearTE(1);

    // Boundary Cases for goodFridayDay specific
    private RangeEachYearTE April18toOct25 = new RangeEachYearTE(4, 18, 18, 25);
    private RangeEachYearTE Jan21toApril17 = new RangeEachYearTE(1, 4, 21, 17);
    private RangeEachYearTE April19toOct25 = new RangeEachYearTE(4, 18, 19, 25);

    @Test
    public void includes_christmasDay_julyToDec_true() {
        assertTrue(julyToDec.includes(christmasDay));
    }
    @Test
    public void includes_aprilFoolDay_marchToJune_true() {
        assertTrue(marchToJune.includes(aprilFoolDay));
    }
    @Test
    public void includes_christmasDay_marchToJune_false() {
        assertFalse(marchToJune.includes(christmasDay));
    }
    @Test
    public void includes_goodFridayDay_Jan_false() {
        assertFalse(Jan.includes(goodFridayDay));
    }
    @Test
    public void includes_happyNewYear_Jan_true() {
        assertTrue(Jan.includes(happyNewYear));
    }
    // Test for Boundary cases
    @Test
    public void includes_aprilFoolDay_April18toOct25_true() {
        assertTrue(April18toOct25.includes(goodFridayDay));
    }
    @Test
    public void includes_aprilFoolDay_Jan21toApril17_false() {
        assertFalse(Jan21toApril17.includes(goodFridayDay));
    }
    @Test
    public void includes_aprilFoolDay_April19toOct25_false() {
        assertFalse(April19toOct25.includes(goodFridayDay));
    }
}
