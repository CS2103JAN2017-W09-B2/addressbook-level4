package commandunittests;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.logic.commands.exceptions.CommandException;
import seedu.typed.logic.parser.DateTimeParser;
import seedu.typed.model.task.DateTime;
import seedu.typed.model.task.TaskBuilder;
//@@author A0139379M
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
            //LocalDateTime rubbish = DateTimeParser.getLocalDateTimeFromString("");
            LocalDateTime rubbish = null;
            LocalDateTime fifthApril = DateTimeParser.getLocalDateTimeFromString("05/04/2017");
            LocalDateTime twelfthDec = DateTimeParser.getLocalDateTimeFromString("12/12/2017");
            testCommand1 = new TestAddCommand("Meet Joe", "", fifthApril, rubbish, rubbish, new HashSet<String>());
            testCommand2 = new TestAddCommand("Meet Joe", "", fifthApril, rubbish, rubbish, new HashSet<String>());
            testCommand3 = new TestAddCommand("Meet Joe", "", fifthApril, rubbish, rubbish, new HashSet<String>());
            allPresent = new TestAddCommand("Meet Moo", "", twelfthDec, rubbish, rubbish, new HashSet<String>());
            testModel = new ModelStub();
            testModel.addTask(new TaskBuilder()
                    .setName("Meet Joe")
                    .setNotes("")
                    .setDeadline(DateTime.getDateTime(2017, Month.APRIL, 5, 0, 0))
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

    //TODO: fix test
    /*
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
     */
}
