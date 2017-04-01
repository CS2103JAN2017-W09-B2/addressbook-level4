package seedu.typed.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;

public class Quotes extends UiPart<Region> {

    private static final String FXML = "Quotes.fxml";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Text quote;

    @FXML
    private Text author;

    public Quotes(AnchorPane placeholder) {
        super(FXML);
        placeholder.getChildren().add(quote);
        placeholder.getChildren().add(author);
        placeholder.getChildren().add(mainPane);

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(TaskManagerChangedEvent event) {
        quote.setText("");
        author.setText("");
    }
}
