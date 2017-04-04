//@@author A0141094M
package seedu.typed.commons.lexicon;

import java.util.ArrayList;

/**
 * Utility class to maintain the list of date identifiers.
 *
 */
public class DateIdentifiersUtil {

    static String DATE_IDENTIFIER_BY = "by";
    static String DATE_IDENTIFIER_ON = "on";
    static String DATE_IDENTIFIER_FROM = "from";
    static String DATE_IDENTIFIER_TO = "to";
    static String DATE_IDENTIFIER_EVERY = "every";


    public static ArrayList<String> getAllDateIdentifiers() {
        ArrayList<String> dateIdentifiers = new ArrayList<String>();
        dateIdentifiers.add(DATE_IDENTIFIER_BY);
        dateIdentifiers.add(DATE_IDENTIFIER_ON);
        dateIdentifiers.add(DATE_IDENTIFIER_FROM);
        dateIdentifiers.add(DATE_IDENTIFIER_TO);
        dateIdentifiers.add(DATE_IDENTIFIER_EVERY);
        return dateIdentifiers;
    }

    public static boolean isDateIdentifier(String word) {
        return getAllDateIdentifiers().contains(word);
    }

}
//@@author
