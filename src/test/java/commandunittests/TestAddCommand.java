package commandunittests;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.AddCommand;
//@@author A0139379M
public class TestAddCommand extends AddCommand {

    public TestAddCommand(String name, String notes, LocalDateTime date,
            LocalDateTime from, LocalDateTime to, Set<String> tags) throws IllegalValueException {
        super(name, notes, date, from, to, tags);
        this.setData(new ModelStub(), new SessionStub(), "", config, storage);

    }

    public void setModel(ModelStub model) {
        this.model = model;
    }

    public void setSession(SessionStub session) {
        this.session = session;
    }

}
