package commandunittests;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.logic.commands.CommandResult;

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
    public void setUp() {
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
    public void execute_modelNull_assertError() {
        testCommand.setModel(null);
        thrown.expect(AssertionError.class);
        testCommand.execute();
    }

    @Test
    public void execute_clearDirtyTaskManager_success() {
        assertEquals(dirtyClear.execute(), new CommandResult("Task manager has been cleared!"));
    }

    @Test
    public void execute_clearEmptyTaskManager_success() {
        assertEquals(cleanClear.execute(), new CommandResult("Task manager has been cleared!"));
    }
}
