package commandunittests;

import seedu.typed.logic.commands.ClearCommand;

public class TestClearCommand extends ClearCommand {

    public TestClearCommand() {
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
