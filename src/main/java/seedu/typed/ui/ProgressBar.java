package seedu.typed.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import seedu.typed.logic.Logic;

public class ProgressBar extends UiPart<Region>{
    private static final String FXML = "ProgressBar.fxml";

    @FXML
    private Circle progressCircle;
    @FXML
    private Arc progressRightHalf;
    @FXML
    private Arc progressLeftHalf;
    @FXML
    private Arc progressCenter1;
    @FXML
    private Arc progressCenter2;
    
    private Logic logic;


    /**
     * @param placeholder
     *            The AnchorPane where the BrowserPanel must be inserted
     */
    public ProgressBar(AnchorPane placeholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        placeholder.getChildren().add(progressCircle);
        placeholder.getChildren().add(progressRightHalf);
        placeholder.getChildren().add(progressLeftHalf);
        placeholder.getChildren().add(progressCenter1);
        placeholder.getChildren().add(progressCenter2);
        progressCircle.fillProperty().set(Color.AQUA);
        progressLeftHalf.startAngleProperty().set(90);
    }
}
