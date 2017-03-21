package seedu.typed.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

/*
 * Unit Testing for DateTime class 98.4%
 * @author YIM CHIA HUI
 */
public class DateTimeTest {

    private DateTime today = new DateTime();
    private DateTime tomorrow = today.tomorrow();

    private DateTime aprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
    private DateTime christmasDay = DateTime.getDateTime(2017, Month.DECEMBER, 25, 0, 0);
    private DateTime goodFridayDay = DateTime.getDateTime(2017, Month.APRIL, 18, 0, 0);

    private DateTime dayAfterAprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 2, 0, 0);
    private DateTime dayAfterChristmasDay = DateTime.getDateTime(2017, Month.DECEMBER, 26, 0, 0);
    private DateTime dayAfterGoodFridayDay = DateTime.getDateTime(2017, Month.APRIL, 19, 0, 0);

    private DateTime weekAfterAprilFool = DateTime.getDateTime(2017, Month.APRIL, 8, 0, 0);

    private DateTime monthAfterAprilFoolDay = DateTime.getDateTime(2017, Month.MAY, 1, 0, 0);
    private DateTime monthAfterChristmasDay = DateTime.getDateTime(2018, Month.JANUARY, 25, 0, 0);
    private DateTime monthAfterGoodFridayDay = DateTime.getDateTime(2017, Month.MAY, 18, 0, 0);

    private DateTime yearAfterAprilFoolDay = DateTime.getDateTime(2018, Month.APRIL, 1, 0, 0);
    private DateTime yearAfterChristmasDay = DateTime.getDateTime(2018, Month.DECEMBER, 25, 0, 0);
    private DateTime yearAfterGoodFridayDay = DateTime.getDateTime(2018, Month.APRIL, 18, 0, 0);

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

    @Test
    public void isToday_notToday_returnFalse() {
        assertFalse(tomorrow.isToday());
    }

    @Test
    public void isToday_today_returnTrue() {
        assertTrue(today.isToday());
    }

    @Test
    public void isAfter_todayAfterTomorrow_returnFalse() {
        assertFalse(today.isAfter(tomorrow));
    }

    @Test
    public void isAfter_TomorrowAfterToday_returnTrue() {
        assertTrue(tomorrow.isAfter(today));
    }

    @Test
    public void nextWeek_AprilFool_Success() {
        assertTrue(weekAfterAprilFool.equals(aprilFoolDay.nextWeek()));
    }

    @Test
    public void setLocalDateTime_valid_true() {
        DateTime testDate = new DateTime();
        testDate.setLocalDateTime(LocalDateTime.of(2017, Month.APRIL, 1, 0, 0));
        assertTrue(aprilFoolDay.equals(testDate));
    }

    @Test
    public void toString_AprilFool_success() {
        assertTrue((aprilFoolDay.toString()).equals("2017-04-01"));
    }


}
