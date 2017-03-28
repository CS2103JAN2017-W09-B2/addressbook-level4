package commandunittests;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.ClearCommand;
//@@author A0139379M
public class TestClearCommand extends ClearCommand {

    public TestClearCommand() throws IllegalValueException {
        super();
        this.setModel(new ModelStub());
        this.setSession(new SessionStub());
    }

    public void setModel(ModelStub model) {
        this.model = model;
    }

    public void setSession(SessionStub session) {
        this.session = session;
    }
}
