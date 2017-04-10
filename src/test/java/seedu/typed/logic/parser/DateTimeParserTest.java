//@@author A0141094M

package seedu.typed.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.task.DateTime;

public class DateTimeParserTest {

    String invalidDate;
    String dateExactUSFormat;
    String dateShortUSFormat;
    String dateNaturalShort;
    String dateNaturalLong;
    String dateNaturalSentence;
    String dateTmrShort;
    String dateTmrLong;
    String invalidTime;
    String time24Format;
    String time12FormatLong;
    String time12FormatShort;
    String timeNatural;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        invalidDate = "22 decem";
        dateExactUSFormat = "12/22/2017"; // the standard
        dateShortUSFormat = "12/22";
        dateNaturalShort = "22 dec";
        dateNaturalLong = "22 december 2017";
        dateNaturalSentence = "the 22nd of december";
        dateTmrShort = "tmr";
        dateTmrLong = "tomorrow";
        invalidTime = "20hr";
        time24Format = "2000"; // the standard
        time12FormatLong = "0800pm";
        time12FormatShort = "8pm";
        timeNatural = "night";
    }

    @After
    public void tearDown() {
        invalidDate = null;
        dateExactUSFormat = null;
        dateShortUSFormat = null;
        dateNaturalShort = null;
        dateNaturalLong = null;
        dateNaturalSentence = null;
        dateTmrShort = null;
        dateTmrLong = null;
        invalidTime = null;
        time24Format = null;
        time12FormatLong = null;
        time12FormatShort = null;
        timeNatural = null;
    }

    private void assertExceptionThrown() {
        thrown.expect(AssertionError.class);
    }

    private DateTime genStandardDate() throws IllegalValueException {
        return DateTimeParser.getDateTimeFromString(dateExactUSFormat);
    }

    private void isDateEqual(DateTime dt, DateTime dt2) {
        assertEquals(dt.getYear(), dt2.getYear());
        assertEquals(dt.getMonth(), dt2.getMonth());
        assertEquals(dt.getDay(), dt2.getDay());
    }

    @Test
    public void parsingNaturalDates_invalidDate_illegalValueExceptionThrown() throws IllegalValueException {
        assertExceptionThrown();
        isDateEqual(genStandardDate(), DateTimeParser.getDateTimeFromString(invalidDate));
    }

    @Test
    public void parsingNaturalDates_dateShortUSFormat_equals() throws IllegalValueException {
        DateTime dateTimeShortUSFormat = DateTimeParser.getDateTimeFromString(dateShortUSFormat);
        isDateEqual(genStandardDate(), dateTimeShortUSFormat);
    }

    @Test
    public void parsingNaturalDates_dateNaturalShort_equals() throws IllegalValueException {
        DateTime dateTimeNaturalShort = DateTimeParser.getDateTimeFromString(dateNaturalShort);
        isDateEqual(genStandardDate(), dateTimeNaturalShort);
    }

    @Test
    public void parsingNaturalDates_dateNaturalLong_equals() throws IllegalValueException {
        DateTime dateTimeNaturalLong = DateTimeParser.getDateTimeFromString(dateNaturalLong);
        isDateEqual(genStandardDate(), dateTimeNaturalLong);
    }

    @Test
    public void parsingNaturalDates_dateNaturalSentence_equals() throws IllegalValueException {
        DateTime dateTimeNaturalSentence = DateTimeParser.getDateTimeFromString(dateNaturalSentence);
        isDateEqual(genStandardDate(), dateTimeNaturalSentence);
    }

    @Test
    public void parsingNaturalDates_dateTomorrowShorthands_equals() throws IllegalValueException {
        DateTime dateTimeTmrShort = DateTimeParser.getDateTimeFromString(dateTmrShort);
        DateTime dateTimeTmrLong = DateTimeParser.getDateTimeFromString(dateTmrLong);
        isDateEqual(dateTimeTmrShort, dateTimeTmrLong);
    }

    // ----------------------------------------------------------------------------------------

    private DateTime genStandardTime() throws IllegalValueException {
        return DateTimeParser.getDateTimeFromString(time24Format);
    }

    /*
    private void isTimeEqual(DateTime dt, DateTime dt2) {
        assertEquals(dt, dt2);
    }

    @Test
    public void parsingNaturalDates_invalidTime_illegalValueExceptionThrown() throws IllegalValueException {
        assertExceptionThrown("");
        DateTimeParser.getDateTimeFromString(invalidTime);
    }

    @Test
    public void parsingNaturalTimes_time12FormatShort_equals() throws IllegalValueException {
        DateTime dateTime12FormatShort = DateTimeParser.getDateTimeFromString(time12FormatShort);
        assertEquals(genStandardTime(), dateTime12FormatShort);
    }

    @Test
    public void parsingNaturalTimes_time12FormatLong_equals() throws IllegalValueException {
        DateTime dateTime12FormatLong = DateTimeParser.getDateTimeFromString(time12FormatLong);
        assertEquals(genStandardTime(), dateTime12FormatLong);
    }

    @Test
    public void parsingNaturalTimes_timeNaturalTime_equals() throws IllegalValueException {
        DateTime dateTimeNatural = DateTimeParser.getDateTimeFromString(timeNatural);
        assertEquals(genStandardTime(), dateTimeNatural);
    }

    */

}
//@@author
