//@@author A0141094M

package seedu.typed.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FindUtilTest {

    private String catsLowerCase;
    private String cotsLowerCase;
    private String jonniCamelCase;
    private String johnCamelCase;
    private String mintingLowerCase;
    private String sLowerCase;
    private String seLowerCase;
    private String seaLowerCase;
    private String seasLowerCase;
    private String meetLowerCase;
    private String meetUpperCase;
    private String meetsLowerCase;
    private String meetssLowerCase;
    private String scheduleLowerCase;
    private String meLowerCase;
    private String meetingLowerCase;
    private String meetingUpperCase;
    private String meetingCamelCase;
    private String meetingsLowerCase;
    private String meetingssLowerCase;
    private String meetingsssLowerCase;
    private String meetingsagainLowerCase;
    private String meetingInSentence;
    private String emptyStr;
    private String whitespaceStr;
    private String nullStr;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        catsLowerCase = "cats";
        cotsLowerCase = "cots";
        jonniCamelCase = "Jonni";
        johnCamelCase = "John";
        mintingLowerCase = "minting";
        sLowerCase = "s";
        seLowerCase = "se";
        seaLowerCase = "sea";
        seasLowerCase = "seas";
        meetLowerCase = "meet";
        meetUpperCase = "MEET";
        meetsLowerCase = "meets";
        meetssLowerCase = "meetss";
        scheduleLowerCase = "schedule";
        meLowerCase = "me";
        meetingLowerCase = "meeting";
        meetingUpperCase = "MEETING";
        meetingCamelCase = "Meeting";
        meetingsLowerCase = "meetings";
        meetingssLowerCase = "meetingss";
        meetingsssLowerCase = "meetingsss";
        meetingsagainLowerCase = "meetingsagain";
        meetingInSentence = "schedule a meeting with contractor Jamie";
        emptyStr = "";
        whitespaceStr = "    ";
        nullStr = null;
    }

    @After
    public void tearDown() {
        catsLowerCase = null;
        cotsLowerCase = null;
        jonniCamelCase = null;
        johnCamelCase = null;
        mintingLowerCase = null;
        sLowerCase = null;
        seLowerCase = null;
        seaLowerCase = null;
        seasLowerCase = null;
        meetLowerCase = null;
        meetUpperCase = null;
        meetsLowerCase = null;
        meetssLowerCase = null;
        scheduleLowerCase = null;
        meLowerCase = null;
        meetingLowerCase = null;
        meetingUpperCase = null;
        meetingCamelCase = null;
        meetingsLowerCase = null;
        meetingssLowerCase = null;
        meetingsssLowerCase = null;
        meetingsagainLowerCase = null;
        meetingInSentence = null;
        emptyStr = null;
        whitespaceStr = null;
        nullStr = null;
    }

    private void assertExceptionThrown(String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
    }

    // ---------------- Tests for isExactWordMatchIgnoreCase
    // -----------------------------------------------------------------------

    @Test
    public void isExactWordMatchIgnoreCase_nullWord_assertionError() {
        assertExceptionThrown(FindUtil.STR_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isExactWordMatchIgnoreCase(nullStr, meetingLowerCase);
    }
    @Test
    public void isExactWordMatchIgnoreCase_nullQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, nullStr);
    }
    @Test
    public void isExactWordMatchIgnoreCase_emptyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_EMPTY_MESSAGE);
        FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, emptyStr);
    }
    @Test
    public void isExactWordMatchIgnoreCase_whitespaceOnlyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, whitespaceStr);
    }
    @Test
    public void isExactWordMatchIgnoreCase_multipleWordQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, meetingInSentence);
    }

    @Test
    public void isExactWordMatchIgnoreCase_baseIsASentence_falseReturned() {
        assertFalse(FindUtil.isExactWordMatchIgnoreCase(meetingInSentence, meetingLowerCase));
    }
    @Test
    public void isExactWordMatchIgnoreCase_queryIsSubstringOfWord_falseReturned() {
        assertFalse(FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, meetLowerCase));
    }
    @Test
    public void isExactWordMatchIgnoreCase_queryContainsWord_falseReturned() {
        assertFalse(FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, meetingsagainLowerCase));
    }
    @Test
    public void isExactWordMatchIgnoreCase_queryIsDifferentFromWord_falseReturned() {
        assertFalse(FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, scheduleLowerCase));
    }

    @Test
    public void isExactWordMatchIgnoreCase_sameWordSameCase_trueReturned() {
        assertTrue(FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, meetingLowerCase));
    }
    @Test
    public void isExactWordMatchIgnoreCase_sameWordDifferentCase_trueReturned() {
        assertTrue(FindUtil.isExactWordMatchIgnoreCase(meetingLowerCase, meetingUpperCase));
        assertTrue(FindUtil.isExactWordMatchIgnoreCase(meetingCamelCase, meetingLowerCase));
    }

    // ---------------- Tests for isSubstringWordMatchIgnoreCase
    // ------------------------------------------------------------------------
    @Test
    public void isSubstringWordMatchIgnoreCase_nullWord_assertionError() {
        assertExceptionThrown(FindUtil.STR_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isSubstringWordMatchIgnoreCase(nullStr, meetingLowerCase);
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_nullQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, nullStr);
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_emptyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_EMPTY_MESSAGE);
        FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, emptyStr);
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_whitespaceOnlyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, whitespaceStr);
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_multipleWordQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetingInSentence);
    }

    @Test
    public void isSubstringWordMatchIgnoreCase_baseIsASentence_trueReturned() {
        assertTrue(FindUtil.isSubstringWordMatchIgnoreCase(meetingInSentence, meetingLowerCase));
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_queryContainsWord_falseReturned() {
        assertFalse(FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetingsagainLowerCase));
    }

    @Test
    public void isSubstringWordMatchIgnoreCase_queryIsSubstringOfWord_trueReturned() {
        assertTrue(FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetLowerCase));
        assertTrue(FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetUpperCase));
    }
    @Test
    public void isSubstringWordMatchIgnoreCase_queryIsExactMatch_trueReturned() {
        assertTrue(FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetingLowerCase));
        assertTrue(FindUtil.isSubstringWordMatchIgnoreCase(meetingLowerCase, meetingCamelCase));
    }


    // ---------------- Tests for isFuzzyWordMatchIgnoreCase
    // -------------------------------------------------------------------------
    @Test
    public void isFuzzyWordMatchIgnoreCase_nullWord_assertionError() {
        assertExceptionThrown(FindUtil.STR_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isFuzzyWordMatchIgnoreCase(nullStr, meetingLowerCase);
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_nullQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, nullStr);
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_emptyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_EMPTY_MESSAGE);
        FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, emptyStr);
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_whitespaceOnlyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, whitespaceStr);
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_multipleWordQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isFuzzyWordMatchIgnoreCase(meetingInSentence, meetingInSentence);
    }

    @Test
    public void isFuzzyWordMatchIgnoreCase_queryIsExactMatch_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingLowerCase));
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingUpperCase));
    }

    // the following tests make reference to :
    //      case one: base word being queried is of length less than or equal two
    //      case two: base word being queried is of length less than or equal four
    //      case three: base word being queried is of minimum length five
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseOneExactMatch_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(seLowerCase, seLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseOneQueryIsSubstringOfWord_falseReturned() {
        assertFalse(FindUtil.isFuzzyWordMatchIgnoreCase(seLowerCase, sLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseOneQueryContainsWord_falseReturned() {
        assertFalse(FindUtil.isFuzzyWordMatchIgnoreCase(seLowerCase, seaLowerCase));
    }

    @Test
    public void isFuzzyWordMatchIgnoreCase_caseTwoQueryExactMatch_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetLowerCase, meetLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseTwoQueryIsWithinEditDistanceOfOne_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetLowerCase, meetsLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseTwoQueryIsWithinEditDistanceOfTwo_falseReturned() {
        assertFalse(FindUtil.isFuzzyWordMatchIgnoreCase(meetLowerCase, meetssLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseTwoQueryIsSubstringOfWord_falseReturned() {
        assertFalse(FindUtil.isFuzzyWordMatchIgnoreCase(meetLowerCase, meLowerCase));
    }

    @Test
    public void isFuzzyWordMatchIgnoreCase_caseThreeQueryExactMatch_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseThreeQueryIsWithinEditDistanceOfOne_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingsLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseThreeQueryIsWithinEditDistanceOfTwo_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingssLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseThreeQueryIsSubstringOfWord_trueReturned() {
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meLowerCase));
        assertTrue(FindUtil.isFuzzyWordMatchIgnoreCase(meetingInSentence, meetingLowerCase));
    }
    @Test
    public void isFuzzyWordMatchIgnoreCase_caseThreeQueryIsWithinEditDistanceOfThree_falseReturned() {
        assertFalse(FindUtil.isFuzzyWordMatchIgnoreCase(meetingLowerCase, meetingsssLowerCase));
    }

    // ---------------- Tests for isFuzzyTagMatchIgnoreCase
    // -----------------------------------------------------------------------
    @Test
    public void isFuzzyTagMatchIgnoreCase_nullWord_assertionError() {
        assertExceptionThrown(FindUtil.STR_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isFuzzyTagMatchIgnoreCase(nullStr, meetLowerCase);
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_nullQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_CANNOT_BE_NULL_MESSAGE);
        FindUtil.isFuzzyTagMatchIgnoreCase(meetingLowerCase, nullStr);
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_whitespaceOnlyQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isFuzzyTagMatchIgnoreCase(meetingLowerCase, whitespaceStr);
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_multipleWordQuery_assertionError() {
        assertExceptionThrown(FindUtil.QUERY_LEN_SHOULD_BE_LESS_THAN_ONE_MESSAGE);
        FindUtil.isFuzzyTagMatchIgnoreCase(meetingLowerCase, meetingInSentence);
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_baseIsASentence_assertionError() {
        assertExceptionThrown(FindUtil.TAG_LEN_SHOULD_BE_ONE_MESSAGE);
        FindUtil.isFuzzyTagMatchIgnoreCase(meetingInSentence, meetingLowerCase);
    }

    @Test
    public void isFuzzyTagMatchIgnoreCase_queryIsExactMatch_trueReturned() {
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(seLowerCase, seLowerCase));
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetLowerCase, meetLowerCase));
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetingCamelCase, meetingUpperCase));
    }

    // the following tests make reference to :
    //      case one: query is of length zero (searching for all tags)
    //      case two: base tag being queried is of length less than or equal four
    //      case three: base tag being queried is of minimum length five
    @Test
    public void isFuzzyTagMatchIgnoreCase_caseOneQueryLengthZero_trueReturned() {
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(seLowerCase, emptyStr));
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetingCamelCase, emptyStr));
    }

    @Test
    public void isFuzzyTagMatchIgnoreCase_caseTwoQueryIsExactMatchSameCase_trueReturned() {
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetLowerCase, meetLowerCase));
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetLowerCase, meetUpperCase));
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_caseTwoQueryIsNotExactMatch_falseReturned() {
        assertFalse(FindUtil.isFuzzyTagMatchIgnoreCase(meetLowerCase, meetsLowerCase));
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_caseTwoQueryIsSubstringOfTag_falseReturned() {
        assertFalse(FindUtil.isFuzzyTagMatchIgnoreCase(meetLowerCase, meLowerCase));
    }

    @Test
    public void isFuzzyTagMatchIgnoreCase_caseThreeQueryIsEditDistanceOfOne_TrueReturned() {
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetingLowerCase, meetingsLowerCase));
    }
    @Test
    public void isFuzzyTagMatchIgnoreCase_caseThreeQueryIsSubstringOfTag_trueReturned() {
        assertTrue(FindUtil.isFuzzyTagMatchIgnoreCase(meetingLowerCase, meLowerCase));
    }

    // ---------------- Tests for computeMinEditDistance
    // --------------------------------------------------------------------
    @Test
    public void computeMinEditDistance_distanceOfZero_trueReturned() {
        assertTrue(FindUtil.computeMinEditDistance(seLowerCase, seLowerCase) == 0);
    }
    @Test
    public void computeMinEditDistance_distanceOfZero_falseReturned() {
        assertFalse(FindUtil.computeMinEditDistance(meetLowerCase, meetUpperCase) == 0);
    }
    @Test
    public void computeMinEditDistance_distanceOfOne_trueReturned() {
        assertTrue(FindUtil.computeMinEditDistance(seLowerCase, seaLowerCase) == 1);
        assertTrue(FindUtil.computeMinEditDistance(meetingsLowerCase, meetingLowerCase) == 1);
        assertTrue(FindUtil.computeMinEditDistance(catsLowerCase, cotsLowerCase) == 1);
    }
    @Test
    public void computeMinEditDistance_distanceOfOne_falseReturned() {
        assertFalse(FindUtil.computeMinEditDistance(meetingsLowerCase, meetingUpperCase) == 1);
    }
    @Test
    public void computeMinEditDistance_distanceOfTwo_trueReturned() {
        assertTrue(FindUtil.computeMinEditDistance(seLowerCase, seasLowerCase) == 2);
        assertTrue(FindUtil.computeMinEditDistance(meetingLowerCase, mintingLowerCase) == 2);
        assertTrue(FindUtil.computeMinEditDistance(jonniCamelCase, johnCamelCase) == 2);
    }
    @Test
    public void computeMinEditDistance_distanceOfTwo_falseReturned() {
        assertFalse(FindUtil.computeMinEditDistance(meetingCamelCase, mintingLowerCase) == 2);
    }

}
//@@author
