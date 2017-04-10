package commandunittests;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.logic.commands.ClearCommand;
import seedu.typed.logic.commands.CommandResult;
import seedu.typed.logic.commands.exceptions.CommandException;
//@@author A0139379M
/**
 * Unit Testing for ClearCommand
 * We are testing on whether a populated TaskManager and new TaskManager will be cleared successfully
 * @author YIM CHIA HUI
 *
 */
public class ClearCommandTest {
    private TestClearCommand dirtyClear;
    private TestClearCommand cleanClear;
    private TestClearCommand testCommand;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IllegalValueException {
        ModelStub dirtyModel = new ModelStub();
        dirtyModel.addTestTask();
        ModelStub cleanModel = new ModelStub();
        dirtyClear = new TestClearCommand();
        cleanClear = new TestClearCommand();
        testCommand = new TestClearCommand();
        dirtyClear.setModel(dirtyModel);
        cleanClear.setModel(cleanModel);
    }


    @Test
    public void execute_modelNull_assertError() throws CommandException {
        testCommand.setModel(null);
        thrown.expect(AssertionError.class);
        testCommand.execute();
    }

    @Test
    public void execute_clearDirtyTaskManager_success() throws CommandException {
        assertEquals(dirtyClear.execute(), new CommandResult(ClearCommand.MESSAGE_SUCCESS));
    }

    @Test
    public void execute_clearEmptyTaskManager_success() throws CommandException {
        assertEquals(cleanClear.execute(), new CommandResult(ClearCommand.MESSAGE_SUCCESS));
    }
}
