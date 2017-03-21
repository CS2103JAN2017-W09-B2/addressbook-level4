package commandunittests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.logic.commands.CommandResult;
import seedu.typed.logic.commands.exceptions.CommandException;

/**
 * Unit Testing for AddCommand
 * Test for null session or model and various AddCommands inputs
 * @author YIM CHIA HUI
 *
 */
public class AddCommandTest {
    TestAddCommand testCommand1;
    TestAddCommand testCommand2;
    TestAddCommand allPresent;
    TestAddCommand allNulls;
    TestAddCommand dateNull;
    TestAddCommand tagsNull;
    TestAddCommand nameNull;
    TestAddCommand dateTagNulls;
    // TODO The AddCommand still does not tolerate null values for date, tags


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        try {
            testCommand1 = new TestAddCommand("Meet Joe", "05/04/2017", new HashSet<String>());
            testCommand2 = new TestAddCommand("Meet Joe", "05/04/2017", new HashSet<String>());
            allPresent = new TestAddCommand("Meet Moo", "12/12/2017", new HashSet<String>());
            //dateTagNulls = new TestAddCommand("Meet John", null , null);
            //allNulls = new TestAddCommand(null, null, null);
            dateNull = new TestAddCommand("Meet John", null, new HashSet<String>());
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
    public void execute_allPresent_success() {
        try {
            assertEquals(allPresent.execute(),
                    new CommandResult("Task Added!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_dateTagNulls_success() {

    }
    @Test
    public void execute_dateNull_success() {

    }
    @Test
    public void execute_tagNull_success() {

    }
    @Test
    public void execute_nameNull_fail() {

    }
    @Test
    public void execute_allNull_fail() {

    }
}
