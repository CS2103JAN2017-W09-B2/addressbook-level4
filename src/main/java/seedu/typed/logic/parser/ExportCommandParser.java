package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.ExportCommand;
import seedu.typed.logic.commands.IncorrectCommand;

//@@author A0139392X
/**
 * Parses input argument and exports the file.
 */
public class ExportCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ExportCommand and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");

        // if there are whitespace, invalid input by user
        if ((keywords.length) != 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        String fileName = keywords[0];

        if (isAPath(fileName)) {
            return new ExportCommand(1, FileUtil.createProperExtension(fileName));
        } else if (FileUtil.isValidName(fileName)) {
            return new ExportCommand(2, FileUtil.createProperExtension(fileName));
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }

    /*
     * Returns true is the input given by the user is a path. False otherwise.
     *
     * @param   String fileName
     *              Input given by the user.
     */
    private boolean isAPath(String fileName) {
        return fileName.contains("/");
    }
}
//@@author
