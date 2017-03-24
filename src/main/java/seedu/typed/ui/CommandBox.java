package seedu.typed.ui;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.typed.commons.core.LogsCenter;
import seedu.typed.commons.events.ui.NewResultAvailableEvent;
import seedu.typed.commons.util.FxViewUtil;
import seedu.typed.logic.Logic;
import seedu.typed.logic.commands.CommandResult;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.storage.temp.Session;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;
    private Session session;

    private Stack<String> historyCommand = new Stack<String>();
    private Stack<String> nextCommand = new Stack<String>();

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic, Session session) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        commandTextField.requestFocus();
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            String commandInput = commandTextField.getText();
            CommandResult commandResult = logic.execute(commandInput);

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            historyCommand.push(commandInput);
            commandTextField.requestFocus();
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            commandTextField.requestFocus();
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if ((event.getCode().toString().equals("UP")) && (!historyCommand.isEmpty())) {
            String commandToShow = historyCommand.pop();
            // save the command to the nextCommand stack
            nextCommand.push(commandToShow);

            commandTextField.setText(commandToShow);
        }
        if (event.getCode().toString().equals("DOWN")) {
            if (!nextCommand.isEmpty()) {
                String commandToShow = nextCommand.pop();
                // save the command to historyCommand stack
                historyCommand.push(commandToShow);

                commandTextField.setText(commandToShow);
            } else {
                commandTextField.clear();
            }
        }
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

}
