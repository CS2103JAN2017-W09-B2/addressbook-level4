package seedu.typed.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.typed.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private final Image stampComplete = new Image("/images/done.png");

    @FXML
    private AnchorPane cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Text notes;
    @FXML
    private ImageView stamp;

    //@@author A0139392X
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().getValue());
        id.setText(displayedIndex + ". ");
        if (task.haveDuration()) {
            date.setText(task.getFrom().getValue() + " to " + task.getTo().getValue());
        } else {
            date.setText(task.getDate().getValue());
        }
        notes.setText(task.getNotes().toString());

        if (task.getIsCompleted()) {
            stamp.setImage(stampComplete);
        }

        initTags(task);
    }
    //@@author

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
