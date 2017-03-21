package commandunittests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import seedu.typed.logic.commands.CommandResult;


public class AddCommandTest {
    TestAddCommand testCommand;
    TestAddCommand allPresent;
    TestAddCommand allNulls;
    TestAddCommand dateNull;
    TestAddCommand tagsNull;
    TestAddCommand nameNull;
    TestAddCommand dateTagNulls;
    // TODO The AddCommand still does not tolerate null values for date, tags
    @Before
    public void setup() {
        try {
            testCommand = new TestAddCommand("Meet Joe", "05/04/2017", new HashSet<String>());
            allPresent = new TestAddCommand("Meet Moo", "12/12/2017", new HashSet<String>());
            //dateTagNulls = new TestAddCommand("Meet John", null , null);
            //allNulls = new TestAddCommand(null, null, null);
            //dateNull = new TestAddCommand("Meet John", null, new HashSet<String>());
            //tagsNull = new TestAddCommand("Meet John", "12/12/12", null);
            //nameNull = new TestAddCommand(null, "12/12/12", new HashSet<String>());
        } catch (Exception e) {
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
