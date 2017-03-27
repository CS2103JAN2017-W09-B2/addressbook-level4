package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.typed.commons.util.FileUtil;
import seedu.typed.logic.commands.Command;
import seedu.typed.logic.commands.IncorrectCommand;
import seedu.typed.logic.commands.SaveCommand;

//@@author A0139392X
/**
 * Parses input argument and save the file.
 */
public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SaveCommand and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");

        // if there are whitespace, invalid input by user
        if ((keywords.length) != 1){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        String fileName = keywords[0];

        if (isAPath(fileName)) {
            return new SaveCommand(1, createProperExtension(fileName));
        } else if (FileUtil.isValidName(fileName)){
            return new SaveCommand(2, createProperExtension(fileName));
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
    }

    /*
     * Returns the correct extension no matter the input.
     * @param  String fileName
     *             fileName input by the user as the new name
     * @return String
     *             with the proper extension.
     */
    private String createProperExtension(String fileName) {
        if (fileName.contains(".")) {
            String beforeDot = fileName.substring(0, fileName.lastIndexOf("."));
            String afterDot = fileName.substring(fileName.lastIndexOf("."));
            if (afterDot.equalsIgnoreCase("xml")) {
                return beforeDot;
            } else {
                return (beforeDot + ".xml");
            }
        } else {
            return (fileName + ".xml");
        }
    }

    /*
     * Returns true is the input given by the user is a path. False otherwise.
     *
     * @param   String fileName
     *              Input given by the user.
     */
    private boolean isAPath(String fileName) {
        return fileName.contains("/") || fileName.contains("\\");
    }
}
//@@author
