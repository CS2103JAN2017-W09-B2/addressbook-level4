package seedu.typed.logic.commands.util;
//@@author A0143853A
public class IndexRangeUtil {

    private static final String RANGE_TO_REGEX = "[1-9]+[0-9]*\\s+[t]{1}[o]{1}\\s+[1-9]+[0-9]*";
    private static final String RANGE_NUMBERS_ONLY_REGEX = "[1-9]+[0-9]*\\s+[1-9]+[0-9]*";
    private static final String RANGE_DASH_REGEX = "[1-9]+[0-9]*\\s+[\\-]\\s+[1-9]+[0-9]*";

    private static final String TO_REGEX = "\\s+[t]{1}[o]{1}\\s+";
    private static final String SPACE_REGEX = "\\s+";
    private static final String DASH_REGEX = "\\s+[\\-]\\s+";

    private int startIndex;
    private int endIndex;
    private boolean isValid;

    public IndexRangeUtil(String range) {
        String trimmedRange = range.trim();
        if (trimmedRange.matches(RANGE_TO_REGEX)) {
            handleToRange(trimmedRange);
        } else if (trimmedRange.matches(RANGE_NUMBERS_ONLY_REGEX)) {
            handleNumbersOnlyRange(trimmedRange);
        } else if (trimmedRange.matches(RANGE_DASH_REGEX)) {
            handleDashRange(trimmedRange);
        } else {
            isValid = false;
            startIndex = -1;
            endIndex = -1;
        }

    }

    public boolean isValid() {
        return isValid;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    private void handleToRange(String range) {
        String[] indices = range.split(TO_REGEX);
        if (indices.length == 2) {
            startIndex = Integer.parseInt(indices[0]);
            endIndex = Integer.parseInt(indices[1]);
        } else {
            startIndex = -1;
            endIndex = -1;
        }

        if ((startIndex <= 0) || (startIndex > endIndex)) {
            isValid = false;
        } else {
            isValid = true;
        }
    }

    private void handleNumbersOnlyRange(String range) {
        String[] indices = range.split(SPACE_REGEX);
        if (indices.length == 2) {
            startIndex = Integer.parseInt(indices[0]);
            endIndex = Integer.parseInt(indices[1]);
        } else if (indices.length == 1) {
            startIndex = Integer.parseInt(indices[0]);
            endIndex = startIndex;
        } else {
            startIndex = -1;
            endIndex = -1;
        }

        if ((startIndex <= 0) || (startIndex > endIndex)) {
            isValid = false;
        } else {
            isValid = true;
        }
    }

    private void handleDashRange(String range) {
        String[] indices = range.split(DASH_REGEX);
        if (indices.length == 2) {
            startIndex = Integer.parseInt(indices[0]);
            endIndex = Integer.parseInt(indices[1]);
        } else {
            startIndex = -1;
            endIndex = -1;
        }

        if ((startIndex <= 0) || (startIndex > endIndex)) {
            isValid = false;
        } else {
            isValid = true;
        }
    }
}
