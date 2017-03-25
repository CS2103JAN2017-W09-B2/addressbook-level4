package seedu.typed.logic.parser;

import java.util.regex.Pattern;

import seedu.typed.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE = new Prefix(" by ");
    public static final Prefix PREFIX_FROM = new Prefix(" from ");
    public static final Prefix PREFIX_TO = new Prefix(" to ");
    public static final Prefix PREFIX_TAG = new Prefix(" #");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                          // or
                                                                                                          // more
                                                                                                          // keywords
                                                                                                          // separated
                                                                                                          // by
                                                                                                          // whitespace

}
