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
    private TestAddCommand allPresent;
    private ModelStub testModel;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            LocalDateTime nullTime = null;
            LocalDateTime fifthApril = DateTimeParser.getLocalDateTimeFromString("05/04/2017");
            LocalDateTime twelfthDec = DateTimeParser.getLocalDateTimeFromString("12/12/2017");
            testCommand1 = new TestAddCommand("Meet Joe", "", fifthApril, nullTime, nullTime, new HashSet<String>());
            testCommand2 = new TestAddCommand("Meet Joe", "", fifthApril, nullTime, nullTime, new HashSet<String>());
            allPresent = new TestAddCommand("Meet Moo", "", twelfthDec, nullTime, nullTime, new HashSet<String>());
            testModel = new ModelStub();
            testModel.addTask(new TaskBuilder()
                    .setName("Meet Joe")
                    .setNotes("")
                    .setDeadline(DateTime.getDateTime(2017, Month.APRIL, 5, 0, 0))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_modelNull_error() {
        testCommand1.setModel(null);
        thrown.expect(AssertionError.class);
        try {
            testCommand1.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_sessionNull_error() {
        testCommand2.setSession(null);
        thrown.expect(AssertionError.class);
        try {
            testCommand2.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_sessionModelNull_error() {
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
    public void execute_sessionModelPresent_success() {
        try {
            allPresent.execute();
        } catch (CommandException e) {

        }
    }
}
