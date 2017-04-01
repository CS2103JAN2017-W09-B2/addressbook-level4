package seedu.typed.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.typed.model.Model;

//@@author A0139392X
public class TypeOverview extends UiPart<Region> {
    private static final String FXML = "TypeOverview.fxml";

    private Model model;

    @FXML
    private Text title;

    @FXML
    private Circle overdueCircle;

    @FXML
    private Circle deadlineCircle;

    @FXML
    private Circle durationCircle;

    @FXML
    private Circle floatingCircle;

    public TypeOverview(AnchorPane holder, Model model) {
        super(FXML);
        this.model = model;
        holder.getChildren().add(title);
        holder.getChildren().add(overdueCircle);
        holder.getChildren().add(deadlineCircle);
        holder.getChildren().add(durationCircle);
        holder.getChildren().add(floatingCircle);
    }
}
