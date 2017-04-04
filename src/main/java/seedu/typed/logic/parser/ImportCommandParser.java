//@@author A0139392X
package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.ImportCommand;
import seedu.typed.logic.commands.IncorrectCommand;

/**
 * Parses input argument and imports the file.
 */
public class ImportCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ImportCommand and returns an ImportCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");

        // if there are whitespace, invalid input by user
        if ((keywords.length) != 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        String location = keywords[0];

        if (FileUtil.isXML(location)) {
            return new ImportCommand(location);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        }
    }

}
//@@author
