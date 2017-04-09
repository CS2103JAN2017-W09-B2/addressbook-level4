//@@author A0139392X
package seedu.typed.ui;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.typed.commons.events.model.TaskManagerChangedEvent;
import seedu.typed.commons.events.ui.NewResultAvailableEvent;
import seedu.typed.commons.util.Pair;

public class Quotes extends UiPart<Region> {

    private static final String FXML = "Quotes.fxml";
    private static final int QUOTES_DATABASE_SIZE = 7;
    private final Image quotationLogo = new Image("/images/quotation.png");

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label quote;

    @FXML
    private Text author;

    @FXML
    private ImageView quotationHolder;

    private ArrayList<Pair<String, String>> quotesDatabase;

    public Quotes(AnchorPane placeholder) {
        super(FXML);
        placeholder.getChildren().add(quote);
        placeholder.getChildren().add(author);
        placeholder.getChildren().add(quotationHolder);
        placeholder.getChildren().add(mainPane);

        quotationHolder.setImage(quotationLogo);

        quotesDatabase = new ArrayList<Pair<String, String>>();
        initializeDatabase();

        int randomNumber = randomizedNumber();

        quote.setText(quotesDatabase.get(randomNumber).getFirst());
        author.setText("~ " + quotesDatabase.get(randomNumber).getSecond());

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(TaskManagerChangedEvent event) {
        quote.setText("");
        author.setText("");
        quotationHolder.setImage(null);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        quote.setText("");
        author.setText("");
        quotationHolder.setImage(null);
    }

    private int randomizedNumber() {
        return (int) ((Math.random() * 10) % QUOTES_DATABASE_SIZE);
    }

    private void initializeDatabase() {
        String first = "Productivity is being able to do things that "
                + "you were never able to do before.";
        String firstAuthor = "Franz Kafka";

        String second = "If you spend too much time thinking about a thing, you'll never get it done.";
        String secondAuthor = "Bruce Lee";

        String third = "There is no substitute for hard work.";
        String thirdAuthor = "Thomas Edison";

        String forth = "Productivity is never an accident. It is always the result of a "
                + "commitment to excellence, intelligent planning, and focused effort.";
        String forthAuthor = "Paul J. Meyer";

        String fifth = "Life is too complicated not to be orderly.";
        String fifthAuthor = "Martha Stewart";

        String sixth = "Until we can manage time, we can manage nothing else.";
        String sixthAuthor = "Peter Drucker";

        String seventh = "Your mind is for having ideas, not holding them.";
        String seventhAuthor = "David Allen";

        quotesDatabase.add(pair(first, firstAuthor));
        quotesDatabase.add(pair(second, secondAuthor));
        quotesDatabase.add(pair(third, thirdAuthor));
        quotesDatabase.add(pair(forth, forthAuthor));
        quotesDatabase.add(pair(fifth, fifthAuthor));
        quotesDatabase.add(pair(sixth, sixthAuthor));
        quotesDatabase.add(pair(seventh, seventhAuthor));
    }

    private Pair<String, String> pair(String quote, String author) {
        return new Pair<String, String>(quote, author);
    }
}
//@@author
