package seedu.typed.logic.parser;

import java.util.regex.Pattern;

import seedu.typed.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    //@@author A0141094M
    public static final Prefix PREFIX_NOTES = new Prefix(" + ");
    public static final Prefix PREFIX_DATE = new Prefix(" by ");
    public static final Prefix PREFIX_ON = new Prefix(" on ");
    public static final Prefix PREFIX_FROM = new Prefix(" from ");
    public static final Prefix PREFIX_TO = new Prefix(" to ");
    public static final Prefix PREFIX_EVERY = new Prefix(" every ");
    public static final Prefix PREFIX_TAG = new Prefix(" #");

    public static final Prefix PREFIX_WITH = new Prefix(" with ");
    //@@author

    /* Patterns definitions */
    // one or more keywords separated by whitespace
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

}
