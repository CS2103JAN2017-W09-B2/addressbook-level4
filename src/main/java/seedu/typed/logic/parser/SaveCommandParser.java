package seedu.typed.logic.parser;

import static seedu.typed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.typed.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

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

        String fileName = keywords[0];

//        if (hasXML(fileName)) {
//            return new SaveCommand(fileName);
//        } else {
//            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
//        }
        return new SaveCommand(createProperExtension(fileName));
    }

    /*
     * Returns the correct extension (xml) no matter the input.
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
}
