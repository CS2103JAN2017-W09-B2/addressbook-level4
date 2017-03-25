package commandunittests;

import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;

public class TestAddCommand extends AddCommand {

    public TestAddCommand(String name, String notes, String date, String from, String to, Set<String> tags) throws IllegalValueException {
        super(name, notes, date, from, to, tags);
        this.setData(new ModelStub(), new SessionStub(), ""); //TODO Change String
    }

    public void setModel(ModelStub model) {
        this.model = model;
    }

    public void setSession(SessionStub session) {
        this.session = session;
    }

}
