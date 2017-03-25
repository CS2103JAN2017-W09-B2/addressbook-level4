package commandunittests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.logic.commands.CommandResult;
import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.model.task.TaskBuilder;
import seedu.typed.model.task.UniqueTaskList;

/**
 * Unit Testing for AddCommand
 * Test for null session or model and various AddCommands inputs
 * @author YIM CHIA HUI
 *
 */
public class AddCommandTest {
    private TestAddCommand testCommand1;
    private TestAddCommand testCommand2;
    private TestAddCommand testCommand3;
    private TestAddCommand allPresent;
    private ModelStub testModel;
    //private TestAddCommand allNulls;
    //private TestAddCommand dateNull;
    //private TestAddCommand tagsNull;
    //private TestAddCommand nameNull;
    //private TestAddCommand dateTagNulls;
    // TODO The AddCommand still does not tolerate null values for date, tags


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            testCommand1 = new TestAddCommand("Meet Joe", "05/04/2017", "", new HashSet<String>());
            testCommand2 = new TestAddCommand("Meet Joe", "05/04/2017", "", new HashSet<String>());
            testCommand3 = new TestAddCommand("Meet Joe", "05/04/2017", "", new HashSet<String>());
            allPresent = new TestAddCommand("Meet Moo", "12/12/2017", "", new HashSet<String>());
            testModel = new ModelStub();
            testModel.addTask(new TaskBuilder().setName("Meet Joe")
                    .setDate("05/04/2017")
                    .setFrom("")
                    .build());
            //dateTagNulls = new TestAddCommand("Meet John", null , null);
            //allNulls = new TestAddCommand(null, null, null);
            //dateNull = new TestAddCommand("Meet John", null, new HashSet<String>());
            //tagsNull = new TestAddCommand("Meet John", "12/12/2012", null);
            //nameNull = new TestAddCommand(null, "12/12/12", new HashSet<String>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_modelNull_assertError() {
        testCommand1.setModel(null);
        thrown.expect(AssertionError.class);
        try {
            testCommand1.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_sessionNull_assertError() {
        testCommand2.setSession(null);
        thrown.expect(AssertionError.class);
        try {
            testCommand2.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_sessionModelNull_assertError() {
        testCommand1.setModel(null);
        testCommand1.setSession(null);
        thrown.expect(AssertionError.class);
        try {
            testCommand1.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_sessionModelPresent_assertSuccess() {
        try {
            allPresent.execute();
        } catch (CommandException e) {

        }
    }

    @Test
    public void execute_duplicateTasks_exception() {
        try {
            testCommand3.setModel(testModel);
            testCommand3.execute();
            thrown.expect(UniqueTaskList.DuplicateTaskException.class);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_allPresent_success() {
        try {
            assertEquals(allPresent.execute(),
                    new CommandResult("Task Added!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
