package seedu.typed.model.task;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

public class DateTimeTest {

    private DateTime getDateTime(int year, Month month, int d1, int d2, int d3) {
        return new DateTime(LocalDateTime.of(year, month, d1, d2, d3));
    }

    DateTime aprilFoolDay = getDateTime(2017, Month.APRIL, 1, 0, 0);
    DateTime christmasDay = getDateTime(2017, Month.DECEMBER, 25, 0, 0);
    DateTime goodFridayDay = getDateTime(2017, Month.APRIL, 18, 0, 0);

    DateTime dayAfterAprilFoolDay = getDateTime(2017, Month.APRIL, 2, 0, 0);
    DateTime dayAfterChristmasDay = getDateTime(2017, Month.DECEMBER, 26, 0, 0);
    DateTime dayAfterGoodFridayDay = getDateTime(2017, Month.APRIL, 19, 0, 0);

    DateTime monthAfterAprilFoolDay = getDateTime(2017, Month.MAY, 1, 0, 0);
    DateTime monthAfterChristmasDay = getDateTime(2018, Month.JANUARY, 25, 0, 0);
    DateTime monthAfterGoodFridayDay = getDateTime(2017, Month.MAY, 18, 0, 0);

    DateTime yearAfterAprilFoolDay = getDateTime(2018, Month.APRIL, 1, 0, 0);
    DateTime yearAfterChristmasDay = getDateTime(2018, Month.DECEMBER, 25, 0, 0);
    DateTime yearAfterGoodFridayDay = getDateTime(2018, Month.APRIL, 18, 0, 0);

    @Test
    public void dateTimeTomorrow_aprilFoolDay_returnTrue() {
        assertTrue(aprilFoolDay.tomorrow().equals(dayAfterAprilFoolDay));
    }

    @Test
    public void dateTimeTomorrow_christmasDay_returnTrue() {
        assertTrue(christmasDay.tomorrow().equals(dayAfterChristmasDay));
    }

    @Test
    public void dateTimeTomorrow_goodFridayDay_returnTrue() {
        assertTrue(goodFridayDay.tomorrow().equals(dayAfterGoodFridayDay));
    }

    @Test
    public void dateTimeNextMonth_aprilFoolDay_returnTrue() {
        assertTrue(aprilFoolDay.nextMonth().equals(monthAfterAprilFoolDay));
    }
    @Test
    public void dateTimeNextMonth_christmasDay_returnTrue() {
        assertTrue(christmasDay.nextMonth().equals(monthAfterChristmasDay));
    }
    @Test
    public void dateTimeNextMonth_goodFridayDay_returnTrue() {
        assertTrue(goodFridayDay.nextMonth().equals(monthAfterGoodFridayDay));
    }

    @Test
    public void dateTimeNextYear_aprilFoolDay_returnTrue() {
        assertTrue(aprilFoolDay.nextYear().equals(yearAfterAprilFoolDay));
    }
    @Test
    public void dateTimeNextYear_christmasDay_returnTrue() {
        assertTrue(christmasDay.nextYear().equals(yearAfterChristmasDay));
    }
    @Test
    public void dateTimeNextYear_goodFridayDay_returnTrue() {
        assertTrue(goodFridayDay.nextYear().equals(yearAfterGoodFridayDay));
    }

}
