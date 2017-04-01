//@@author A0141094M

/**
 * Helper functions for handling dates.
 */
package seedu.typed.commons.util;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Utility functions for natty support.
 *
 */
public class NattyUtil {

    public static Date parseDate(String date) {
        Parser natty = new Parser();
        List<DateGroup> dateGroups = natty.parse(date);
        if (dateGroups.isEmpty() || dateGroups.get(0).getDates().isEmpty()) {
            return null;
        }
        return dateGroups.get(0).getDates().get(0);
    }

}
