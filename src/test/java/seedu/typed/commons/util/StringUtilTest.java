package seedu.typed.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // ---------------- Tests for isUnsignedPositiveInteger
    // --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // Equivalence partition: null
        assertFalse(StringUtil.isUnsignedInteger(null));

        // EP: empty strings
        assertFalse(StringUtil.isUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isUnsignedInteger("a"));
        assertFalse(StringUtil.isUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isUnsignedInteger("0"));

        // EP: signed numbers
        assertFalse(StringUtil.isUnsignedInteger("-1"));
        assertFalse(StringUtil.isUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isUnsignedInteger(" 10 ")); // Leading/trailing
                                                           // spaces
        assertFalse(StringUtil.isUnsignedInteger("1 0")); // Spaces in the
                                                          // middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }

    //@@author A0141094M

    // ---------------- Tests for isFuzzyKeywordSearchIgnoreCase
    // --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     */

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_invalidNullWord_exceptionThrown() {
        assertExceptionThrown("typical sentence", null, "Query parameter cannot be null");
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_invalidEmptyWord_exceptionThrown() {
        assertExceptionThrown("typical sentence", "  ", "Query parameter cannot be empty");
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_invalidMultipleWords_exceptionThrown() {
        assertExceptionThrown("typical sentence", "aaa BBB", "Query parameter should be a single word");
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_invalidNullSentence_exceptionThrown() {
        assertExceptionThrown(null, "abc", "Sentence parameter cannot be null");
    }

    private void assertExceptionThrown(String sentence, String word, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        StringUtil.isFuzzyKeywordSearchIgnoreCase(sentence, word);
    }

    /*
     * Valid equivalence partitions returning false: empty sentence, whitespace only sentence,
     * minimum edit distance of above two i.e. regarded as not similar in our implementation
     */

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validEmptySentence_falseReturned() {
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("", "abc"));
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validWhitespaceOnlySentence_falseReturned() {
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("    ", "123"));
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validPartialMatchDistanceOfAboveTwo_falseReturned() {
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "z"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "xyZ"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "123"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "h124heu71"));
    }

    /*
     * Valid equivalence partitions returning true
     */

    // Some base tests for sentence format: single word in sentence, extra whitespace in sentence,
    // sentence contains alphanumeric words

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validSingleWordInSentence_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa", "Aaa"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validSentenceWithExtraWhitespace_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa   bbb cccc", "aaa"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("      aaa bBb cccc", "Bbb"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bBb cccc             ", "cCc"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validSentenceWithAlphanumericWords_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bBb Ccc13", "cCc12"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bBb 134", "133"));
    }

    // Tests for exact matches: same case, different case; single keyword match, multiple keyword match

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validExactMatchSameCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "aaa"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validExactMatchDifferentCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "AAA"));
    }

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validMultipleExactMatchSameCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc aaa", "aaa"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validMultipleExactMatchDifferentCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc aaa", "aAA"));
    }

    // Tests for partial matches: same case, different case; minimum edit distance of 1, minimum
    // edit distance of 2

    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validPartialMatchDistanceOfOneSameCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "bb"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "bbbb"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "aab"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "abb"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validPartialMatchDistanceOfOneDifferentCase_trueReturned() {
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "Bb"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "BbBB"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "AAB"));
        assertTrue(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "ABB"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validPartialMatchDistanceOfTwoSameCase_falseReturned() {
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "b"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "bbbbb"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "abc"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "ccde"));
    }
    @Test
    public void isFuzzyKeywordSearchIgnoreCase_validPartialMatchDistanceOfTwoDifferentCase_falseReturned() {
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "B"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "BBBBB"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "ABC"));
        assertFalse(StringUtil.isFuzzyKeywordSearchIgnoreCase("aaa bbb cccc", "CCDE"));
    }

    // ---------------- Tests for getDetails
    // --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    //@@author

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }

}
