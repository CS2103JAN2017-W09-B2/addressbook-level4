package seedu.typed.logic.commands;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;

        this.feedbackToUser = feedbackToUser;
    }

    public String getFeedback() {
        return feedbackToUser;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
               || (other instanceof CommandResult
                   && this.getFeedback().equals(((CommandResult) other).getFeedback()));
    }

}
