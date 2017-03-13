package seedu.typed.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DateTimeTest {

    String testDate1 = "Apr 18 2017";
    String testDate2 = "Dec 25 2017";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM DD YYYY");

    // Holidays date
    LocalDateTime christmas = LocalDateTime.of(2017, Month.DECEMBER, 25, 0, 0);
    LocalDateTime goodFriday = LocalDateTime.of(2017, Month.APRIL, 18, 0, 0);
    LocalDateTime aprilFool = LocalDateTime.of(2017, Month.APRIL, 1, 0, 0);

    // day after Holidays
    LocalDateTime dayAfterAprilFool = LocalDateTime.of(2017, Month.APRIL, 2, 0, 0);
    LocalDateTime dayAfterChristmas = LocalDateTime.of(2017, Month.DECEMBER, 26, 0, 0);
    LocalDateTime dayAfterGoodfriday = LocalDateTime.of(2017, Month.APRIL, 19, 0, 0);

    // day before Holidays
    LocalDateTime dayBeforeAprilFool = LocalDateTime.of(2017, Month.MARCH, 31, 0, 0);
    LocalDateTime dayBeforeChristmas = LocalDateTime.of(2017, Month.DECEMBER, 24, 0, 0);
    LocalDateTime dayBeforeGoodfriday = LocalDateTime.of(2017, Month.APRIL, 17, 0, 0);

    // month after Holidays
    LocalDateTime monthAfterAprilFool = LocalDateTime.of(2017, Month.MAY, 1, 0, 0);
    LocalDateTime monthAfterChristmas = LocalDateTime.of(2018, Month.JANUARY, 25, 0, 0);
    LocalDateTime monthAfterGoodfriday = LocalDateTime.of(2017, Month.MAY, 18, 0, 0);


    DateTime christmasDay = new DateTime(christmas);
    DateTime goodFridayDay = new DateTime(goodFriday);
    DateTime aprilFoolDay = new DateTime(aprilFool);

    DateTime dayAfterAprilFoolDay = new DateTime(dayAfterAprilFool);
    DateTime dayAfterGoodFriday = new DateTime(dayAfterGoodfriday);
    DateTime dayAfterChristmasDay = new DateTime(dayAfterChristmas);

    DateTime dayBeforeAprilFoolDay = new DateTime(dayBeforeAprilFool);
    DateTime dayBeforeGoodFriday = new DateTime(dayBeforeGoodfriday);
    DateTime dayBeforehristmasDay = new DateTime(dayBeforeChristmas);

    DateTime monthAfterAprilFoolDay = new DateTime(monthAfterAprilFool);
    DateTime monthAfterGoodFriday = new DateTime(monthAfterGoodfriday);
    DateTime monthAfterChristmasDay = new DateTime(monthAfterChristmas);


    @Test
    public void testDateTimeTomorrow() {
        assertTrue(aprilFoolDay.tomorrow().equals(dayAfterAprilFoolDay));
        assertTrue(christmasDay.tomorrow().equals(dayAfterChristmasDay));
        assertTrue(goodFridayDay.tomorrow().equals(dayAfterGoodFriday));

        assertFalse(aprilFoolDay.tomorrow().equals(dayBeforeAprilFoolDay));
        assertFalse(christmasDay.tomorrow().equals(dayBeforehristmasDay));
        assertFalse(goodFridayDay.tomorrow().equals(dayBeforeGoodFriday));
    }

    @Test
    public void testDateTimeNextMonth() {
        assertTrue(aprilFoolDay.nextMonth().equals(monthAfterAprilFoolDay));
        assertTrue(christmasDay.nextMonth().equals(monthAfterChristmasDay));
        assertTrue(goodFridayDay.nextMonth().equals(monthAfterGoodFriday));

        assertFalse(aprilFoolDay.nextMonth().equals(dayBeforeAprilFoolDay));
        assertFalse(christmasDay.nextMonth().equals(dayBeforehristmasDay));
        assertFalse(goodFridayDay.nextMonth().equals(dayBeforeGoodFriday));

    }

}
