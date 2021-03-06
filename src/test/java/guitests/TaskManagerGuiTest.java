package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.typed.TestApp;
import seedu.typed.commons.core.EventsCenter;
import seedu.typed.commons.events.BaseEvent;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.ReadOnlyTask;
import seedu.typed.testutil.TestUtil;
import seedu.typed.testutil.TypicalTestTasks;

/**
 * A GUI Test class for TaskManager.
 */
public abstract class TaskManagerGuiTest {

    /*
     * The TestName Rule makes the current test name available inside test
     * methods
     */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    private Stage stage;


    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {

            this.mainGui = new MainGuiHandle(new GuiRobot(), stage);
            this.mainMenu = this.mainGui.getMainMenu();
            this.taskListPanel = this.mainGui.getTaskListPanel();
            this.resultDisplay = this.mainGui.getResultDisplay();
            this.commandBox = this.mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        this.testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData,
                                                                                    getDataFileLocation()));
        FxToolkit.showStage();
        while (!this.stage.isShowing()) {
            ;
        }
        this.mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data. Return null
     * to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager tm = new TaskManager();
        TypicalTestTasks.loadTaskManagerWithSampleData(tm);
        return tm;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = this.taskListPanel.getNumberOfTask();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given
     * string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, this.resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        // JUnit doesn't run its test cases on the UI thread. Platform.runLater
        // is
        // used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
