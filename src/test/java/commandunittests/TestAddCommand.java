package commandunittests;

import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;
//@@author A0139379M
public class TestAddCommand extends AddCommand {

    public TestAddCommand(String name, String date, Set<String> tags) throws IllegalValueException {
        super(name, date, tags);
        this.setData(new ModelStub(), new SessionStub(), "", config); //TODO Change String
    }

    public void setModel(ModelStub model) {
        this.model = model;
    }

    public void setSession(SessionStub session) {
        this.session = session;
    }

}
//@@author A0139379M