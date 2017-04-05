package seedu.typed.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.application.Platform;
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

    private ArrayList<String> commandHistory;
    private int pointer;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic, Session session) {
        super(FXML);
        this.logic = logic;
        this.session = session;
        this.commandHistory = this.session.getAllCommandsHistory();
        this.pointer = 0;
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
            commandTextField.clear();
            resetPointer();
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            resetPointer();
            commandTextField.clear();
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    //@@author A0139392X
    @FXML
    void handleKeyPressed(KeyEvent event) {
        String keyPressed = event.getCode().toString();

        switch(keyPressed) {

        case "UP":
            handleUpKey();
            break;

        case "DOWN":
            handleDownKey();
            break;

        default:
            break;

        }
    }
    //@@author

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

    //@@author A0143853A
    private void resetPointer() {
        pointer = commandHistory.size();
    }

    private boolean canUpPointer() {

        if (pointer == 0) {
            return false;
        }

        if (pointer > 0) {
            pointer--;
        }

        if (pointer < commandHistory.size()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canDownPointer() {
        if (pointer < (commandHistory.size() - 1)) {
            pointer++;
            return true;
        } else {
            return false;
        }
    }

    private void handleUpKey() {
        if (canUpPointer()) {
            String commandToShow = getCommandFromHistory();
            commandTextField.setText(commandToShow);
            setCaretToEnd();
        } else {
            setCaretAtOriginal();
        }
    }

    private void handleDownKey() {
        if (canDownPointer()) {
            String commandToShow = getCommandFromHistory();
            commandTextField.setText(commandToShow);
            setCaretToEnd();
        } else {
            resetPointer();
            commandTextField.clear();
        }
    }

    private String getCommandFromHistory() {
        return commandHistory.get(pointer);
    }

    private void setCaretToEnd() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                commandTextField.end();
            }
        });
    }

    private void setCaretAtOriginal() {
        int originalPosition = commandTextField.getCaretPosition();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                commandTextField.positionCaret(originalPosition);
            }
        });
    }
    //@@author
}
